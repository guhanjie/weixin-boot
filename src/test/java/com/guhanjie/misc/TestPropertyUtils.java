/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.misc 
 * File Name:			TestPropertieUtils.java 
 * Create Date:		2016年9月4日 下午12:33:35 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.misc;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.alibaba.fastjson.JSON;
import com.guhanjie.model.User;

/**
 * Class Name:		TestPropertieUtils<br/>
 * Description:		[description]
 * @time				2016年9月4日 下午12:33:35
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class TestPropertyUtils {
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年9月4日 下午12:33:35
     * @param args 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final User user1 = new User();
        System.out.println(JSON.toJSONString(user1));
        User user2 = new User();
        user2.setCity("jfds");
        user2.setId(12421);
        user2.setNickname("sdfsdf");
        PropertyUtils.copyProperties(user1, user2);
        //BeanUtils.copyProperties(user1, user2);
        System.out.println(JSON.toJSONString(user1));
    }
}
