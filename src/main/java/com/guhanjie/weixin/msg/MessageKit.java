/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin.msg 
 * File Name:			MessageKit.java 
 * Create Date:		2016年8月28日 下午9:23:20 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.guhanjie.weixin.WeixinContants;

/**
 * Class Name:		MessageKit<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:23:20
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class MessageKit {
    private static Map<String,String> replyMsgs = new HashMap<String,String>();
    static{
        replyMsgs.put("123", "你输入了123");
        replyMsgs.put("hello", "world");
        replyMsgs.put("run", "祝你一路平安!");
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String,String> reqMsg2Map(HttpServletRequest req) throws IOException {
        String xml = req2xml(req);
        try {
            Map<String,String> maps = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> eles = root.elements();
            for(Element e:eles) {
                maps.put(e.getName(), e.getTextTrim());
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String req2xml(HttpServletRequest req) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while((str=br.readLine())!=null) {
            sb.append(str);
        }
        return sb.toString();
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
	public static String handlerMsg(Map<String, String> msgMap) throws IOException {
	    String msgType = msgMap.get("MsgType");
	    //处理事件消息
	    if(msgType.equals(WeixinContants.MSG_TYPE_EVENT)) {
	    	String eventType = msgMap.get("Event");
	    	//订阅事件
	        if(eventType.equals(WeixinContants.EVENT_SUBSCRIBE)) {
	        	return handleSubscribeEvent(msgMap);
	        }
	        //上报地址位置事件
	        else if(eventType.equals(WeixinContants.EVENT_LOCATION)) {
	        	return handleLocationEvent(msgMap);
	        }
	    }
	    //处理文本消息
	    else if(msgType.equals(WeixinContants.MSG_TYPE_TEXT)) {
	        return handleTextMsg(msgMap);
	    }
	    //处理图片消息
	    else if(msgType.equals(WeixinContants.MSG_TYPE_IMAGE)) {
	        return handleImageMsg(msgMap,"_I53ClKoGvcQC4z1mWLf-O_nDJ_rw2p-LtfJOslSONSzUEtv8eKEvlDbn8m71d9m");
	    }
	    return "";
	}

	public static String map2xml(Map<String, String> map) throws IOException {
	    Document d = DocumentHelper.createDocument();
	    Element root = d.addElement("xml");
	    Set<String> keys = map.keySet();
	    for(String key:keys) {
	        root.addElement(key).addText(map.get(key));
	    }
	    StringWriter sw = new StringWriter();
	    XMLWriter xw = new XMLWriter(sw);
	    xw.setEscapeText(false);
	    xw.write(d);
	    return sw.toString();
	}

	private static String handleImageMsg(Map<String, String> msgMap,String mediaId) throws IOException {
        Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "image");
        map.put("Image", "<MediaId>"+mediaId+"</MediaId>");
        return map2xml(map);
    }
    
    private static String handleTextMsg(Map<String, String> msgMap) throws IOException {
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
        return map2xml(map);
    }
    
    private static String handleSubscribeEvent(Map<String, String> msgMap) throws IOException {
    	String openId = msgMap.get("FromUserName");
    	String createTime = msgMap.get("CreateTime");
    	//自动添加关注用户
        Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "text");
        map.put("Content", "您好，欢迎关注！");
    	return map2xml(map);
    }
    
    private static String handleLocationEvent(Map<String, String> msgMap) throws IOException {
    	String lat = msgMap.get("Latitude");
    	String lng = msgMap.get("Longitude");
    	//获取用户上报的地址微信，并绑定到内存中
    	return "";
    }
}
