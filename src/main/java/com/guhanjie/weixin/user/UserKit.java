/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.user 
 * File Name:			UserKit.java 
 * Create Date:		2016年9月4日 上午9:30:50 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.user;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
import com.guhanjie.weixin.model.UserInfo;

/**
 * Class Name:		UserKit<br/>
 * Description:		[description]
 * @time				2016年9月4日 上午9:30:50
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class UserKit {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserKit.class);
    
    public static UserInfo getUserInfo(final String openid) {
        LOGGER.info("Starting to get user[{}] info...", openid);
        final UserInfo user = new UserInfo();
        try {
            String url = WeixinConstants.API_USER_INFO;
            url = url.replaceAll("OPENID", openid);
            WeixinHttpUtil.sendGet(url, new WeixinHttpCallback() {
                @Override
                public void process(String json) {
                    UserInfo ui = JSONObject.parseObject(json, UserInfo.class);
                    if(ui!=null && ui.getOpenid()!=null) {
                        try {
                            PropertyUtils.copyProperties(user, ui);
                            LOGGER.info("Success to get user info:[{}].", json);
                        }
                        catch (Exception e) {
                            LOGGER.error("error in coping user properties");
                        }
                    }
                    else {
                        LOGGER.error("Failed to get user[{}] info.", openid);
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to get user[{}] info.", openid);
        }
        return user;
    }
    
    public static UserInfo getUserInfoByOauth2(final String openid, final String accsstoken) {
        LOGGER.info("Starting to get user[{}] info by oauth2.0...", openid);
        final UserInfo user = new UserInfo();
        try {
            String url = WeixinConstants.OAUTH2_GET_USER_INFO;
            url = url.replaceAll("OPENID", openid);
            url = url.replaceAll("ACCESS_TOKEN", accsstoken);
            WeixinHttpUtil.sendGet(url, new WeixinHttpCallback() {
                @Override
                public void process(String json) {
                    UserInfo ui = JSONObject.parseObject(json, UserInfo.class);
                    if(ui!=null && ui.getOpenid()!=null) {
                        try {
                            PropertyUtils.copyProperties(user, ui);
                            LOGGER.info("Success to get user info:[{}] by oauth2.0.", json);
                        }
                        catch (Exception e) {
                            LOGGER.error("error in coping user properties");
                        }
                    }
                    else {
                        LOGGER.error("Failed to get user[{}] info by oauth2.0.", openid);
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to get user[{}] info by oauth2.0.", openid);
        }
        return user.getOpenid() != null ? user : null;
    }
}
