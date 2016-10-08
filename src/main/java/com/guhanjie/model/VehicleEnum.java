/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.model 
 * File Name:			VehicleEnum.java 
 * Create Date:		2016年10月1日 下午9:58:30 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.model;

/**
 * Class Name:		VehicleEnum<br/>
 * Description:		[车型（1：小面车， 2：金杯车，3：全顺）]
 * @time				2016年10月1日 下午9:58:30
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public enum VehicleEnum {
    XIAOMIAN((short)1, "小面车型"),
    JINBEI((short)2, "金杯车型"),
    QUANSHUN((short)3, "全顺/依维轲"),
    UNKNOWN((short)-1, "未知车型");
    
    private short code;
    private String desc;
    
    private VehicleEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public short code() {
        return code;
    }
    
    public String desc() {
        return desc;
    }
    
    public static VehicleEnum valueOf(short code) {
        VehicleEnum[] enums = VehicleEnum.values();
        for(VehicleEnum e : enums) {
            if(e.code() == code) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
