/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.media 
 * File Name:			MediaKit.java 
 * Create Date:		2016年8月28日 下午9:20:49 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.media;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.WeixinContants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
import com.guhanjie.weixin.model.WeixinMedia;
import com.guhanjie.weixin.token.AccessTokenKit;

/**
 * Class Name:		MediaKit<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:20:49
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class MediaKit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaKit.class);
    
    public static String postMedia(String path,String type) {
        LOGGER.debug("starting to post media: path[{}] type[{}]...", path, type);
        final WeixinMedia wm = new WeixinMedia();
        try {
            String url = WeixinContants.API_POST_MEDIA;
            url = url.replace("TYPE", type);
            FileBody fb = new FileBody(new File(path));
            HttpEntity entity = MultipartEntityBuilder.create().addPart("media", fb).build();
            WeixinHttpUtil.sendPost(url, entity, new WeixinHttpCallback() {
                @Override
                public void process(String json) {
                    WeixinMedia t = JSONObject.parseObject(json, WeixinMedia.class);
                    if(t != null) {
                        wm.setMedia_id(t.getMedia_id());
                        LOGGER.info("Success to post media:[{}].", t.getMedia_id());
                    }
                    else {
                        LOGGER.error("Failed to post media.");
                    }
                }                
            });
        } catch (Exception e) {
            LOGGER.error("error post media: type[{}], path[{}]", type, path);
        } 
        return wm.getMedia_id();
    }
    
    public static void getMedia(String mediaId,File f) {
        LOGGER.debug("starting to get media: mediaId[{}]...", mediaId);        
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;        
        try {
            client = HttpClients.createDefault();
            String url = WeixinContants.API_GET_MEDIA;
            url = url.replace("MEDIA_ID", mediaId);
            url = url.replace("ACCESS_TOKEN", AccessTokenKit.getToken());
            HttpGet get = new HttpGet(url);
            resp = client.execute(get);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                InputStream is = resp.getEntity().getContent();
                FileUtils.copyInputStreamToFile(is, f);
                LOGGER.debug("success to get media[{}]", mediaId);
            }
        } catch (Exception e) {
            LOGGER.error("error get media[{}].", mediaId);
        } finally {
            HttpClientUtils.closeQuietly(resp);
            HttpClientUtils.closeQuietly(client);
        }
    }
}
