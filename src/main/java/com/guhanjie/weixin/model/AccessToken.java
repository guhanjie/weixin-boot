/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			AccessToken.java 
 * Create Date:		2016年8月28日 下午9:16:27 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.model;

/**
 * Class Name:		AccessToken<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:16:27
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class AccessToken {
    private String access_token;
    private String expires_in;
    
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
