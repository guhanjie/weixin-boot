package com.guhanjie.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.guhanjie.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/test-application-context.xml"})
public class TestUserMapper {
	private String tableName;
	private Logger logger = LoggerFactory.getLogger(TestUserMapper.class);
	@Autowired
	private UserMapper mapper;
	
	@Before
	public void setup() throws Exception {
		tableName = "user";
	}
	
	@Test
	public void testCRUD() {
		//Create
		logger.debug("Create one record to table[{}]...\n", tableName);
		User model = new User();
		model.setName("guhanjie");
		model.setSex("m");
		long insertCount = mapper.insertSelective(model);
		assertEquals(insertCount, 1L);
		//Retrieve
		logger.debug("Select one record from table[{}]...\n", tableName);
		model = mapper.selectByPrimaryKey(model.getId());
		logger.debug(JSON.toJSONString(model, true));
		//Update
		logger.debug("Update one record in table[{}]...\n", tableName);
		model.setSex("f");
		long updateCount = mapper.updateByPrimaryKeySelective(model);
		logger.debug("Update [{}] record(s) in table[{}]...\n", updateCount, tableName);
		//Delete
		logger.debug("Delete one record from table[{}]...\n", tableName);
		long deleteCount = mapper.deleteByPrimaryKey(model.getId());
		assertEquals(deleteCount, 1L);
	}
	
	@Test
	public void testGeneratedKey() {
		logger.debug("Create one record to table[{}]...\n", tableName);
		User model = new User();
		model.setName("guhanjie");
		model.setSex("m");
		long insertCount = mapper.insertSelective(model);
		logger.debug(JSON.toJSONString(model));
		assertEquals(insertCount, 1L);
	}

}
