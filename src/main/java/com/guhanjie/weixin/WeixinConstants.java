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
public class WeixinConstants {
	
	@Value("${weixin.app.id}")
    public String APPID;
	@Value("${weixin.app.secret}")
	public String APPSECRET;
	@Value("${weixin.token}")
	public String TOKEN;
	@Value("${weixin.kf.openids:o_05UwXR65RZ-VeZ12CfLH27UiEk}")	//,o_05Uwe4_9GGQ93ESXg27RCw6HqE
	public String KF_OPENIDS;
	@Value("${weixin.mch.id:1378339302}")
	public String MCH_ID;
	@Value("${weixin.mch.key}")
	public String MCH_KEY;
	
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
    public final static String API_KF_SEND_MSG="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    
    public final static String API_PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public final static String API_PAY_ORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
    public final static String API_PAY_CLOSEORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
    public final static String API_PAY_CALLBACK = "http://www.guhanjie.top/weixin-boot/order/paycallback";
    
    public final static String OAUTH2_SCOPE_SNSAPI_BASE="snsapi_base";
    public final static String OAUTH2_SCOPE_SNSAPI_USERINFO="snsapi_userinfo";
    public final static String OAUTH2_AUTHORIZE="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public final static String OAUTH2_REDIRECT_URI="http://www.guhanjie.top/weixin-boot/wx/oauth2";
    public final static String OAUTH2_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public final static String OAUTH2_REFRESH_TOKEN="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    public final static String OAUTH2_GET_USER_INFO="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    
}
