package com.guhanjie.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

public final class HttpUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

	private static String SCHEMA_HTTPS = "https";
	private static String SCHEMA_HTTP = "http";
	
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && name != null) {
			for(Cookie cookie : cookies) {
				if(name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
    
    /** 
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
     *  
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public final static String getIpAddress(HttpServletRequest request) {  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址    
        String ip = request.getHeader("X-Forwarded-For");  
        LOGGER.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=[{}]", ip);    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
                LOGGER.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=[{}]", ip);  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
                LOGGER.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=[{}]", ip);  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
                LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=[{}]", ip);  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=[{}]", ip); 
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
                LOGGER.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=[{}]", ip); 
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }  
    
    public static int getResponseCode(String url) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpGet);
            return response.getStatusLine().getStatusCode();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static String getRequestBody(HttpServletRequest request, String charsetName) throws ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = requestWrapper.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw new ServletException("Error reading the request payload", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException iox) {
                    // ignore
                }
            }
        }
        return stringBuilder.toString();
    }

    public static HttpResponse sendDelete(String url) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            return httpclient.execute(httpDelete);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static HttpResponse sendDelete(String url, String username, String password) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            String s = username + ":" + password;
            String basicHeader = "Basic " + Base64.encodeBase64String(s.getBytes());
            httpDelete.setHeader(HttpHeaders.AUTHORIZATION, basicHeader);
            return httpclient.execute(httpDelete);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public static boolean isMultiPartRequest(HttpServletRequest request) {
        return request.getHeader(HTTP.CONTENT_TYPE) != null && request.getHeader(HTTP.CONTENT_TYPE).contains("multipart/form-data");
    }

    public static String encodeUTF8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static String decodeUTF8(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }
    
	/**
	 * 如果url不是以https开头，需要修改成request.getScheme()开头
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String schema(HttpServletRequest request, String url){
		if(url == null){
			return null;
		}		
		String scheme = request.getScheme();		
		if(url.startsWith(SCHEMA_HTTPS)){
			return url;
		}else if(url.startsWith(SCHEMA_HTTP)){
			if(SCHEMA_HTTPS.equalsIgnoreCase(scheme)){
				return SCHEMA_HTTPS + url.substring(4);
			}else{
				return url;
			}
		}else{
			return scheme + "://" + url;
		}
	}
}
