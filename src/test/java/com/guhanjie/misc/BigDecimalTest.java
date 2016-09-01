/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			BigDecimalTest.java 
 * Create Date:		2016年9月1日 上午11:53:42 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.math.BigDecimal;

/**
 * Class Name:		BigDecimalTest<br/>
 * Description:		[description]
 * @time				2016年9月1日 上午11:53:42
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class BigDecimalTest {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年9月1日 上午11:53:42
	 * @param args 
	 */
	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("0.0000911");
		System.out.println(b);
        BigDecimal b1 = new BigDecimal(1).setScale(2).movePointRight(2);
        System.out.println(b1);
	}
}
