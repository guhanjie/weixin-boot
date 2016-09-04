/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestArraysort.java 
 * Create Date:		2016年9月4日 下午2:34:55 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.util.Arrays;

/**
 * Class Name:		TestArraysort<br/>
 * Description:		[description]
 * @time				2016年9月4日 下午2:34:55
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestArraysort {
    public static void main(String[] args) {
        String[] strs = {"sdf", "erhg", "aof"};
        Arrays.sort(strs);
        System.out.println(Arrays.asList(strs));
    }
}
