/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.controller 
 * File Name:			WeixinController.java 
 * Create Date:		2016年8月28日 下午8:58:30 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guhanjie.util.SHA1Util;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
import com.guhanjie.weixin.model.AccessToken;
import com.guhanjie.weixin.msg.MessageKit;

/**
 * Class Name:		WeixinController<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午8:58:30
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@Controller
@RequestMapping("/wx")
public class WeixinController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);
    
    @Autowired
    private WeixinConstants weixinContants;

    @RequestMapping(value="",method=RequestMethod.GET)
    public void init(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	String echostr = req.getParameter("echostr");
        if(checkSignature(req)) {
        	LOGGER.info("Weixin auth successfully.");
        	resp.getWriter().println(echostr);
    		resp.getWriter().flush();
        }
    }
    
    @RequestMapping(value="",method=RequestMethod.POST)
    public void receiveMsg(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        if(checkSignature(req)) {
            Map<String,String> msgMap = MessageKit.reqMsg2Map(req);        
            LOGGER.debug("Weixin msg request="+msgMap);
            String respCon = MessageKit.handlerMsg(msgMap);
            resp.setContentType("application/xml;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            LOGGER.debug("Weixin msg response= "+respCon);
            resp.getWriter().write(respCon);
    		resp.getWriter().flush();
        }
    }    
    
    @RequestMapping(value="oauth2",method=RequestMethod.GET)
    public void oauth2(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	final HttpSession session = req.getSession();
    	final HttpServletResponse response = resp;
    	String originState = (String)session.getAttribute(AppConstants.SESSION_KEY_OAUTH_STATE);
    	//根据state校验是否是刚刚发出的授权申请，防止CSRF跨站伪造攻击
    	String state = req.getParameter("state");
    	if(!state.equals(originState)) {
    		LOGGER.warn("The state[{}] does not match original value[{}]. You may be a victim of CSRF.", state, originState);
    		resp.getWriter().write("Authentication failed. It may be CSRF attack.");
    		resp.getWriter().flush();
    		return;
    	}
        String code = req.getParameter("code");
        String url = WeixinConstants.OAUTH2_ACCESS_TOKEN;
        url = url.replaceAll("APPID", weixinContants.APPID);
        url = url.replaceAll("SECRET", weixinContants.APPSECRET);
        url = url.replaceAll("CODE", code);
        WeixinHttpUtil.sendGet(url, new WeixinHttpCallback() {
            @Override
            public void process(String json) {
                AccessToken at = JSONObject.parseObject(json, AccessToken.class);
                if(at!=null && at.getAccess_token()!=null && at.getOpenid()!=null) {
                	//拿到accesstoken，绑定到对应的人
                    String token = at.getAccess_token();
                    String openid = at.getOpenid();
                    LOGGER.info("User authentication successful, access token:[{}], openid:[{}].", token, openid);
                	session.setAttribute(AppConstants.SESSION_KEY_ACCESS_TOKEN, token);
                	try {
	                	String returnURL = (String)session.getAttribute(AppConstants.SESSION_KEY_RETURN_URL);
	                	if(StringUtils.isBlank(returnURL)) {
								response.getWriter().write("Welcome, user authentication successful.");
								response.getWriter().flush();
	                	}
	                	else {	//跳转回原来地址
	                		LOGGER.info("redirecting back to last request[{}] for user.", returnURL);
	                		response.sendRedirect(returnURL);
	                	}
                	}
                	catch (IOException e) {
                		e.printStackTrace();
                	}
                }
                else {
                    LOGGER.error("User authentication failed, response[{}] error.", JSON.toJSONString(at));
                }
            }
        });
    }    
    
    private boolean checkSignature(HttpServletRequest req) {
//      signature   微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//      timestamp   时间戳
//      nonce   随机数
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
    	String[] arrs = {weixinContants.TOKEN,nonce,timestamp};
        Arrays.sort(arrs);
        StringBuffer sb = new StringBuffer();
        for(String a:arrs) {
            sb.append(a);
        }
        String sha1 = SHA1Util.sha1(sb.toString());
        if(sha1.equals(signature)) {
            LOGGER.debug("Success to check weixin request signature.");
            return true;
        }
        else {
        	LOGGER.warn("Failed to check weixin request signature, this msg may be fake!");
        	return false;
        }
    }
    
//    @RequestMapping("/at")
//    public void testAccessToken(HttpServletResponse resp) throws IOException {
//        resp.getWriter().println(WeixinContext.getAccessToken());
//    }
}
