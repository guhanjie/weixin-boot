/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.media 
 * File Name:			MediaKit.java 
 * Create Date:		2016年8月28日 下午9:20:49 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.WeixinContants;
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

    public static String postMedia(String path,String type) {
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        
        try {
            client = HttpClients.createDefault();
            String url = WeixinContants.API_POST_MEDIA;
            url = url.replace("ACCESS_TOKEN", AccessTokenKit.getToken());
            url = url.replace("TYPE", type);
            HttpPost post = new HttpPost(url);
            FileBody fb = new FileBody(new File(path));
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("media", fb).build();
            post.setEntity(reqEntity);
            resp = client.execute(post);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                String json = EntityUtils.toString(resp.getEntity());
                WeixinMedia wm = JSONObject.parseObject(json, WeixinMedia.class);
                return wm.getMedia_id();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(client!=null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(resp!=null) resp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void getMedia(String mediaId,File f) {
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        
        try {
            client = HttpClients.createDefault();
            String url = WeixinContants.API_GET_MEDIA;
            url = url.replace("ACCESS_TOKEN", AccessTokenKit.getToken());
            url = url.replace("MEDIA_ID", mediaId);
            HttpGet get = new HttpGet(url);
            resp = client.execute(get);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                InputStream is = resp.getEntity().getContent();
                FileUtils.copyInputStreamToFile(is, f);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(client!=null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(resp!=null) resp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
