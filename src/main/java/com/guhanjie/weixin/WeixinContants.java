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
	
    public final static String MSG_TYPE_TEXT = "text";									//文本消息
    public final static String MSG_TYPE_IMAGE = "image";								//图片消息
    public final static String MSG_TYPE_VOICE = "voice";								//语音消息
    public final static String MSG_TYPE_VIDEO = "video";								//视频消息
    public final static String MSG_TYPE_SHORTVIDEO = "shortvideo";			//小视频消息
    public final static String MSG_TYPE_LOCATION = "location";					//地理位置消息
    public final static String MSG_TYPE_LINK = "link";										//链接消息
    public final static String MSG_TYPE_EVENT = "event";								//事件
    
    public final static String EVENT_SUBSCRIBE = "subscribe";						//关注公众号事件
    public final static String EVENT_LOCATION = "LOCATION";						//上报地理位置事件
    public final static String EVENT_CLICK = "CLICK";										//点击菜单拉取消息时的事件推送
    public final static String EVENT_VIEW = "VIEW";										//点击菜单跳转链接时的事件推送
    
    public final static String API_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String API_MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public final static String API_POST_MEDIA="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    public final static String API_GET_MEDIA="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    public final static String API_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
}
