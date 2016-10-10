/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestStrReplace.java 
 * Create Date:		2016年10月9日 上午4:16:41 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import com.guhanjie.weixin.WeixinConstants;

/**
 * Class Name:		TestStrReplace<br/>
 * Description:		[description]
 * @time				2016年10月9日 上午4:16:41
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestStrReplace {
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年10月9日 上午4:16:41
     * @param args 
     */
    public static void main(String[] args) {
        String str = WeixinConstants.OAUTH2_AUTHORIZE;
        System.out.println(str);
        str = str.replaceAll("STATE", "123");
        System.out.println(str);
    }
}
