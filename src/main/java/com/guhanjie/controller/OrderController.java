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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.guhanjie.exception.WebException;
import com.guhanjie.exception.WebExceptionEnum;
import com.guhanjie.exception.WebExceptionFactory;
import com.guhanjie.model.Order;
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
	
	@RequestMapping(value={"", "pre"},method=RequestMethod.GET)
	public String order(HttpServletRequest req, HttpServletResponse resp, @RequestParam(required=false) String phone) {
	    resp.setHeader("Cache-Control", "no-cache");
	    if(getSessionUser()==null && StringUtils.isNotBlank(phone)) {
    	    User user = userService.getUserByPhone(phone);
    	    if(user != null) {
    	        setSessionUser(user);
    	        req.getSession().setAttribute(AppConstants.SESSION_KEY_OPEN_ID, user.getOpenId());
    	    }
	    }
		return "order";
	}
	
	@RequestMapping(value="put",method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Map<String, Object> putOrder(HttpServletRequest req, 
    	                                                    @RequestParam("open_id") String openid, 
    	                                                    @RequestBody Order order) {
		LOGGER.info("putting new order for user open_id[{}]...", openid);
		LOGGER.info("=====order:[{}]...", JSON.toJSONString(order, true));
		//获取用户信息
		User user = getSessionUser();
		if(user == null) {
		    user = userService.getUserByOpenId(openid);
		}
		if(user == null) {
		    user = new User();
		    user.setPhone(order.getPhone());
		    setSessionUser(user);
		}
		//封装信息
		order.setUser(user);
		
		//下单
		orderService.putOrder(order);
		return success();
	}

    @RequestMapping(value="list",method=RequestMethod.GET)
    public String listOrders(HttpServletRequest req, Model model, 
                    @RequestParam(required=false) String beginDate, //yyyy-mm-dd
                    @RequestParam(required=false) String endDate,    //yyyy-mm-dd
                    @PageableDefault(page=0, size=5) Pageable pageable) {        
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
		User user = getSessionUser();
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
	public Map<String, Object> payOrder(HttpServletRequest req, final Integer orderid, final Integer tip) {
	    final String APPID = weixinContants.APPID;
	    final String MCH_ID = weixinContants.MCH_ID;
	    final String MCH_KEY = weixinContants.MCH_KEY;
	    //查询订单
        final Order order = orderService.getOrderById(orderid);
        if(order == null) {
            return fail("无效的订单号");
        }
        //获取订单的微信预支付id
        String prepayid = order.getPayId();
        try {
            if(prepayid != null) {  //若先前发起过支付，需要验证prepayid是否已关闭
                Map<String, String>map = PayKit.search(order, APPID, MCH_ID, MCH_KEY);
                String payStatus = map.get("result");
                if("CLOSED".equals(payStatus)) {        //若该prepayid已失效则重新下单
                    prepayid = null;
                }
            }
            if(prepayid == null) {  //第一次发起支付或支付已关闭
                //设置订单小费（只有在第一次支付时可以设，数据模型有问题，建议订单和支付单独两个表，1:N关系）
                if(tip != null) {
                    order.setTip(new BigDecimal(tip));
                }
                prepayid = PayKit.unifiedorder(req, order, APPID, MCH_ID, MCH_KEY);
                orderService.updatePayInfo(order);
            }
            //定时（10分钟）查询订单支付情况
        	long now = new Date().getTime();
        	taskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map;
                    try {
                        map = PayKit.search(order, APPID, MCH_ID, MCH_KEY);
                        String result = map.get("result");
                        Integer orderid = Integer.valueOf(map.get("order_id"));
                        String total_fee = map.get("total_fee");
                        String time_end = map.get("time_end");
                        boolean success = "SUCCESS".equals(result);                    
                        orderService.updateOrderByPay(success, orderid, total_fee, time_end);
                    }
                    catch (Exception e) {
                        LOGGER.error("error in search pay result for order[{}].", orderid, e);
                    }
                }
        	}, new Date(now+10*60*1000));       //产生支付预付单后的10分钟查询，以防微信支付回调没有接收到。
        }
        catch (IOException e) {
            LOGGER.error("error in unified weixin pay for order[{}].", order.getId());
        }
        
        if(StringUtils.isBlank(prepayid)) {
        	throw WebExceptionFactory.exception(WebExceptionEnum.PAY_ERROR, "支付系统有误，目前无法支付");
        }
        Map<String, String> payParams = new HashMap<String, String>();
        final String nonceStr = String.valueOf(new Random().nextInt(10000));
        payParams.put("appId", APPID);                                  //公众号id
        payParams.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));         //时间戳
        payParams.put("nonceStr", nonceStr);                                                   //随机字符串
        payParams.put("package", "prepay_id="+prepayid);                              //订单详情扩展字符串
        payParams.put("signType", "MD5");                                                       //签名方式
        payParams.put("paySign", PayKit.sign(payParams, MCH_KEY)); //签名        
		return success(payParams);
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
