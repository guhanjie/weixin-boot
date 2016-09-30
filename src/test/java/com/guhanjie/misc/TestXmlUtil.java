/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestXmlUtil.java 
 * Create Date:		2016年9月30日 下午12:01:50 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.guhanjie.util.XmlUtil;

/**
 * Class Name:		TestXmlUtil<br/>
 * Description:		[description]
 * @time				2016年9月30日 下午12:01:50
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestXmlUtil {
	@Test
	public void test() throws DocumentException, IOException {
		String xml = "<xml>\r\n" + 
						"   <return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
						"   <return_msg><![CDATA[OK]]></return_msg>\r\n" + 
						"   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\r\n" + 
						"   <mch_id><![CDATA[10000100]]></mch_id>\r\n" + 
						"   <device_info><![CDATA[1000]]></device_info>\r\n" + 
						"   <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>\r\n" + 
						"   <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>\r\n" + 
						"   <result_code><![CDATA[SUCCESS]]></result_code>\r\n" + 
						"   <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>\r\n" + 
						"   <is_subscribe><![CDATA[Y]]></is_subscribe>\r\n" + 
						"   <trade_type><![CDATA[MICROPAY]]></trade_type>\r\n" + 
						"   <bank_type><![CDATA[CCB_DEBIT]]></bank_type>\r\n" + 
						"   <total_fee>1</total_fee>\r\n" + 
						"   <fee_type><![CDATA[CNY]]></fee_type>\r\n" + 
						"   <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>\r\n" + 
						"   <out_trade_no><![CDATA[1415757673]]></out_trade_no>\r\n" + 
						"   <attach><![CDATA[订单额外描述]]></attach>\r\n" + 
						"   <time_end><![CDATA[20141111170043]]></time_end>\r\n" + 
						"   <trade_state><![CDATA[SUCCESS]]></trade_state>\r\n" + 
						"</xml>";
		
		Map<String, String> map = XmlUtil.xmlstr2map(xml);
		for(String key : map.keySet()) {
			System.out.println(key+" : "+map.get(key));
		}
		
		Map<String, Object> map2= new HashMap<String, Object>();
		map2.put("abc", 666);
		map2.put("cba", "nice");
				
		String str = XmlUtil.map2xmlstr(map2);
		System.out.println(str);
	}
}
