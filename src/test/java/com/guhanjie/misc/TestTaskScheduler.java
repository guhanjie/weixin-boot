/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestThreadPoolTaskExecutor.java 
 * Create Date:		2016年10月3日 上午10:59:16 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class Name:		TestThreadPoolTaskExecutor<br/>
 * Description:		[description]
 * @time				2016年10月3日 上午10:59:16
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/context/application-context.xml"})
public class TestTaskScheduler {
    
    @Resource(name="paySearchScheduler")
    private TaskScheduler taskScheduler;
    
    @Test
    public void test() {
        Date now = new Date();
        System.out.println("=============="+now);
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("=================="+new Date());
            }
        }, new Date(now.getTime()+5000));
        try {
            Thread.sleep(20000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
