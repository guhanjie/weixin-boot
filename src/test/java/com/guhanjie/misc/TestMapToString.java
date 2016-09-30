/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestMapToString.java 
 * Create Date:		2016年9月30日 上午11:25:23 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Name:		TestMapToString<br/>
 * Description:		[description]
 * @time				2016年9月30日 上午11:25:23
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestMapToString {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年9月30日 上午11:25:23
	 * @param args 
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "guhanjie");
		map.put("2", "guhanjie");
		map.put("3", "guhanjie");
		map.put("4", "guhanjie");
		System.out.println(map);
	}
}
