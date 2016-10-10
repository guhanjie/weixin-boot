package com.guhanjie.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.guhanjie.weixin.WeixinConstants;

@Component("OAuthIntercepter")
public class OAuthIntercepter implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthIntercepter.class);

    @Autowired
    private WeixinConstants weixinContants;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    String openid = (String)request.getSession().getAttribute(AppConstants.SESSION_KEY_OPEN_ID);
	    if(openid==null) { //用户未登录，需要网页授权获取用户信息
	        if("1".equals(request.getParameter("p"))) {    //预览用
	            return true;
	        }
	    	LOGGER.info("user non login, redirect to weixin oauth2.0...");
	    	HttpSession session = request.getSession();
	    	String lasturl = request.getRequestURI();
	    	session.setAttribute(AppConstants.SESSION_KEY_RETURN_URL, lasturl);
	    	String state = String.valueOf(new Random().nextInt(100));
	    	session.setAttribute(AppConstants.SESSION_KEY_OAUTH_STATE, state);
	    	LOGGER.info("cache return url[{}] and oauth state[{}] into user session", lasturl, state);
	        String url = WeixinConstants.OAUTH2_AUTHORIZE;
            url = url.replaceAll("APPID", weixinContants.APPID);
            url = url.replaceAll("REDIRECT_URI", weixinContants.OAUTH2_REDIRECT_URI);
            url = url.replaceAll("SCOPE", weixinContants.OAUTH2_SCOPE_SNSAPI_USERINFO);
            url = url.replaceAll("STATE", state);
            response.sendRedirect(url);
	        return false;
	    }
	    LOGGER.debug("user[{}] login...", openid);
	    return true;
//        LOGGER.debug("intercept request, getScheme: [{}]", request.getScheme());
//        LOGGER.debug("intercept request, getProtocol: [{}]", request.getProtocol());
//        LOGGER.debug("intercept request, getRequestURI: [{}]", request.getRequestURI());
//        LOGGER.debug("intercept request, getRequestURL: [{}]", request.getRequestURL());
//        LOGGER.debug("intercept request, getContextPath: [{}]", request.getContextPath());
//        LOGGER.debug("intercept request, getServletPath: [{}]", request.getServletPath());
//        LOGGER.debug("intercept request, getPathInfo: [{}]", request.getPathInfo());
//        LOGGER.debug("intercept request, getQueryString: [{}]", request.getQueryString());
//        LOGGER.debug("intercept request, getParameterMap: [{}]", request.getParameterMap()==null?"":request.getParameterMap().toString());
//        LOGGER.debug("intercept request, getMethod: [{}]", request.getMethod());
//        LOGGER.debug("intercept request, getContentType: [{}]", request.getContentType());
//        LOGGER.debug("intercept request, getRemoteAddr: [{}]", request.getRemoteAddr());
//        LOGGER.debug("intercept request, getRemoteHost: [{}]", request.getRemoteHost());
//        LOGGER.debug("intercept request, getRemotePort: [{}]", request.getRemotePort());
//        LOGGER.debug("intercept request, getRemoteUser: [{}]", request.getRemoteUser());
//        LOGGER.debug("intercept request, getLocalAddr: [{}]", request.getLocalAddr());
//        LOGGER.debug("intercept request, getLocalName: [{}]", request.getLocalName());
//        LOGGER.debug("intercept request, getLocalPort: [{}]", request.getLocalPort());
//        LOGGER.debug("intercept request, getServerName: [{}]", request.getServerName());
//        LOGGER.debug("intercept request, getServerPort: [{}]", request.getServerPort());
//        LOGGER.debug("intercept request, getAuthType: [{}]", request.getAuthType());
//        LOGGER.debug("intercept request, getCharacterEncoding: [{}]", request.getCharacterEncoding());
//        LOGGER.debug("intercept request, getContentLength: [{}]", request.getContentLength());
//        LOGGER.debug("intercept request, getCookies: [{}]", request.getCookies()==null?"":request.getCookies().toString());
//        LOGGER.debug("intercept request, getPathTranslated: [{}]", request.getPathTranslated());
//        LOGGER.debug("intercept request, getRequestedSessionId: [{}]", request.getRequestedSessionId());
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
