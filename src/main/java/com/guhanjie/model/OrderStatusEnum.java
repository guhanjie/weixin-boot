/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.model 
 * File Name:			OrderStatusEnum.java 
 * Create Date:		2016年10月1日 下午9:57:33 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.model;

/**
 * Class Name:		OrderStatusEnum<br/>
 * Description:		[订单状态，采用位表示法，第1-2位：是否下单成功，第3-4位：是否开始送货，第5-6位：是否支付完成]
 * @time				2016年10月1日 下午9:57:33
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public enum OrderStatusEnum {
    //位表示法  
//  private static final short PUT_ORDER = 0x01;        //下单与（否）
//  private static final short CANCEL_ORDER = 0x02;     //取消与（否）
//  private static final short START_SEND = 0x04;       //开始送货与（否）
//  private static final short FINISH_ORDER = 0x08;     //完成与（否）
//  private static final short PAY_ORDER = 0x10;        //支付与（否）
    
    NEW((short)0x01, "新建订单"),           //1
    CANCEL((short)0x03, "取消订单"),    //3
    SENDING((short)0x05, "开始送货"),   //5
    FINISH((short)0x0D, "送货完成"),        //13
    PAYED((short)0x1D, "支付完成");     //29
    
    private short code;
    private String desc;
    
    private OrderStatusEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public short code() {
        return code;
    }
    
    public String desc() {
        return desc;
    }
}
