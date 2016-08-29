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
@RequestMapping("/w")
public class WeixinController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);
    
    @Autowired
    private WeixinContants weixinContants;

    @RequestMapping(value="/get",method=RequestMethod.GET)
    public void init(HttpServletRequest req,HttpServletResponse resp) throws IOException {
//      signature   微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//      timestamp   时间戳
//      nonce   随机数
//      echostr
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        String[] arrs = {weixinContants.TOKEN,nonce,timestamp};
        Arrays.sort(arrs);
        StringBuffer sb = new StringBuffer();
        for(String a:arrs) {
            sb.append(a);
        }
        String sha1 = SHA1Util.sha1(sb.toString());
//      System.out.println(sha1.equals(signature));
        if(sha1.equals(signature)) {
            LOGGER.info("Weixin auth successfully.");
            resp.getWriter().println(echostr);
        }
    }
    
    @RequestMapping(value="/post",method=RequestMethod.POST)
    public void getInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Map<String,String> msgMap = MessageKit.reqMsg2Map(req);
        LOGGER.info("request="+msgMap);
        String respCon = MessageKit.handlerMsg(msgMap);
        resp.setContentType("application/xml;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        LOGGER.info("response= "+respCon);
        resp.getWriter().write(respCon);
    }    
    
//    @RequestMapping("/at")
//    public void testAccessToken(HttpServletResponse resp) throws IOException {
//        resp.getWriter().println(WeixinContext.getAccessToken());
//    }
}
