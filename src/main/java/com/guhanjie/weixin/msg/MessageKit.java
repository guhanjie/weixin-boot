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
    public static String handlerMsg(Map<String, String> msgMap) throws IOException {
        String msgType = msgMap.get("MsgType");
        if(msgType.equals(WeixinContants.MSG_EVENT_TYPE)) {
            
        } else if(msgType.equals(WeixinContants.MSG_TEXT_TYPE)) {
            return textTypeHandler(msgMap);
        } else if(msgType.equals(WeixinContants.MSG_IMAGE_TYPE)) {
            return imageTypeHandler(msgMap,"_I53ClKoGvcQC4z1mWLf-O_nDJ_rw2p-LtfJOslSONSzUEtv8eKEvlDbn8m71d9m");
        }
        return null;
    }
    private static String imageTypeHandler(Map<String, String> msgMap,String mediaId) throws IOException {
        Map<String,String> map = new HashMap<String, String>();
        map.put("ToUserName", msgMap.get("FromUserName"));
        map.put("FromUserName", msgMap.get("ToUserName"));
        map.put("CreateTime", new Date().getTime()+"");
        map.put("MsgType", "image");
        map.put("Image", "<MediaId>"+mediaId+"</MediaId>");
        return map2xml(map);
    }
    private static String textTypeHandler(Map<String, String> msgMap) throws IOException {
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
}
