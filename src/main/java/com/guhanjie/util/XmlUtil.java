/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			WeixinXmlUtil.java 
 * Create Date:		2016年9月26日 下午10:55:09 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * Class Name:		XmlUtil<br/>
 * Description:		[description]
 * @time				2016年9月26日 下午10:55:09
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class XmlUtil {
    
    public static Document str2xml(String str) throws DocumentException {
        if(str == null || str.isEmpty()) {
            return null;
        }
        Document xml = DocumentHelper.parseText(str);
        return xml;
    }
    
    public static Map<String, String> xml2map(Document xml) {
        if(xml == null) {
            return null;
        }
        Map<String,String> maps = new HashMap<String, String>();
        Element root = xml.getRootElement();
        List<Element> eles = root.elements();
        for(Element e:eles) {
            maps.put(e.getName(), e.getTextTrim());
        }
        return maps;
    }
    
    public static Map<String, String> xmlstr2map(String str) throws DocumentException {
        Document xml = str2xml(str);
        return xml2map(xml);
    }
    
    public static String map2xmlstr(Map<String, String> map) throws IOException {
        if(map == null) {
            return null;
        }
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
