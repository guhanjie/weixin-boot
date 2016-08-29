/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.util 
 * File Name:			SHA1Util.java 
 * Create Date:		2016年8月28日 下午9:03:13 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class Name:		SHA1Util<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:03:13
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class SHA1Util {
    public static String sha1(String str) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] msg = md.digest();
            for(byte b:msg) {
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
