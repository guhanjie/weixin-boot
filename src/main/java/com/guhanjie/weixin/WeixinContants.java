/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.weixin 
 * File Name:			WeixinContants.java 
 * Create Date:		2016年8月28日 下午9:41:44 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.weixin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class Name:		WeixinContants<br/>
 * Description:		[description]
 * @time				2016年8月28日 下午9:41:44
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@Component
public class WeixinContants {
	
	@Value("${weixin.app.id}")
    public String APPID;
	@Value("${weixin.app.secret}")
	public String APPSECRET;
	@Value("${weixin.token}")
	public String TOKEN;
	
    public final static String MSG_TEXT_TYPE = "text";
    public final static String MSG_IMAGE_TYPE = "image";
    public final static String MSG_VOICE_TYPE = "voice";
    public final static String MSG_VIDEO_TYPE = "video";
    public final static String MSG_SHORTVIDEO_TYPE = "shortvideo";
    public final static String MSG_LOCATION_TYPE = "location";
    public final static String MSG_EVENT_TYPE = "event";
    
    public final static String API_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String API_MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public final static String API_POST_MEDIA="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    public final static String API_GET_MEDIA="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
}
