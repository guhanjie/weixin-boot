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
import com.guhanjie.util.IdGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/test-application-context.xml"})
public class UserMapperTest {
	private String tableName;
	private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
	@Autowired
	private UserMapper userMapper;
	
	@Before
	public void setUp() throws Exception {
		tableName = "user";
	}
	
	@Test
	public void testCRUD() {
		long id = IdGenerator.getInstance().nextId();
		//Create
		logger.debug("Create one record to table[{}]...\n", tableName);
		User model = new User();
		model.setId(id);
		model.setName("guhanjie");
		model.setSex("m");
		long insertCount = userMapper.insertSelective(model);
		assertEquals(insertCount, 1L);
		//Retrieve
		logger.debug("Select one record from table[{}]...\n", tableName);
		model = userMapper.selectByPrimaryKey(model.getId());
		logger.debug(JSON.toJSONString(model, true));
		//Update
		logger.debug("Update one record in table[{}]...\n", tableName);
		model.setSex("f");
		long updateCount = userMapper.updateByPrimaryKeySelective(model);
		logger.debug("Update [{}] record(s) in table[{}]...\n", updateCount, tableName);
		//Delete
		logger.debug("Delete one record from table[{}]...\n", tableName);
		long deleteCount = userMapper.deleteByPrimaryKey(model.getId());
		assertEquals(deleteCount, 1L);
	}

}
