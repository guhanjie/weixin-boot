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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guhanjie.util.SHA1Util;
import com.guhanjie.weixin.WeixinContants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
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
    private WeixinContants weixinContants;

    @RequestMapping(value="",method=RequestMethod.GET)
    public void init(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	String echostr = req.getParameter("echostr");
        if(checkSignature(req)) {
        	resp.getWriter().println(echostr);
        	LOGGER.info("Weixin auth successfully.");
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
        }
    }    
    
    @RequestMapping(value="oauth2",method=RequestMethod.GET)
    public void oauth2(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String state = req.getParameter("state");
        String url = WeixinContants.OAUTH2_ACCESS_TOKEN;
        url = url.replaceAll("APPID", weixinContants.APPID);
        url = url.replaceAll("SECRET", weixinContants.APPSECRET);
        url = url.replaceAll("CODE", code);
        WeixinHttpUtil.sendGet(url, new WeixinHttpCallback() {
            @Override
            public void process(String json) {
                // TODO Auto-generated method stub
                //拿到accesstoken，根据state绑定到对应的人
            }
        });
        //跳转回原来地址
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
