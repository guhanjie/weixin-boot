/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.service 
 * File Name:			OrderService.java 
 * Create Date:		2016年9月1日 下午1:46:59 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.guhanjie.exception.WebExceptionEnum;
import com.guhanjie.exception.WebExceptionFactory;
import com.guhanjie.mapper.OrderMapper;
import com.guhanjie.mapper.PositionMapper;
import com.guhanjie.model.Order;
import com.guhanjie.model.Order.VehicleEnum;
import com.guhanjie.model.Position;
import com.guhanjie.model.User;

/**
 * Class Name:		OrderService<br/>
 * Description:		[description]
 * @time				2016年9月1日 下午1:46:59
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */

@Service
public class OrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PositionMapper positionMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	public void putOrder(Order order) {
		if(order == null) {
			LOGGER.error("put order error, order is null");
			throw WebExceptionFactory.exception(WebExceptionEnum.PARAMETER_NULL);
		}
		// 1. 检查用户信息 
		User user = order.getUser();
		if(user == null) {	//若用户id不存在，说明首次进入，添加user记录
			LOGGER.warn("user first in while putting order:[{}]", order.getId());
			if(StringUtils.isBlank(order.getPhone())) {
				LOGGER.error("put order error, user not exist, order:[{}]", order.getId());
				throw WebExceptionFactory.exception(WebExceptionEnum.USER_NOT_EXIST);
			}
			user = new User();
			user.setName(order.getContactor());
			user.setPhone(order.getPhone());
			LOGGER.debug("Add an new user:[{}]", JSON.toJSONString(user));
			userService.addUser(user);
		}
		order.setUserId(user.getId());
		order.setUser(user);
		if(StringUtils.isBlank(order.getContactor())) {
			String username = StringUtils.isBlank(user.getName()) ? user.getNickname() : user.getName();
			order.setContactor(username);
		}
		if(StringUtils.isBlank(order.getPhone())) {
			order.setContactor(user.getPhone());
		}
		// 2. 检查位置信息
		// 起始地信息
		Position from = order.getFrom();
		if(from == null) {
			LOGGER.warn("put order error, from info not exist, order:[{}]", order.getId());
			throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "缺失起始地信息");
		}
		positionMapper.insertSelective(from);
		order.setFromId(from.getId());
		order.setFrom(from);
		// 目的地信息
		Position to = order.getTo();
		if(to == null) {
			LOGGER.warn("put order error, to info not exist, order:[{}]", order.getId());
			throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "缺失目的地信息");
		}
		positionMapper.insertSelective(to);
		order.setToId(to.getId());
		order.setTo(to);
		// 3. 校验订单有效性
		double price = 0.0;
		double amount = order.getAmount().doubleValue();
        double distance = order.getDistance().doubleValue();
        short fromFloor = from.getFloor();
        short toFloor = to.getFloor();
		Short vehicle = order.getVehicle();
		if(vehicle == null) {
		    throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "缺失车型信息");
		}
		if(VehicleEnum.XIAOMIAN.code() == vehicle) {    //小面车
		    price = 150.0; //起步价150（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*5.0;  //超出后每公里5元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : (toFloor-1)*10.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*10.0;
                }
            }
        }
		else if(VehicleEnum.JINBEI.code() == vehicle) {    //金杯车
		    price = 200.0; //起步价200（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*6.0;  //超出后每公里6元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*10.0; //电梯和1楼搬运免费，每多1层加收10元
            price += (toFloor<2) ? 0.0 : (toFloor-1)*10.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*10.0;
                }
            }
        }
		else if(VehicleEnum.QUANSHUN.code() == vehicle) {   //全顺/依维轲
		    price = 300.0; //起步价300（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*8.0;  //超出后每公里8元
            price += 50.0; //电梯和1楼搬运按50元收取，每多1层加收20元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*20.0;
            price += (toFloor<2) ? 0.0 : (toFloor-1)*20.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*20.0;
                }
            }
		}
		else {    //车型参数非法
            throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "车型信息有误");
		}
		//校验金额（计算金额与前端展示金额误差在1.0以内）
		if(Math.abs(price-amount) > 1.0) {
		    throw WebExceptionFactory.exception(WebExceptionEnum.VALIDATE_ERROR, "订单金额有误");
		}
		
		// 4. 生成订单
		orderMapper.insertSelective(order);
	}
	
}
