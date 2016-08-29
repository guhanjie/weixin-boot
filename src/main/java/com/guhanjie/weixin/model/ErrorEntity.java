/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			ErrorEntity.java 
 * Create Date:		2016年8月28日 下午9:18:47 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.model;

/**
 * Class Name:		ErrorEntity<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:18:47
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class ErrorEntity {
    private String errcode;
    private String errmsg;
    public String getErrcode() {
        return errcode;
    }
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
