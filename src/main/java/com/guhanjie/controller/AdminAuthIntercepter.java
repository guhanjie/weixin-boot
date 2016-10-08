package com.guhanjie.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.guhanjie.weixin.WeixinConstants;

@Component("AdminAuthIntercepter")
public class AdminAuthIntercepter implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthIntercepter.class);

    @Autowired
    private WeixinConstants weixinContants;
        
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    String openid = (String)request.getSession().getAttribute(AppConstants.SESSION_KEY_OPEN_ID);
	    if(openid==null) { //用户未登录，需要网页授权获取用户信息
	    	LOGGER.warn("user non login, auth not passed.");
	        return false;
	    }
	    else {
	        String kfOpenids = weixinContants.KF_OPENIDS;
	        if(StringUtils.isNotBlank(kfOpenids)) {
	            String[] kfs = kfOpenids.split(",");
	            for(String kf : kfs) {
	                if(openid.equals(kf)) {
	                    return true;
	                }
	            }
	        }
	    }
	    LOGGER.warn("user not KF admin, auth not passed.");
	    return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
