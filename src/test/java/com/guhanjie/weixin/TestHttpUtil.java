/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			HttpUtilTest.java 
 * Create Date:		2016年9月3日 下午11:46:53 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.junit.Test;

import com.guhanjie.util.HttpUtil;
import com.guhanjie.util.HttpUtil.HttpCallback;

/**
 * Class Name:		HttpUtilTest<br/>
 * Description:		[description]
 * @time				2016年9月3日 下午11:46:53
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestHttpUtil {
    @Test
    public void testSendGet() throws UnsupportedOperationException, IOException {
        HttpUtil.sendGet("http://www.baidu.com", new HttpCallback() {
            public void processEntity(HttpEntity e) {
                System.out.println(e.getContentLength());        
                try {
                    System.out.println(IOUtils.toString(e.getContent()));
                }
                catch (UnsupportedOperationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }
}
