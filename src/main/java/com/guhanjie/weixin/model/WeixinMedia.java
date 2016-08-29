/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			WeixinMedia.java 
 * Create Date:		2016年8月28日 下午9:28:00 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin.model;

/**
 * Class Name:		WeixinMedia<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:28:00
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class WeixinMedia {
    private String type;
    private String media_id;
    private Integer created_at;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMedia_id() {
        return media_id;
    }
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
    public Integer getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }
}
