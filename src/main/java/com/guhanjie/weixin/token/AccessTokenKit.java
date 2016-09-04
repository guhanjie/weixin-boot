/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			AccessTokenKit.java 
 * Create Date:		2016年8月28日 下午11:02:14 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.WeixinContants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
import com.guhanjie.weixin.model.AccessToken;

/**
 * Class Name:		AccessTokenKit<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午11:02:14
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@Component
public class AccessTokenKit {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenKit.class);
    
    private static volatile String token;
    
    @Autowired
    private WeixinContants weixinContants;
    
    public static String getToken() {
        return token;
    }

    @Scheduled(fixedRate=6000000)
    public synchronized void refreshToken() {
        LOGGER.info("Starting to refresh access token...");
        try {
            String url = WeixinContants.API_ACCESS_TOKEN;
            url = url.replaceAll("APPID", weixinContants.APPID);
            url = url.replaceAll("APPSECRET", weixinContants.APPSECRET);
            WeixinHttpUtil.sendGet(url, new WeixinHttpCallback() {
                @Override
                public void process(String json) {
                    AccessToken at = JSONObject.parseObject(json, AccessToken.class);
                    if(at != null) {
                        token = at.getAccess_token();
                        LOGGER.info("Success to refresh access token:[{}].", token);
                    }
                    else {
                        LOGGER.error("Failed to refresh access token.");
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to refresh access token.", e);
        }
    }
    
}
