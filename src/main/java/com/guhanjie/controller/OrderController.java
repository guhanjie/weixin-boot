/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.controller 
 * File Name:			OrderController.java 
 * Create Date:		2016年9月1日 上午10:32:19 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.guhanjie.model.Order;
import com.guhanjie.model.Position;
import com.guhanjie.model.User;
import com.guhanjie.service.OrderService;
import com.guhanjie.service.UserService;

/**
 * Class Name:		OrderController<br/>
 * Description:		[description]
 * @time				2016年9月1日 上午10:32:19
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String order(HttpServletRequest req) {
		return "order";
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> putOrder(HttpServletRequest req) {
		LOGGER.info("putting new order for user[]...", JSON.toJSONString(getUser(req)));
		//获取用户信息
		String openid = req.getParameter("open_id");
		User user = userService.getUserByOpenId(openid);
		//获取起始位置信息
		String fromAddress = req.getParameter("from_address");
		String fromLng = req.getParameter("from_lng");
		String fromLat = req.getParameter("from_lat");
		String fromDetail = req.getParameter("from_detail");
		short fromFloor = (short)Integer.parseInt(req.getParameter("from_floor"));
		Position from = new Position();
		from.setAddress(fromAddress);
		from.setDetail(fromDetail);
		from.setFloor(fromFloor);
		from.setGeoLng(fromLng);
		from.setGeoLat(fromLat);
		//获取目的位置信息
		String toAddress = req.getParameter("to_address");
		String toLng = req.getParameter("to_lng");
		String toLat = req.getParameter("to_lat");
		String toDetail = req.getParameter("to_detail");
		short toFloor = (short)Integer.parseInt(req.getParameter("to_floor"));
		Position to = new Position();
		to.setAddress(toAddress);
		to.setDetail(toDetail);
		to.setFloor(toFloor);
		to.setGeoLng(toLng);
		to.setGeoLat(toLat);		
		//获取订单详细信息
		String _amount = req.getParameter("amount");
		BigDecimal amount = new BigDecimal(StringUtils.isBlank(_amount) ? "0" : _amount);
		String _tip = req.getParameter("tip");
		BigDecimal tip = new BigDecimal(StringUtils.isBlank(_tip) ? "0" : _tip);
		String _distance = req.getParameter("distance");
		BigDecimal distance = new BigDecimal(StringUtils.isBlank(_distance) ? "0" : _distance);
		short vehicle = (short)Integer.parseInt(req.getParameter("vehicle"));
		String contactor = req.getParameter("contactor");
		String phone = req.getParameter("phone");
		String remark = req.getParameter("remark");		
		String _startTime = req.getParameter("start_time");
		Date startTime = StringUtils.isBlank(_startTime) ? new Date() : new Date(Long.parseLong(_startTime));
		//封装信息
		Order order = new Order();
		order.setUser(user);
		order.setAmount(amount);
		order.setTip(tip);
		order.setFrom(from);
		order.setTo(to);
		order.setStartTime(startTime);
		order.setDistance(distance);
		order.setVehicle(vehicle);
		order.setContactor(contactor);
		order.setPhone(phone);
		order.setRemark(remark);
		
		//下单
		orderService.putOrder(order);
		return success();
	}
	
	@RequestMapping(value="search",method=RequestMethod.GET)
	public String searchOrder(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(AppConstants.SESSION_KEY_USER);
//		User user = new User();
//		user.setId(4);
		List<Order> orders = orderService.getOrdersByUser(user);
		model.addAttribute("orders", orders);
		return "order_search";
	}
}
