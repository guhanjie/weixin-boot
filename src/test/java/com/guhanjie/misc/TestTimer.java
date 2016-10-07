/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestTimer.java 
 * Create Date:		2016年10月3日 上午10:05:08 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class Name:		TestTimer<br/>
 * Description:		[description]
 * @time				2016年10月3日 上午10:05:08
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestTimer {
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年10月3日 上午10:05:08
     * @param args 
     */
    public static void main(String[] args) {
        final Timer timer = new Timer();
        Date now = new Date();
        System.out.println(now);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println(new Date());
                timer.cancel();
            }
            
        }, new Date(now.getTime()+5000));
    }
}
