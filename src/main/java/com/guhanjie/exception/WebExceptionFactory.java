package com.guhanjie.exception;

public class WebExceptionFactory {
	
	public static WebException exception(Integer code, String message, String screenMessage, Throwable cause){
        return new WebException(code, message, screenMessage, cause);
    }
	
    public static WebException exception(WebExceptionEnum exEnum){
    	exEnum = (exEnum==null ? WebExceptionEnum.SYSTEM_ERROR : exEnum);
		return new WebException(exEnum.getCode(), exEnum.getMessage(), exEnum.getScreenMessage(), null);
	}

    public static WebException exception(WebExceptionEnum exEnum, String screenMsg){
    	exEnum = (exEnum==null ? WebExceptionEnum.SYSTEM_ERROR : exEnum);
		return new WebException(exEnum.getCode(), exEnum.getMessage(), screenMsg, null);
    }
    
	public static WebException exception(WebExceptionEnum exEnum, Throwable cause){
    	exEnum = (exEnum==null ? WebExceptionEnum.SYSTEM_ERROR : exEnum);
		return new WebException(exEnum.getCode(), exEnum.getMessage(), exEnum.getScreenMessage(), cause);
	}

    public static WebException exception(WebExceptionEnum exEnum, String screenMsg, Throwable cause){
    	exEnum = (exEnum==null ? WebExceptionEnum.SYSTEM_ERROR : exEnum);
		return new WebException(exEnum.getCode(), exEnum.getMessage(), screenMsg, cause);
    }
}