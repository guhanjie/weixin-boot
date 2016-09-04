/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestTryCatchFinally.java 
 * Create Date:		2016年9月3日 下午8:44:26 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

/**
 * Class Name:		TestTryCatchFinally<br/>
 * Description:		[description]
 * @time				2016年9月3日 下午8:44:26
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestTryCatchFinally {
    public static void main(String[] args) {
        String str = get();
        System.out.println("return "+str);
    }
    
    public static String get() {
        try {
            System.out.println("entering method...");
//            return "dfsdfs";
        }
        catch(Exception e) {
            System.out.println("exception");
        }
        finally {
            System.out.println("Finally");
        }
        System.out.println("returning method xxx");
        return "xxx";
    }
}
