/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.pay 
 * File Name:			PayKit.java 
 * Create Date:		2016年9月26日 下午10:49:11 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.pay;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    public void unifiedorder() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", weixinConstants.APPID);
        map.put("mch_id", weixinConstants.APPID);
        map.put("device_info", "WEB");
        map.put("nonce_str", "24234324");
        map.put("sign", "24234324");
        map.put("body", "商家名称-销售商品类目");
        map.put("detail", "商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。");
        map.put("attach", "附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据");
        map.put("out_trade_no", "商户系统内部的订单号,32个字符内、可包含字母");
        map.put("fee_type", "CNY");
        map.put("spbill_create_ip", "用户端ip");
        map.put("time_start", "订单生成时间，格式为yyyyMMddHHmmss");
        map.put("time_expire", "订单失效时间，格式为yyyyMMddHHmmss,最短失效时间间隔必须大于5分钟");
        map.put("goods_tag", "商品标记");
        map.put("notify_url", "接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。");
        map.put("trade_type", "JSAPI");
        map.put("product_id", "此id为二维码中包含的商品ID，商户自行定义");
        map.put("limit_pay", "no_credit--指定不能使用信用卡支付");
        map.put("openid", "用户在商户appid下的唯一标识");
        
    }
}
