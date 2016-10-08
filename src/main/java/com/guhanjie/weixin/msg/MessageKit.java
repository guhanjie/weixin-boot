/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.msg 
 * File Name:			MessageKit.java 
 * Create Date:		2016年8月28日 下午9:23:20 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.msg;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guhanjie.model.Position;
import com.guhanjie.model.User;
import com.guhanjie.service.UserService;
import com.guhanjie.util.HttpUtil;
import com.guhanjie.util.SpringContextUtil;
import com.guhanjie.util.XmlUtil;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.WeixinHttpUtil;
import com.guhanjie.weixin.WeixinHttpUtil.WeixinHttpCallback;
import com.guhanjie.weixin.model.ErrorEntity;
import com.guhanjie.weixin.model.UserInfo;
import com.guhanjie.weixin.user.UserKit;

/**
 * Class Name:		MessageKit<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:23:20
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class MessageKit {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageKit.class);
    
    private static Map<String,String> replyMsgs = new HashMap<String,String>();
    static{
        replyMsgs.put("123", "你输入了123");
        replyMsgs.put("hello", "world");
        replyMsgs.put("run", "祝你一路平安!");
    }
    
    public static Map<String,String> reqMsg2Map(HttpServletRequest req) {
        try {
            Document xml = HttpUtil.getRequest2Xml(req);
            Map<String,String> maps = XmlUtil.xml2map(xml);
            return maps;
        } catch (Exception e) {
            LOGGER.error("error in parsing request to xml from weixin.", e); 
        }
        return null;
    }
    
    /**
	 * Method Name:	handlerMsg<br/>
	 * Description:			[处理微信消息]
	 * @author				GUHANJIE
	 * @time					2016年9月2日 上午10:58:25
	 * @param msgMap
	 * @return
	 * @throws IOException
	 */
	public static String handlerMsg(Map<String, String> msgMap) {
	    String msgType = msgMap.get("MsgType");
	    try {
    	    //处理事件消息
    	    if(msgType.equals(WeixinConstants.MSG_TYPE_EVENT)) {
    	    	String eventType = msgMap.get("Event");
    	    	//订阅事件
    	        if(eventType.equals(WeixinConstants.EVENT_SUBSCRIBE)) {
    	        	return handleSubscribeEvent(msgMap);
    	        }
    	        //上报地址位置事件
    	        else if(eventType.equals(WeixinConstants.EVENT_LOCATION)) {
    	        	return handleLocationEvent(msgMap);
    	        }
    	    }
    	    //处理文本消息
    	    else if(msgType.equals(WeixinConstants.MSG_TYPE_TEXT)) {
    	        return handleTextMsg(msgMap);
    	    }
    	    //处理图片消息
    	    else if(msgType.equals(WeixinConstants.MSG_TYPE_IMAGE)) {
    	        return handleImageMsg(msgMap,"_I53ClKoGvcQC4z1mWLf-O_nDJ_rw2p-LtfJOslSONSzUEtv8eKEvlDbn8m71d9m");
    	    }
	    } catch(IOException e) {
	        LOGGER.error("error to handle msg for weixin.", e);
	    }
	    return "";
	}

	private static String handleImageMsg(Map<String, String> msgMap,String mediaId) throws IOException {
	    LOGGER.info("starting to handle image message....");
	    Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "image");
        map.put("Image", "<MediaId>"+mediaId+"</MediaId>");
        return XmlUtil.map2xmlstr(map);
    }
    
    private static String handleTextMsg(Map<String, String> msgMap) throws IOException {
        LOGGER.info("starting to handle text message....");
        Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "text");
        String replyContent = "你请求的消息的内容不正确!";
        String con = msgMap.get("Content");
        if(replyMsgs.containsKey(con)) {
            replyContent = replyMsgs.get(con);
        }
        map.put("Content", replyContent);
        return XmlUtil.map2xmlstr(map);
    }
    
    private static String handleSubscribeEvent(Map<String, String> msgMap) throws IOException {
        LOGGER.info("starting to handle subscribe event....");
        String openId = msgMap.get("FromUserName");
    	//自动添加关注用户
    	try {
            UserInfo userInfo = UserKit.getUserInfo(openId);
            UserService userService = SpringContextUtil.getBean(UserService.class);
            User user = new User();
            user.setOpenId(userInfo.getOpenid());
            user.setUnionid(userInfo.getUnionid());
            user.setName(userInfo.getNickname());
            user.setNickname(userInfo.getNickname());
            user.setSex(userInfo.getSex());
            user.setLanguage(userInfo.getLanguage());
            user.setCountry(userInfo.getCountry());
            user.setProvince(userInfo.getProvince());
            user.setCity(userInfo.getCity());
            user.setSubscribeTime(new Date(Long.parseLong(userInfo.getSubscribe_time())));
            userService.addUser(user);
    	} catch(Exception e) {
    	    LOGGER.error("error happened in add user info, openId[{}].", openId, e);
    	}
        Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "text");
        map.put("Content", "您好，欢迎关注！");
    	return XmlUtil.map2xmlstr(map);
    }
    
    private static String handleLocationEvent(Map<String, String> msgMap) throws IOException {
        LOGGER.info("starting to handle location event....");
        String openId = msgMap.get("FromUserName");
        String lat = msgMap.get("Latitude");
    	String lng = msgMap.get("Longitude");
    	//获取用户上报的地址微信，并绑定到内存中
    	User user = new User();
    	user.setOpenId(openId);
    	Position p = new Position();
    	p.setGeoLat(lat);
    	p.setGeoLng(lng);
    	user.setPosition(p);
        UserService userService = SpringContextUtil.getBean(UserService.class);
    	userService.updateToCache(user);
    	return "";
    }
    
    public static void sendKFMsg(String openids, String content) {
        if(StringUtils.isBlank(openids)) {
            LOGGER.warn("send order msg to KF, but no kf openids.");
            return;
        }
        String [] ids = openids.split(",");
        for(final String openid : ids) {
            LOGGER.info("starting to send message to KF[{}]...", openid);
            try {
                String url = WeixinConstants.API_KF_SEND_MSG;
                String str = new String("{" + 
                                                    "    \"touser\":\"OPENID\"," + 
                                                    "    \"msgtype\":\"text\"," + 
                                                    "    \"text\":" + 
                                                    "    {" + 
                                                    "         \"content\":\"ContentText\"" + 
                                                    "    }" + 
                                                    "}");
                str = str.replaceAll("OPENID", openid);
                str = str.replaceAll("ContentText", content);
                LOGGER.debug("message content: [{}]", str);
                HttpEntity entity = new StringEntity(str, ContentType.APPLICATION_JSON);
                WeixinHttpUtil.sendPost(url, entity, new WeixinHttpCallback() {
                        @Override
                        public void process(String json) {
                            ErrorEntity t = JSONObject.parseObject(json, ErrorEntity.class);
                            if(t!=null && t.getErrcode()!=null && t.getErrmsg()!=null) {
                                if(t.getErrcode().equals("0") && t.getErrmsg().equals("ok"))
                                LOGGER.info("Success to post message to KF[{}].", openid);
                                return;
                            }
                            LOGGER.error("Failed to post message to KF, error:[{}].", json);
                        }
                    });
            } catch (Exception e) {
                LOGGER.error("error to post message[{}] to KF[{}].", content, openid);
            } 
        }
	}
}
