package com.guhanjie.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.guhanjie.exception.WebException;
import com.guhanjie.exception.WebExceptionEnum;

public abstract class BaseController {

    @Autowired			//Spring上下文已经将这个web应用的servletContext注入到上下文中去了，只要注入进来即可。
    protected ServletContext servletContext;
	
    /**
     * 返回成功（不带任何结果值）
     * @return {"success": true}
     */
    protected Map<String, Object> success() {
		Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("success", true);
        return rt;
    }

	/**
	 * 返回请求成功后带相关内容的Map对象
	 * 
	 * @param obj
	 * @return {<br>"success": true<br>"content": obj<br>}
	 */
	protected Map<String, Object> success(Object obj){
		Map<String, Object> rt = success();
		if(obj != null){
			rt.put("content", obj);
		}
		return rt;
	}

	/**
     * 返回失败（不带任何结果值）
     * @return {"success": false}
     */
    protected Map<String, Object> fail() {
		Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("success", false);
        return rt;
    }

	/**
	 * 返回请求失败后带相关内容的Map对象
	 * 
	 * @param obj
	 * @return {<br>"success": false<br>"content": obj<br>}
	 */
	protected Map<String, Object> fail(Object obj){
		Map<String, Object> rt = fail();
		if(obj != null){
			rt.put("content", obj);
		}
		return rt;
	}

	/**
     * 返回失败，并带相关失败消息
	 * @param response  用于设置status=299,不需要时可设置传null
	 * @param code 错误编号，如：20001
	 * @param message 错误消息key,如:account.password.incorrect
	 * @param description 页面显示错误消息：如：password is incorrect
	 * @param cause 调试信息：如：suiteId=1
     * @return {<br>"success": false<br>"code": 错误编号<br>"error": 错误消息<br>"description": 页面显示错误消息<br>"cause": 调试信息<br>}
	 */
	protected Map<String, Object> fail(HttpServletResponse response, Integer code, String message, String description, String cause){
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.put("success", false);
		if(code != null){
			rt.put("code", code);//api
		}
		if(message != null){
			rt.put("error", message);//老api
		}
		if(description != null){
			rt.put("description", description);//老api规范
		}
		if(cause != null){
			rt.put("cause", cause);
		}
		if(response != null){
			response.setStatus(299);
		}
		return rt;
	}
    
	/**
	 * 返回异常失败，并带相关失败消息
	 * @param response  用于设置status=299,不需要时可设置传null
	 * @param e 抛出的异常
	 * @return {<br>"success": false<br>"result": false<br>"code": 错误编号<br>"error": 错误消息<br>"description": 页面显示错误消息<br>"cause": 调试信息<br>}
	 */
	protected Map<String, Object> fail(HttpServletResponse response, Exception e) {
		// 平台异常
        if(e instanceof WebException) {
        	WebException e2 = (WebException) e;
        	Integer code = e2.getCode();
    		String message = e2.getMessage();
    		String description = e2.getScreenMessage()==null ? e2.getMessage() : e2.getScreenMessage();
    		String causeMessage = e2.getCauseMessage();
    		if(response != null) {
    			response.setStatus(e2.getHttpStatus());
    		}
    		return fail(null, code, message, description, causeMessage);
        } 
		// 系统异常
        else {
    		if(response != null){
    			response.setStatus(500);
    		}
    		Integer code = WebExceptionEnum.SYSTEM_ERROR.getCode();
    		String message = WebExceptionEnum.SYSTEM_ERROR.getMessage();
    		String description = WebExceptionEnum.SYSTEM_ERROR.getScreenMessage();
    		String debugMessage = e.getMessage();
    		if (StringUtils.isBlank(debugMessage)) {
    			debugMessage = e.getClass().getSimpleName();
    		} else {
    			debugMessage = debugMessage.trim();
    			int index = debugMessage.indexOf('\n');
    			if(index == -1){
    				index = debugMessage.length();
    			}
    			if (index > 0) {
    				debugMessage = debugMessage.substring(0, index%200);
    			}
    		}
    		return fail(null, code, message, description, debugMessage);
        }		
	}

}
