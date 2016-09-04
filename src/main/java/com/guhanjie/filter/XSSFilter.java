/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.filter 
 * File Name:			XSSFilter.java 
 * Create Date:		2015年12月21日 下午3:13:03 
 * Copyright (c) 2008-2015, Canglong All Rights Reserved.
 */  
package com.guhanjie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Class Name:		XSSFilter<br/>
 * Description:		[拦截跨站脚本攻击请求]
 * @time				2015年12月21日 下午3:13:03
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class XSSFilter implements Filter {
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }
}
