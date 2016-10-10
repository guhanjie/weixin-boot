/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestHttpUtil.java 
 * Create Date:		2016年10月11日 上午1:38:14 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
 * Class Name:		TestHttpUtil<br/>
 * Description:		[description]
 * @time				2016年10月11日 上午1:38:14
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestHttpUtil {
    
    private static Logger LOGGER = LoggerFactory.getLogger(TestHttpUtil.class);
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年10月11日 上午1:38:14
     * @param args 
     */
    public static void main(String[] args) {
        
        HttpGet get = null;
        CloseableHttpClient client = null;        
        CloseableHttpResponse resp = null;
        final String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=DYon2mr_xJAGKtRG-gwkh2q3PBJ3QIILlRx2XT-xZC8MmPm8bQ345aG8CttbQBD6kW1pqlKTAITpH8nFAdtvsogkdeqBwFW9rHDTcDYL3eDvALmlh5CLCRM_2TPcoLLAQFMeAEAFUB&openid=o_05Uwe4_9GGQ93ESXg27RCw6HqE&lang=zh_CN";
        try {
            client = HttpClients.createDefault();
            LOGGER.debug("sending http get request[{}]...", url);
            get = new HttpGet(url);
            resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode>=200 && statusCode<300) {
                HttpEntity entity = resp.getEntity();
                LOGGER.debug("content-type: [{}]", entity.getContentType().getValue());
                String content = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                LOGGER.debug("success to get response:[{}]", content);
                ErrorEntity err = JSONObject.parseObject(content, ErrorEntity.class);
                if(err.getErrcode()!= null || err.getErrmsg()!=null) {
                    LOGGER.error("http[{}] response is error, errcode:[{}], errmsg:[{}].", url, err.getErrcode(), err.getErrmsg());
                    return;
                }
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
}
