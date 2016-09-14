/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestVehicleEnum.java 
 * Create Date:		2016年9月14日 下午9:15:17 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import com.guhanjie.model.Order.VehicleEnum;

/**
 * Class Name:		TestVehicleEnum<br/>
 * Description:		[description]
 * @time				2016年9月14日 下午9:15:17
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestVehicleEnum {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年9月14日 下午9:15:17
	 * @param args 
	 */
	public static void main(String[] args) {
		short i = 2;
		System.out.println(VehicleEnum.valueOf(i).desc());
	}
}
