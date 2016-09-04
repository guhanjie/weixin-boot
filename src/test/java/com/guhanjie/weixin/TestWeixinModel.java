/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			TestWeixinModel.java 
 * Create Date:		2016年9月3日 下午9:04:49 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.model.AccessToken;
import com.guhanjie.weixin.model.ErrorEntity;
import com.guhanjie.weixin.model.UserInfo;

/**
 * Class Name:		TestWeixinModel<br/>
 * Description:		[description]
 * @time				2016年9月3日 下午9:04:49
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestWeixinModel {
    @Test
    public void testErrorEntity() {
        String response = "{\"errcode\":40013,\"errmsg\":\"invalid appid hint: [.n0.QA0680ken1]\"}";
        System.out.println(response);
        ErrorEntity e = JSON.parseObject(response, ErrorEntity.class);
        System.out.println(e.getErrcode()+"\n"+e.getErrmsg());
        assertNotNull(e);
    }
    
    @Test
    public void testAccessToken() {
//        String response = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
        String response = "{\"errcode\":40013,\"errmsg\":\"invalid appid hint: [.n0.QA0680ken1]\"}";
        System.out.println(response);
        AccessToken e = JSONObject.parseObject(response, AccessToken.class);
//        AccessToken e = JSON.parseObject(response, AccessToken.class);
        System.out.println(e.getAccess_token()+"\n"+e.getExpires_in());
        assertNotNull(e);
    }    

    @Test
    public void testUserInfo() {
        String response =                         
//                        "{" + 
//                        "   \"subscribe\": 1, " + 
//                        "   \"openid\": \"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\", " + 
//                        "   \"nickname\": \"Band\", " + 
//                        "   \"sex\": 1, " + 
//                        "   \"language\": \"zh_CN\", " + 
//                        "   \"city\": \"广州\", " + 
//                        "   \"province\": \"广东\", " + 
//                        "   \"country\": \"中国\", " + 
//                        "   \"headimgurl\":  \"http://wx.qlogo.cn/mmopen/g3MHe/0\"," + 
//                        "  \"subscribe_time\": 1382694957," + 
//                        "  \"unionid\": \" o6_bmasdasdsad6_2sgVt7hMZOPfL\"," + 
//                        "  \"remark\": \"\"," + 
//                        "  \"groupid\": 0," + 
//                        "  \"tagid_list\":[128,2]" + 
//                        "}";
                        "{" + 
                        "    \"subscribe\": 0, " + 
                        "    \"openid\": \"otvxTs_JZ6SEiP0imdhpi50fuSZg\", " + 
                        "    \"unionid\": \"oR5GjjjrbqBZbrnPwwmSxFukE41U\"" + 
                        "}";
        UserInfo user = JSON.parseObject(response, UserInfo.class);
        //System.out.println(Arrays.asList(user.getTagid_list()).toString());
        System.out.println(user.getNickname());
        assertNotNull(user);
    }
}
