package com.guhanjie.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guhanjie.exception.WebExceptionEnum;
import com.guhanjie.exception.WebExceptionFactory;
import com.guhanjie.mapper.UserMapper;
import com.guhanjie.model.User;
import com.guhanjie.util.TTLCache;

@Service
public class UserService {	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private static final TTLCache<String, User> CACHE = new TTLCache<String, User>(60 * 20); //失效时间为20分钟
	
	@Autowired
	private UserMapper userMapper;
	
	public User getUserById(int id) {
		LOGGER.debug("Get user[{}]...", id);
		return userMapper.selectByPrimaryKey(id);
	}
	
	public int addUser(User user) {
	    if(user.getOpenId() != null) {
	        User u = userMapper.selectByOpenId(user.getOpenId());
	        if(u != null) {
	            LOGGER.error("user has exist, openid:[{}]", user.getOpenId());
	            throw WebExceptionFactory.exception(WebExceptionEnum.USER_HAS_EXIST);
	        }
	    }
		return userMapper.insertSelective(user);
	}

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    
    public void updateToCache(User user) {
        if(user != null && StringUtils.isBlank(user.getOpenId())) {
            CACHE.put(user.getOpenId(), user);
        }            
    }
    
    public User getFromCache(String openid) {
        return CACHE.get(openid);
    }
}
