/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.service 
 * File Name:			OrderService.java 
 * Create Date:		2016年9月1日 下午1:46:59 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.guhanjie.exception.WebExceptionEnum;
import com.guhanjie.exception.WebExceptionFactory;
import com.guhanjie.mapper.OrderMapper;
import com.guhanjie.mapper.PositionMapper;
import com.guhanjie.model.Order;
import com.guhanjie.model.OrderStatusEnum;
import com.guhanjie.model.PayStatusEnum;
import com.guhanjie.model.Position;
import com.guhanjie.model.User;
import com.guhanjie.model.VehicleEnum;
import com.guhanjie.util.DateTimeUtil;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.msg.MessageKit;

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
	
	@Autowired
	private WeixinConstants weixinConstants;
	
	public void putOrder(Order order) {
		if(order == null) {
			LOGGER.error("put order error, order is null");
			throw WebExceptionFactory.exception(WebExceptionEnum.PARAMETER_NULL);
		}
		// 1. 检查用户信息 
		User user = order.getUser();
		if(user == null) {	//若用户不存在，首次进入添加user记录
			if(StringUtils.isBlank(order.getPhone())) {
				LOGGER.error("put order error, user not exist, order:[{}]", order.getId());
				throw WebExceptionFactory.exception(WebExceptionEnum.USER_NOT_EXIST);
			}
			user = userService.getUserByPhone(order.getPhone());
			if(user == null) {
	            user = new User();
	            user.setName(order.getContactor());
	            user.setPhone(order.getPhone());
	            user.setCreateTime(new Date());
	            LOGGER.info("user first in while putting order, add an new user:[{}]", JSON.toJSONString(user));
	            userService.addUser(user);
			}
		}
		if(StringUtils.isBlank(user.getPhone())) {    //用户信息默认不含手机号码，第一次用户填写信息时记录手机号码
		    String phone = order.getPhone();
		    if(StringUtils.isBlank(phone)) {
		        throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "缺失联系方式");
		    }
		    user.setPhone(phone);
		    userService.updateUser(user);
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
        double distance = Math.ceil(order.getDistance().doubleValue());
        short fromFloor = from.getFloor();
        short toFloor = to.getFloor();
        int workers = order.getWorkers();
		Short vehicle = order.getVehicle();
		if(vehicle == null) {
		    throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "缺失车型信息");
		}
		if(VehicleEnum.XIAOMIAN.code() == vehicle) {    //小面车
		    price = 150.0; //起步价150（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*5.0;  //超出后每公里5元
            price += (fromFloor<2) ? 0.0 : 10.0+(fromFloor-1)*10.0; //电梯和1楼搬运免费，2楼20元，每多1层加收10元
            price += (toFloor<2) ? 0.0 : 10.0+(toFloor-1)*10.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*10.0;
                }
            }
            price += workers>1 ? (workers-1)*150 : 0;
        }
		else if(VehicleEnum.JINBEI.code() == vehicle) {    //金杯车
		    price = 200.0; //起步价200（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*6.0;  //超出后每公里6元
            price += (fromFloor<2) ? 0.0 : 10.0+(fromFloor-1)*10.0; //电梯和1楼搬运免费，2楼20元，每多1层加收10元
            price += (toFloor<2) ? 0.0 : 10.0+(toFloor-1)*10.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*10.0;
                }
            }
            price += workers>1 ? (workers-1)*150 : 0;
        }
		else if(VehicleEnum.QUANSHUN.code() == vehicle) {   //全顺/依维轲
		    price = 300.0; //起步价300（10公里内）
            price += (distance<10) ? 0.0 : (distance-10)*8.0;  //超出后每公里8元
            price += (fromFloor==0?0.0:50.0) + (toFloor==0?0.0:50.0); //电梯和1楼搬运按50元收取，每多1层加收20元
            price += (fromFloor<2) ? 0.0 : (fromFloor-1)*20.0;
            price += (toFloor<2) ? 0.0 : (toFloor-1)*20.0;
            if(order.getWaypoints() != null) {
                for(Position p : order.getWaypoints()) {
                    price += 50.0; //每增加一个点位装卸货，增加50元
                    price += (p.getFloor()<2) ? 0.0 : (p.getFloor()-1)*20.0;
                }
            }
            price += workers>1 ? (workers-1)*150 : 0;
		}
		else {    //车型参数非法
            throw WebExceptionFactory.exception(WebExceptionEnum.DATA_NOT_WELL, "车型信息有误");
		}
		//校验金额（计算金额与前端展示金额误差在1.0以内）
		if(Math.abs(price-amount) > 1.0) {
		    throw WebExceptionFactory.exception(WebExceptionEnum.VALIDATE_ERROR, "订单金额有误");
		}
		
		// 4. 生成订单
		order.setCreateTime(new Date());
		order.setStatus(OrderStatusEnum.NEW.code());
		orderMapper.insertSelective(order);
		
		// 5. 发送微信消息给客户
		StringBuffer sb = new StringBuffer("主人，您有新的订单：\n");
		sb.append("订单金额：").append(order.getAmount()).append("元\n");
		sb.append("路程：").append(order.getDistance()).append("公里\n");
		sb.append("联系人：").append(order.getContactor()).append("\n");
		sb.append("电话：").append(order.getPhone()).append("\n");
		sb.append("车型：").append(VehicleEnum.valueOf(order.getVehicle()).desc()).append("\n");
		sb.append("预订时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getStartTime())).append("\n");
		sb.append("起始地：").append(order.getFrom().getAddress()).append(" ").append(order.getFrom().getDetail()).append(" 第").append(order.getFrom().getFloor()).append("楼\n");
		sb.append("目的地：").append(order.getTo().getAddress()).append(" ").append(order.getTo().getDetail()).append(" 第").append(order.getTo().getFloor()).append("楼\n");
		sb.append("备注：").append(order.getRemark()).append("\n");
		MessageKit.sendKFMsg(weixinConstants.KF_OPENIDS, sb.toString());
	}
	
	public PageImpl<Order> listOrders(Date beginTime, Date endTime, Pageable pageable) {
	  //查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        if(beginTime != null) {
            param.put("beginTime", beginTime);
        }
        if(endTime != null) {
            param.put("endTime", endTime);
        }
        param.put("offset", pageable.getOffset());
        param.put("pagesize", pageable.getPageSize());
        
        int total = orderMapper.countSelective(param);
        List<Order> list = orderMapper.selectByQualifiedPage(param);
        PageImpl<Order> page = new PageImpl<Order>(list, pageable, total);
        return page;
	}
	
	public List<Order> getOrdersByUser(User user) {
		List<Order> result = null;
		if(user!=null && user.getId()!=null) {
			result = orderMapper.selectByUserId(user.getId());
		}
		return result;
	}
	
	public Order getOrderById(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}
	
	public void updateOrderByPay(boolean success, Integer orderid, String total_fee, String time_end) {
	    LOGGER.info("Updating order[{}] pay info: sucess=[{}], total_fee=[{}], time_end=[{}]", orderid, success, total_fee, time_end);
	    Order order = getOrderById(orderid);
	    if(order==null) {
	        LOGGER.warn("order[{}] not exists", orderid);
	        return;
	    }
	    //一旦订单状态到达“支付成功”，就不再进行后续处理
	    short oldOrderStatus = order.getStatus();
	    short oldPayStatus = order.getPayStatus();
	    if(oldPayStatus == PayStatusEnum.SUCCESS.code()) {
	        LOGGER.info("order[{}] pay has succeed, no more to handler.", orderid);
	        return;
	    }
        if(success) {
            if(total_fee==null || time_end==null) {
                LOGGER.warn("error in updating order[{}] pay, as pay info not complete: total_fee=[{}], time_end=[{}].", orderid, total_fee, time_end);
                return;
            }
            if(order.getAmount().intValue() == Integer.valueOf(total_fee)/100) {
                LOGGER.info("Success to complete order[{}] pay!", orderid);
                order.setStatus(OrderStatusEnum.PAYED.code());
                order.setPayStatus(PayStatusEnum.SUCCESS.code());
                order.setPayTime(DateTimeUtil.getDate(time_end, "yyyyMMddHHmmss"));
            }
        }
        else {
            LOGGER.warn("Pay exception, amount not matched: topay=[{}], payed=[{}]", order.getAmount(), total_fee);
            order.setPayStatus(PayStatusEnum.PAYERROR.code());
        }
        //更新订单支付状态
        LOGGER.info("===updating order[{}] status:[{}]-->[{}], pay status: [{}]-->[{}].", orderid, order.getStatus(), oldOrderStatus, order.getPayStatus(), oldPayStatus);
        if(1 == orderMapper.updateByPayStatus(order, oldOrderStatus, oldPayStatus)) {
            LOGGER.info("Success to update order[{}] pay status: [{}]-->[{}].", orderid, order.getPayStatus(), oldPayStatus);
        }
        else {
            LOGGER.warn("Failed to update order[{}] pay status, as already been updated before", orderid);
        }
	}
	
	public boolean cancelOrder(Order order) {
        if(order == null) {
            LOGGER.warn("order can not be null.");
            return false;
        }
        LOGGER.info("cancelling order[{}]...", order.getId());
		long startTime = order.getStartTime().getTime();
		long now = System.currentTimeMillis();
		//只有订单状态为新建，且距离服务时间4小时之前，才可取消订单
		if(order.getStatus()==OrderStatusEnum.NEW.code() && (startTime-now) > 4*60*60*1000) {
			order.setStatus(OrderStatusEnum.CANCEL.code());
			order.setUpdateTime(new Date());
			if(orderMapper.updateByStatus(order, OrderStatusEnum.NEW.code()) == 1) {
			    LOGGER.info("success to cancel order[{}]", order.getId());
				return true;
			}
		}
		throw WebExceptionFactory.exception(WebExceptionEnum.ORDER_CANCEL_ERROR, "当前订单状态无法取消");
	}
	
	public boolean finishOrderPay(Order order) {
	    if(order == null) {
	        LOGGER.warn("order can not be null.");
	        return false;
	    }
	    LOGGER.info("finishing pay order[{}]...", order.getId());    
        short oldOrderStatus = order.getStatus();
        short oldPayStatus = order.getPayStatus();
        order.setStatus(OrderStatusEnum.PAYED.code());
        order.setPayStatus(PayStatusEnum.SUCCESS.code());
        order.setPayTime(new Date());
        if(1 == orderMapper.updateByPayStatus(order, oldOrderStatus, oldPayStatus)) {
            return true;
        }
        throw WebExceptionFactory.exception(WebExceptionEnum.PAY_ERROR, "当前订单支付出错");
    }
	
}
