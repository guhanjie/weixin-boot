/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.controller 
 * File Name:			OrderController.java 
 * Create Date:		2016年9月1日 上午10:32:19 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.guhanjie.exception.WebException;
import com.guhanjie.exception.WebExceptionEnum;
import com.guhanjie.exception.WebExceptionFactory;
import com.guhanjie.model.Order;
import com.guhanjie.model.Position;
import com.guhanjie.model.User;
import com.guhanjie.service.OrderService;
import com.guhanjie.service.UserService;
import com.guhanjie.util.DateTimeUtil;
import com.guhanjie.weixin.WeixinConstants;
import com.guhanjie.weixin.pay.PayKit;

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
    private WeixinConstants weixinContants;
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Resource(name="paySearchScheduler")
	private TaskScheduler taskScheduler;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String order(HttpServletRequest req) {
		return "order";
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> putOrder(HttpServletRequest req) {
		LOGGER.info("putting new order for user[{}]...", JSON.toJSONString(getUser(req)));
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
        //获取途径点位置信息
		String waypoints = req.getParameter("waypoints[0]");
		LOGGER.info(waypoints);
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
		Integer workers = Integer.parseInt(req.getParameter("workers"));
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
		order.setWorkers(workers);
		order.setRemark(remark);
		
		//下单
		orderService.putOrder(order);
		return success();
	}

    @RequestMapping(value="list",method=RequestMethod.GET)
    public String listOrders(HttpServletRequest req, Model model, 
                    @RequestParam(required=false) String beginDate, //yyyy-mm-dd
                    @RequestParam(required=false) String endDate,    //yyyy-mm-dd
                    @PageableDefault(page=0, size=10) Pageable pageable) {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(AppConstants.SESSION_KEY_USER);
        
        Date beginTime = null;
        Date endTime = null;
        if(StringUtils.isNotBlank(beginDate)){
            beginTime = DateTimeUtil.getDate(beginDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        }       
        if(StringUtils.isNotBlank(endDate)){
            endTime = DateTimeUtil.getDate(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        }
        PageImpl<Order> page = orderService.listOrders(beginTime, endTime, pageable);

        model.addAttribute("orders", page.getContent());
        model.addAttribute("current", page.getNumber());
        model.addAttribute("pages", page.getTotalPages());
        model.addAttribute("now", new Date());
        return "order_list";
    }
	
	@RequestMapping(value="search",method=RequestMethod.GET)
	public String searchOrder(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(AppConstants.SESSION_KEY_USER);
//		User user = new User();
//		user.setId(3);
		List<Order> orders = orderService.getOrdersByUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("now", new Date());
		return "order_search";
	}
	
	@RequestMapping(value="cancel",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelOrder(Integer orderid) {
		Order order = orderService.getOrderById(orderid);
		if(order == null) {
			return fail("无效的订单号");
		}
		try {
			orderService.cancelOrder(order);
			return success();
		} catch(WebException e) {
			return fail(e.getScreenMessage());
		}
	}
	
	@RequestMapping(value="payed",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> finishOrderPay(Integer orderid) {
	    Order order = orderService.getOrderById(orderid);
	    if(order == null) {
	        return fail("无效的订单号");
	    }
	    try {
	        orderService.finishOrderPay(order);
	        return success();
	    } catch(WebException e) {
	        return fail(e.getScreenMessage());
	    }
	}
	
	@RequestMapping(value="pay",method=RequestMethod.GET)
    @ResponseBody
	public Map<String, Object> payOrder(HttpServletRequest req, final Integer orderid) {
	    final String APPID = weixinContants.APPID;
	    final String MCH_ID = weixinContants.MCH_ID;
	    final String MCH_KEY = weixinContants.MCH_KEY;
        final Order order = orderService.getOrderById(orderid);
        if(order == null) {
            return fail("无效的订单号");
        }
        String prepayId = null;
        try {
        	prepayId = PayKit.unifiedorder(req, order, APPID, MCH_ID, MCH_KEY);
        	long now = new Date().getTime();
        	taskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map;
                    try {
                        map = PayKit.search(order, APPID, MCH_ID, MCH_KEY);
                        String result = map.get("result");
                        Integer orderid = Integer.valueOf(map.get("out_trade_no"));
                        String total_fee = map.get("total_fee");
                        String time_end = map.get("time_end");
                        boolean success = "SUCCESS".equals(result);                    
                        orderService.updateOrderByPay(success, orderid, total_fee, time_end);
                    }
                    catch (IOException e) {
                        LOGGER.error("error in search order[{}] pay.", orderid, e);
                    }
                }
        	}, new Date(now+10*60*1000));       //产生支付预付单后的10分钟查询，以防微信支付回调没有接收到。
        }
        catch (IOException e) {
            LOGGER.error("error in weixin pay unified order.");
        }
        if(StringUtils.isBlank(prepayId)) {
        	throw WebExceptionFactory.exception(WebExceptionEnum.PAY_ERROR, "支付系统有误，目前无法支付");
        }
        final String nonceStr = String.valueOf(new Random().nextInt(10000));
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", weixinContants.APPID);                                  //公众号id
        map.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));         //时间戳
        map.put("nonceStr", nonceStr);                                                   //随机字符串
        map.put("package", "prepay_id="+prepayId);                              //订单详情扩展字符串
        map.put("signType", "MD5");                                                       //签名方式
        map.put("paySign", PayKit.sign(map, weixinContants.MCH_KEY)); //签名
        
		return success(map);
	}
	
	@RequestMapping(value="paycallback",method=RequestMethod.GET)
	public void paycallback(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LOGGER.debug("getting callback from weixin pay...");
		
		Map<String, String> map = PayKit.callback(req, weixinContants.MCH_KEY);
		String result = map.get("result");
		Integer orderid = Integer.valueOf(map.get("out_trade_no"));
		String total_fee = map.get("total_fee");
		String time_end = map.get("time_end");
		boolean success = "SUCCESS".equals(result);
		
		orderService.updateOrderByPay(success, orderid, total_fee, time_end);
		
        resp.setContentType("application/xml;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String respCon = "<xml>"
        						+ "	<return_code><![CDATA[SUCCESS]]></return_code>"
        						+ "	<return_msg><![CDATA[OK]]></return_msg>"
        						+ "</xml>";
        LOGGER.debug("Weixin msg response= "+respCon);
        resp.getWriter().write(respCon);
		resp.getWriter().flush();
	}
}
