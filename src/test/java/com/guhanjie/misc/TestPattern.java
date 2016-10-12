/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestPattern.java 
 * Create Date:		2016年10月12日 上午10:54:48 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class Name:		TestPattern<br/>
 * Description:		[description]
 * @time				2016年10月12日 上午10:54:48
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestPattern {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年10月12日 上午10:54:48
	 * @param args 
	 */
	public static void main(String[] args) {
		final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?\\}");
		System.out.println("pattern: "+VARIABLE_PATTERN.pattern());
		Scanner s = new Scanner(System.in);
		while(true) {
			String str = s.nextLine();
			if(VARIABLE_PATTERN.matcher(str).matches()) {
				System.out.println("matched: "+str);
			}
			else {
				System.out.println("not matched.");
			}
			System.out.println(VARIABLE_PATTERN.matcher(str).replaceAll("#"));
		}
	}
}
