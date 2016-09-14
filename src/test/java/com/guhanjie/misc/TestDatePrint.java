/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestDatePrint.java 
 * Create Date:		2016年9月14日 下午9:04:24 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Name:		TestDatePrint<br/>
 * Description:		[description]
 * @time				2016年9月14日 下午9:04:24
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestDatePrint {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年9月14日 下午9:04:24
	 * @param args 
	 */
	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println(sdf.format(date));
	}
}
