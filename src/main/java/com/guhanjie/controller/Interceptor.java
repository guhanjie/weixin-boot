package com.guhanjie.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component(value = "interceptor")
public class Interceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("intercept request, getScheme: [{}]", request.getScheme());
        LOGGER.debug("intercept request, getProtocol: [{}]", request.getProtocol());
        LOGGER.debug("intercept request, getRequestURI: [{}]", request.getRequestURI());
        LOGGER.debug("intercept request, getRequestURL: [{}]", request.getRequestURL());
        LOGGER.debug("intercept request, getContextPath: [{}]", request.getContextPath());
        LOGGER.debug("intercept request, getServletPath: [{}]", request.getServletPath());
        LOGGER.debug("intercept request, getPathInfo: [{}]", request.getPathInfo());
        LOGGER.debug("intercept request, getQueryString: [{}]", request.getQueryString());
        LOGGER.debug("intercept request, getParameterMap: [{}]", request.getParameterMap().toString());
        LOGGER.debug("intercept request, getMethod: [{}]", request.getMethod());
        LOGGER.debug("intercept request, getContentType: [{}]", request.getContentType());
        LOGGER.debug("intercept request, getRemoteAddr: [{}]", request.getRemoteAddr());
        LOGGER.debug("intercept request, getRemoteHost: [{}]", request.getRemoteHost());
        LOGGER.debug("intercept request, getRemotePort: [{}]", request.getRemotePort());
        LOGGER.debug("intercept request, getRemoteUser: [{}]", request.getRemoteUser());
        LOGGER.debug("intercept request, getLocalAddr: [{}]", request.getLocalAddr());
        LOGGER.debug("intercept request, getLocalName: [{}]", request.getLocalName());
        LOGGER.debug("intercept request, getLocalPort: [{}]", request.getLocalPort());
        LOGGER.debug("intercept request, getServerName: [{}]", request.getServerName());
        LOGGER.debug("intercept request, getServerPort: [{}]", request.getServerPort());
        LOGGER.debug("intercept request, getAuthType: [{}]", request.getAuthType());
        LOGGER.debug("intercept request, getCharacterEncoding: [{}]", request.getCharacterEncoding());
        LOGGER.debug("intercept request, getContentLength: [{}]", request.getContentLength());
        LOGGER.debug("intercept request, getCookies: [{}]", request.getCookies().toString());
        LOGGER.debug("intercept request, getPathTranslated: [{}]", request.getPathTranslated());
        LOGGER.debug("intercept request, getRequestedSessionId: [{}]", request.getRequestedSessionId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
