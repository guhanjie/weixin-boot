/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestStringReplace.java 
 * Create Date:		2016年9月16日 下午2:07:14 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

/**
 * Class Name:		TestStringReplace<br/>
 * Description:		[description]
 * @time				2016年9月16日 下午2:07:14
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestStringReplace {
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年9月16日 下午2:07:14
     * @param args 
     */
    public static void main(String[] args) {
        String str = new String("{" + 
                        "    \"touser\":\"OPENID\"," + 
                        "    \"msgtype\":\"text\"," + 
                        "    \"text\":" + 
                        "    {" + 
                        "         \"content\":\"ContentText\"" + 
                        "    }" + 
                        "}");
        System.out.println(str);
        String openid = "dhfjsd";
        String content = "xxxx";
        str = str.replaceAll("OPENID", openid);
        str = str.replaceAll("ContentText", content);
        System.out.println(str);
    }
}
