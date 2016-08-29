package com.guhanjie.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guhanjie.mapper.UserMapper;
import com.guhanjie.model.User;

@Service
public class UserService {	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	//@Autowired
	private UserMapper userMapper;
	
	public User getUser(Long id) {
		LOGGER.info("Get user[{}]...", id);
		return userMapper.selectByPrimaryKey(id);
	}
}
