package com.guhanjie.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.guhanjie.model.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/test-application-context.xml"})
public class PositionMapperTest {
	private String tableName;
	private Logger logger = LoggerFactory.getLogger(PositionMapperTest.class);
	@Autowired
	private PositionMapper mapper;
	
	@Before
	public void setup() throws Exception {
		tableName = "position";
	}
	
	@Test
	public void testSelectByGeo() {
		String lat = "31.186951";
		String lng = "121.570592";
		Position model = mapper.selectByGeo(lng, lat);
		logger.debug(JSON.toJSONString(model));
		assertNotNull(model);
	}
	
	@Test
	public void testGeneratedKey() {
		logger.debug("Create one record to table[{}]...\n", tableName);
		Position model = new Position();
		model.setAddress("莲泰苑-东南门");
		model.setDetail("4号602室");
		model.setFloor((short)6);
		model.setGeoLat("31.186951");
		model.setGeoLng("121.570592");
		long insertCount = mapper.insertSelective(model);
		logger.debug(JSON.toJSONString(model));
		assertEquals(insertCount, 1L);
	}

}
