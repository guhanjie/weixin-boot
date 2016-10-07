/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			TestMessageKit.java 
 * Create Date:		2016年9月16日 下午1:10:11 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guhanjie.model.Order;
import com.guhanjie.model.Position;
import com.guhanjie.model.VehicleEnum;
import com.guhanjie.weixin.msg.MessageKit;

/**
 * Class Name:		TestMessageKit<br/>
 * Description:		[description]
 * @time				2016年9月16日 下午1:10:11
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/context/db-mysql.xml", "classpath:/context/application-context.xml"})
public class TestMessageKit {
    
    @Autowired
    WeixinConstants weixinConstants;
    
    @Test
    public void testSendKFMsg() {
        Order order = new Order();
        order.setAmount(new BigDecimal(307.0));
        order.setContactor("顾汉杰");
        order.setDistance(new BigDecimal(34.0));
        Position from = new Position();
        from.setAddress("莲泰苑");
        from.setDetail("4号602室");
        from.setFloor((short)6);
        order.setFrom(from);
        Position to = new Position();
        to.setAddress("莲泰苑");
        to.setDetail("5号502室");
        to.setFloor((short)5);
        order.setTo(to);
        order.setPhone("13052333613");
        order.setRemark("测试");
        order.setStartTime(new Date());
        order.setVehicle(VehicleEnum.JINBEI.code());
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
}
