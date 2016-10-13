/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.mapper 
 * File Name:			TestOrderMapper.java 
 * Create Date:		2016年10月2日 下午5:10:12 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.mapper;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.guhanjie.model.Order;

/**
 * Class Name:		TestOrderMapper<br/>
 * Description:		[description]
 * @time				2016年10月2日 下午5:10:12
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/test-application-context.xml"})
public class TestOrderMapper {
    private String tableName;
    private Logger logger = LoggerFactory.getLogger(TestOrderMapper.class);
    @Autowired
    private OrderMapper mapper;
    
    @Before
    public void setup() throws Exception {
        tableName = "order";
    }
    
    @Test
    public void testSelectByUserId() {
        logger.debug("Select order by userid from table[{}]...\n", tableName);
        List<Order> model = mapper.selectByUserId(3);
        logger.debug(JSON.toJSONString(model, true));
        assertNotNull(model);
    }

    @Test
    public void testSelectByUserPhone() {
        logger.debug("Select order by user phone from table[{}]...\n", tableName);
        List<Order> model = mapper.selectByUserPhone("13052333613");
        logger.debug(JSON.toJSONString(model, true));
        assertNotNull(model);
    }
    
    @Test
    public void testSelectByUserOpenId() {
        logger.debug("Select order by user openid from table[{}]...\n", tableName);
        List<Order> model = mapper.selectByUserOpenId("o_05UwXR65RZ-VeZ12CfLH27UiEk");
        logger.debug(JSON.toJSONString(model, true));
        assertNotNull(model);
    }
    
    @Test
    public void testSelectByPage() {
        logger.debug("Select order by page from table[{}]...\n", tableName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offset", 3);
        map.put("pagesize", 3);
        List<Order> model = mapper.selectByQualifiedPage(map);
        logger.debug(JSON.toJSONString(model, true));
        assertNotNull(model);
    }
}
