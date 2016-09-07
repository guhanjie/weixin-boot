/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			TTT.java 
 * Create Date:		2016年9月3日 下午10:27:28 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.model.AccessToken;
import com.guhanjie.weixin.model.ErrorEntity;

/**
 * Class Name:		TTT<br/>
 * Description:		[description]
 * @time				2016年9月3日 下午10:27:28
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestGetAccessToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestGetAccessToken.class);
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年9月3日 下午10:27:28
     * @param args 
     */
    public static void main(String[] args) {
        LOGGER.info("Starting to refresh access token...");
        HttpGet get = null;
        CloseableHttpResponse resp = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            String url = WeixinConstants.API_ACCESS_TOKEN;
            url = url.replaceAll("APPID", "***");
            url = url.replaceAll("APPSECRET", "***");
            get = new HttpGet(url);
            resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode>=200 && statusCode<300) {
                HttpEntity entity = resp.getEntity();
                String content = EntityUtils.toString(entity);
                try {
                    LOGGER.debug("Got response:[{}]", content);
                    AccessToken at = JSONObject.parseObject(content, AccessToken.class);
//                    token = at.getAccess_token();
                    LOGGER.info("Success to refresh access token:[{}]",at);
                } catch (Exception e) {
                    ErrorEntity err = JSONObject.parseObject(content, ErrorEntity.class);
                    LOGGER.error("Failed to refresh access token, errcode:[{}], errmsg:[{}].", err.getErrcode(), err.getErrmsg());
//                    refreshToken();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Http error while refreshing access token", e);
        } finally {
            try {
                if(resp!=null) resp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(client!=null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
