/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			TestWeixinConstants.java 
 * Create Date:		2016年9月14日 下午8:43:02 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.weixin;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class Name:		TestWeixinConstants<br/>
 * Description:		[description]
 * @time				2016年9月14日 下午8:43:02
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/context/application-context.xml"})
public class TestWeixinConstants {
	@Autowired
	private WeixinConstants weixinConstants;
	
	@Test
	public void test() {
		System.out.println(weixinConstants.KF_OPENIDS);
		assertNotNull(weixinConstants.KF_OPENIDS);
	}
}
