/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.pay 
 * File Name:			PayKit.java 
 * Create Date:		2016年9月26日 下午10:49:11 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.pay;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guhanjie.controller.AppConstants;
import com.guhanjie.model.Order;
import com.guhanjie.util.HttpUtil;
import com.guhanjie.util.MD5Util;
import com.guhanjie.util.XmlUtil;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;

/**
 * Class Name:		PayKit<br/>
 * Description:		[description]
 * @time				2016年9月26日 下午10:49:11
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class PayKit {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayKit.class);
    
    /**
     * Method Name:	unifiedorder<br/>
     * Description:			[除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，
     *                              返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。]
     * @author				guhanjie
     * @time					2016年9月30日 上午1:39:12
     * @param request
     * @param order
     * @param appid
     * @param mchid
     * @throws IOException
     */
    public static String unifiedorder(HttpServletRequest request, final Order order, final String appid, final String mchid, final String mchkey) throws IOException {
        LOGGER.info("starting to unified order to weixin for order:[{}]...", order.getId());
        
        final StringBuilder prepayId = new StringBuilder();        
        
    	final String nonceStr = String.valueOf(new Random().nextInt(10000));
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);                                                                         //公众账号ID
        map.put("mch_id", mchid);                                                                   //商户号
        map.put("device_info", "WEB");                                                              //设备号(PC网页或公众号内支付请传"WEB")
        map.put("nonce_str", nonceStr);                                                         //随机字符串
        map.put("body", "上海尊涵搬家-搬家服务");                                         //商品描述
        //map.put("detail", "商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。");          //商品详情
        //map.put("attach", "附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据");
        map.put("out_trade_no", order.getId().toString());                                  //商户订单号
        map.put("fee_type", "CNY");                                                                 //货币类型
        map.put("total_fee", String.valueOf(order.getAmount().intValue()*100+order.getTip().intValue()*100));                           //总金额，订单总金额，单位为分
        map.put("spbill_create_ip", HttpUtil.getIpAddress(request));                    //用户端IP
        map.put("time_start", new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()));                 //交易起始时间，格式为yyyyMMddHHmmss
        //map.put("time_expire", "订单失效时间，格式为yyyyMMddHHmmss,最短失效时间间隔必须大于5分钟");                       //交易结束时间
        //map.put("goods_tag", "WXG");                                                          //商品标记
        map.put("notify_url", WeixinConstants.API_PAY_CALLBACK);                //通知地址，接收微信支付异步通知回调地址
        map.put("trade_type", "JSAPI");                                                         //交易类型
        //map.put("product_id", "此id为二维码中包含的商品ID，商户自行定义");//商品ID
        //map.put("limit_pay", "no_credit--指定不能使用信用卡支付");               //支付方式
        map.put("openid", (String)request.getSession().getAttribute(AppConstants.SESSION_KEY_OPEN_ID));                         //用户标识        
        map.put("sign", sign(map, mchkey));                                                             //签名
        String reqOrderStr = XmlUtil.map2xmlstr(map);
        LOGGER.debug("=====set request for weixin unified order:[{}].", reqOrderStr);
        
        HttpEntity entity = new StringEntity(reqOrderStr, ContentType.create("application/xml", Consts.UTF_8));
        WeixinHttpUtil.sendPost(WeixinConstants.API_PAY_UNIFIEDORDER, entity, new WeixinHttpCallback() {
            @Override
            public void process(String respBody) {
                try {
                    Map<String, String> map = XmlUtil.xmlstr2map(respBody);
                    LOGGER.debug("=====got response for weixin unified order:[{}].", map);
                    String return_code = map.get("return_code");                                        //返回状态码SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
                    String return_msg = map.get("return_msg");                                          //返回信息
                    if(return_code==null || !return_code.equals("SUCCESS")) {
                    	LOGGER.error("fail to get response for weixin unified order api, cause:[{}]", return_msg);
                    	return;
                    }
                	//验签
                    String signature = sign(map, mchkey);
                    String sign = map.get("sign");                                                          //签名
                    if(sign==null || !sign.equals(signature)) {
                    	LOGGER.warn("signature validation failed, this response maybe fake!");
                    	return;
                    }
                    //根据业务结果，执行后续业务操作
                    String appid = map.get("appid");                                                        //公众账号ID
                    String mch_id = map.get("mch_id");                                                  //商户号
                    String device_info = map.get("device_info");                                        //设备号
                    String nonce_str = map.get("nonce_str");                                            //随机字符串
                    String result_code = map.get("result_code");                                    //业务结果
                    String err_code = map.get("err_code");                                              //错误代码
                    String err_code_des = map.get("err_code_des");                              //错误代码描述
                    if(!nonceStr.equals(nonce_str)) {
                    	LOGGER.warn("nonce not matched, this response maybe fake!");
                    	return;
                    }
                    if(result_code==null || !result_code.equals("SUCCESS")) {
                    	LOGGER.error("fail to get response for weixin unified order api, cause: err_code[{}], err_code_des[{}]", err_code, err_code_des);
                    	return;
                    }
                    String trade_type = map.get("trade_type");                                  //交易类型JSAPI
                    String prepay_id = map.get("prepay_id");                                     //预支付交易会话ID
                    String code_url = map.get("code_url");											//二维码链接
                    prepayId.append(prepay_id);
                    LOGGER.info("success to get weixin prepay id:[{}] for order:[{}]", prepayId, order.getId());
                }
                catch (DocumentException e) {
                    LOGGER.error("error in parsing response xml:[{}]", respBody, e);
                }
            }
        });
        return prepayId.toString();
    }
    
    public static void search(final String orderid, final String appid, final String mchid, final String mchkey) throws IOException {
        LOGGER.info("starting to search payment for order:[{}]...", orderid);

    	final String nonceStr = String.valueOf(new Random().nextInt(10000));
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);                                                                      //公众账号ID
        map.put("mch_id", mchid);                                                                  //商户号
        map.put("out_trade_no", orderid);                                                       	//商户订单号
        map.put("nonce_str", nonceStr);                                                         //随机字符串
        map.put("sign", sign(map, mchkey));                                         			//签名

        String reqStr = XmlUtil.map2xmlstr(map);
        LOGGER.debug("=====set request for weixin search payment:[{}].", reqStr);
        
        HttpEntity entity = new StringEntity(reqStr, ContentType.create("application/xml", Consts.UTF_8));
        WeixinHttpUtil.sendPost(WeixinConstants.API_PAY_ORDERQUERY, entity, new WeixinHttpCallback() {
            @Override
            public void process(String respBody) {
                try {
                    Map<String, String> map = XmlUtil.xmlstr2map(respBody);
                    LOGGER.debug("=====got response for weixin search payment:[{}].", map);
                    String return_code = map.get("return_code");                                        //返回状态码SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
                    String return_msg = map.get("return_msg");                                          //返回信息
                    if(return_code==null || !return_code.equals("SUCCESS")) {
                    	LOGGER.error("fail to get response for weixin search payment api, cause:[{}]", return_msg);
                    	return;
                    }
                	//验签
                    String signature = sign(map, mchkey);
                    String sign = map.get("sign");                                                          //签名
                    if(sign==null || !sign.equals(signature)) {
                    	LOGGER.warn("signature validation failed, this response maybe fake!");
                    	return;
                    }
                    //根据业务结果，执行后续业务操作
                    String appid = map.get("appid");                                                        //公众账号ID
                    String mch_id = map.get("mch_id");                                                  //商户号
                    String nonce_str = map.get("nonce_str");                                            //随机字符串
                    String result_code = map.get("result_code");                                    //业务结果
                    String err_code = map.get("err_code");                                              //错误代码
                    String err_code_des = map.get("err_code_des");                              //错误代码描述
                    if(!nonceStr.equals(nonce_str)) {
                    	LOGGER.warn("nonce not matched, this response maybe fake!");
                    	return;
                    }
                    if(result_code==null || !result_code.equals("SUCCESS")) {
                    	LOGGER.error("fail to get response for weixin search payment api, cause: err_code[{}], err_code_des[{}]", err_code, err_code_des);
                    	return;
                    }
                    String device_info = map.get("device_info");                                        //设备号
                    String openid = map.get("openid");                                        			//用户OpenId
                    String trade_state = map.get("trade_state");                                  //交易状态(SUCCESS—支付成功、REFUND—转入退款、NOTPAY—未支付、CLOSED—已关闭、REVOKED—已撤销（刷卡支付）、USERPAYING--用户支付中、PAYERROR--支付失败(其他原因，如银行返回失败)、)
                    String total_fee = map.get("total_fee");											//订单总金额，单位为分
                    String settlement_total_fee = map.get("settlement_total_fee");		//应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
                    String transaction_id = map.get("transaction_id");						//微信支付订单号
                    String out_trade_no = map.get("out_trade_no");							//商户订单号
                    String time_end = map.get("time_end");										//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
                    String trade_state_desc = map.get("trade_state_desc");				//对当前查询订单状态的描述和下一步操作的指引
//                    String is_subscribe = map.get("is_subscribe");                                //用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
//                    String trade_type = map.get("trade_type");                                  //交易类型JSAPI
//                    String bank_type = map.get("bank_type");                                     //付款银行(银行类型，采用字符串类型的银行标识)
//                    String fee_type = map.get("fee_type");											//货币种类
//                    String cash_fee = map.get("cash_fee");											//货币种类
                    LOGGER.info("success to search weixin payment for order:[{}]: "
                    				+ "openid=[{}], trade_state=[{}], total_fee=[{}], out_trade_no=[{}], time_end=[{}], trade_state_desc=[{}]", 
                    				orderid, openid, trade_state, total_fee, out_trade_no, time_end, trade_state_desc);
                }
                catch (DocumentException e) {
                    LOGGER.error("error in parsing response xml:[{}]", respBody, e);
                }
            }
        });
    }
    
    public static String sign(Map<String, String> params, String secretKey) {
    	if(params == null || params.isEmpty()) {
    		LOGGER.error("signaute params can not be null or empty.");
    	}
    	List<String> keys = new ArrayList<String>(params.keySet().size());
    	for(String key : params.keySet()) {
    		keys.add(key);
    	}
    	Collections.sort(keys);
    	StringBuilder stringA = new StringBuilder();
    	for(String key : keys) {
    		if(!key.equals("sign") && !StringUtils.isEmpty(params.get(key))) {		//sign参数不参与签名，参数的值为空不参与签名
    			stringA.append(key).append("=").append(params.get(key)).append("&");
    		}
    	}
    	String stringSignTemp = stringA.append("key=").append(secretKey).toString();
    	String sign = MD5Util.md5(stringSignTemp).toUpperCase();
    	return sign;
    }
}
