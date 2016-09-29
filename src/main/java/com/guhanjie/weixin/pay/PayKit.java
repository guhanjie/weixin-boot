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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.guhanjie.controller.AppConstants;
import com.guhanjie.model.Order;
import com.guhanjie.util.HttpUtil;
import com.guhanjie.util.MD5Util;
import com.guhanjie.util.XmlUtil;
import com.guhanjie.weixin.WeixinConstants;

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
    
    @Autowired
    private WeixinConstants weixinConstants;
    
    public void unifiedorder(HttpServletRequest request, Order order) throws IOException {
    	final String nonceStr = String.valueOf(new Random().nextInt(10000));
    	
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", weixinConstants.APPID);											//公众账号ID
        map.put("mch_id", weixinConstants.APPID);											//商户号
        map.put("device_info", "WEB");																//设备号(PC网页或公众号内支付请传"WEB")
        map.put("nonce_str", nonceStr);															//随机字符串
        map.put("body", "上海尊涵搬家-搬家服务");											//商品描述
        //map.put("detail", "商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。");			//商品详情
        //map.put("attach", "附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据");
        map.put("out_trade_no", order.getId().toString());									//商户订单号
        map.put("fee_type", "CNY");																	//货币类型
        map.put("total_fee", String.valueOf(order.getAmount().intValue()*100+order.getTip().intValue()*100));							//总金额，订单总金额，单位为分
        map.put("spbill_create_ip", HttpUtil.getIpAddress(request));					//用户端IP
        map.put("time_start", new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()));					//交易起始时间，格式为yyyyMMddHHmmss
        //map.put("time_expire", "订单失效时间，格式为yyyyMMddHHmmss,最短失效时间间隔必须大于5分钟");						//交易结束时间
        //map.put("goods_tag", "WXG");															//商品标记
        map.put("notify_url", weixinConstants.API_PAY_CALLBACK);				//通知地址，接收微信支付异步通知回调地址
        map.put("trade_type", "JSAPI");															//交易类型
        //map.put("product_id", "此id为二维码中包含的商品ID，商户自行定义");//商品ID
        //map.put("limit_pay", "no_credit--指定不能使用信用卡支付");				//支付方式
        map.put("openid", (String)request.getSession().getAttribute(AppConstants.SESSION_KEY_OPEN_ID));							//用户标识        
        map.put("sign", sign(map, ""));																//签名
        String reqOrderStr = XmlUtil.map2xmlstr(map);
        
        
    }
    
    public void parse(Map<String, String> map) {
    	String return_code = map.get("return_code");										//返回状态码SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    	String return_msg = map.get("return_msg");											//返回信息
    	if(return_code.equals("SUCCESS")) {
    		String appid = map.get("appid");														//公众账号ID
    		String mch_id = map.get("mch_id");													//商户号
    		String device_info = map.get("device_info");										//设备号
    		String nonce_str = map.get("nonce_str");											//随机字符串
    		String sign = map.get("sign");															//签名
    		String result_code = map.get("result_code");									//业务结果
    		String err_code = map.get("err_code");												//错误代码
    		String err_code_des = map.get("err_code_des");								//错误代码描述
    		if(result_code.equals("SUCCESS")) {
    			String trade_type = map.get("trade_type");									//交易类型JSAPI
    			String prepay_id = map.get("prepay_id");										//预支付交易会话ID
    		}
    	}
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
