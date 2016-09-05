/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			WeixinHttpUtil.java 
 * Create Date:		2016年9月4日 上午9:39:23 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.model.ErrorEntity;
import com.guhanjie.weixin.token.AccessTokenKit;

/**
 * Class Name:		WeixinHttpUtil<br/>
 * Description:		[description]
 * @time				2016年9月4日 上午9:39:23
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class WeixinHttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinHttpUtil.class);
    
    public static void sendGet(String url, WeixinHttpCallback c) {        
        HttpGet get = null;
        CloseableHttpClient client = null;        
        CloseableHttpResponse resp = null;
        try {
            client = HttpClients.createDefault();
            url = url.replaceAll("ACCESS_TOKEN", AccessTokenKit.getToken());
            LOGGER.debug("sending http get request[{}]...", url);
            get = new HttpGet(url);
            resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode>=200 && statusCode<300) {
                HttpEntity entity = resp.getEntity();
                String content = EntityUtils.toString(entity);
                LOGGER.debug("success to get response:[{}]", content);
                ErrorEntity err = JSONObject.parseObject(content, ErrorEntity.class);
                if(err.getErrcode()!= null || err.getErrmsg()!=null) {
                    LOGGER.error("http[{}] response is error, errcode:[{}], errmsg:[{}].", url, err.getErrcode(), err.getErrmsg());
                    return;
                }
                c.process(content);
                LOGGER.debug("success to finish http get request.");
            }
            else {
                LOGGER.error("failed to get http response, http status:[{}]", statusCode);
            }
        } catch (Exception e) {
            LOGGER.error("failed to send http get request", e);
        } finally {
            HttpClientUtils.closeQuietly(resp);
            HttpClientUtils.closeQuietly(client);
        }
    }
    
    public static void sendPost(String url, HttpEntity entity, WeixinHttpCallback c) {
        HttpPost post = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;        
        try {
            client = HttpClients.createDefault();
            url = url.replace("ACCESS_TOKEN", AccessTokenKit.getToken());
            LOGGER.debug("sending http post request[{}]...", url);
            post = new HttpPost(url);
            post.setEntity(entity);
            resp = client.execute(post);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                String json = EntityUtils.toString(resp.getEntity());
                LOGGER.debug("success to get response:[{}]", json);
                ErrorEntity err = JSONObject.parseObject(json, ErrorEntity.class);
                if(err.getErrcode()!= null || err.getErrmsg()!=null) {
                    LOGGER.error("http[{}] response is error, errcode:[{}], errmsg:[{}].", url, err.getErrcode(), err.getErrmsg());
                    return;
                }
                c.process(json);
                LOGGER.debug("success to finish http post request.");
            }
        } catch (Exception e) {
            LOGGER.error("failed to send http post request", e);
        } finally {
            HttpClientUtils.closeQuietly(resp);
            HttpClientUtils.closeQuietly(client);
        }
    }
    
    public static abstract class WeixinHttpCallback {
        public abstract void process(String json);
    }
}
