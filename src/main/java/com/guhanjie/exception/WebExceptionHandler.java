package com.guhanjie.exception;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.guhanjie.util.HttpUtil;

public class WebExceptionHandler implements HandlerExceptionResolver {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {		
		LOGGER.error(exception.getMessage(), exception);
		
		if (!(exception instanceof WebException)) {
			String debugMessage = exception.getMessage();
			if(debugMessage == null){
				debugMessage="no message";
			}
			debugMessage =debugMessage.trim();
			exception = WebExceptionFactory.exception(WebExceptionEnum.SYSTEM_ERROR, debugMessage, exception);
		}		
		WebException ee = (WebException)exception;
		String causeMsg = ee.getCauseMessage();
		//最多显示800个字符，以免将底层堆栈返回给客户端
		if(causeMsg != null && causeMsg.length()>800){
			causeMsg = causeMsg.substring(0, 800);
		}		
		//status
		response.setStatus(ee.getHttpStatus());
		//ajax
		if(HttpUtil.isAjaxRequest(request) || HttpUtil.isMultiPartRequest(request)){
			PrintWriter os = null;
			try {
				response.setStatus(ee.getHttpStatus());
				os = response.getWriter();
				os.print(causeMsg);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				close(os);
			}
			return null;
		}else{
			request.setAttribute("causeMessage", causeMsg);
			request.setAttribute("exception", exception);
			return new ModelAndView("error");
		}
	}
	
	protected void close(Closeable obj){
		try {
			if(obj != null){
				obj.close();
			}			
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(),e);
		}
	}
}
