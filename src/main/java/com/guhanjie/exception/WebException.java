package com.guhanjie.exception;

import java.util.ArrayList;
import java.util.List;

import com.guhanjie.util.XssSecurityUtil;

public class WebException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private int httpStatus = 299;   			//HTTP状态码，web通信正常，只是服务异常，因此属于2**，此处取值299
    private Integer code;							//异常代码
    private String message;    					//异常英文KEY
    private String screenMessage;   		//用于界面显示的消息
    private String causeMessage; 			//异常原因，便于开发人员调试的消息    
    private List<String> params;				//错误消息中的参数，如：用户名长度要大于{0}小于{1}
          
    protected WebException(Integer code, String message) {
		super(message, null);
		this.code = code;
		this.message = message;
	}
    
    protected WebException(Integer code, String message, String screenMessage) {
		super(message, null);
		this.code = code;
		this.message = message;
		this.screenMessage = screenMessage;
	}
        
    protected WebException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
		this.causeMessage = (cause==null ? null : XssSecurityUtil.xssClean(cause.getMessage()));
	}
    
    protected WebException(Integer code, String message, String screenMessage, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
		this.screenMessage = screenMessage;
		this.causeMessage = (cause==null ? null : XssSecurityUtil.xssClean(cause.getMessage()));
	}
    	
	public int getHttpStatus() {
		return httpStatus;
	}


	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}


	public Integer getCode() {
		return code;
	}


	public WebException setCode(Integer code) {
		this.code = code;
		return this;
	}


	public String getMessage() {
		return message;
	}


	public WebException setMessage(String message) {
		this.message = message;
		return this;
	}


	public String getScreenMessage() {
		return screenMessage;
	}

	public WebException setScreenMessage(String screenMessage) {
		this.screenMessage = screenMessage;
		return this;
	}

	public String getCauseMessage() {
		if(causeMessage!=null && params!=null) {
			for(int i=0; i<params.size(); i++) {
				String regex = "{"+i+"}";
				causeMessage.replaceAll(regex, params.get(i));
			}
		}
		return causeMessage;
	}


	public WebException setCauseMessage(String causeMessage) {
		this.causeMessage = XssSecurityUtil.xssClean(causeMessage);
		return this;
	}


	public List<String> getParams() {
		return params;
	}
	
	public WebException setParams(List<String> params) {
		this.params = params;
		return this;
	}

	public WebException addParam(String param) {
		if (this.params == null) {
			this.params = new ArrayList<String>();
		}
		this.params.add(param);
		return this;
	}
}