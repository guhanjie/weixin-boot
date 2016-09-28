package com.guhanjie.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
    
    public static Document getRequest2Xml(HttpServletRequest req) throws IOException, DocumentException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while((str=br.readLine())!=null) {
            sb.append(str);
        }
        String xml = sb.toString();
        return DocumentHelper.parseText(xml);
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

    public static void sendGet(String url, HttpCallback c) {
        LOGGER.debug("starting to send http get request[{}]...", url);
        HttpGet get = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        HttpEntity entity = null;
        try {
            get = new HttpGet(url);
            client = HttpClients.createDefault();
            resp = client.execute(get);
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode>=200 && statusCode<300) {
                LOGGER.debug("success to get response for[{}]", url);
                entity = resp.getEntity();
                c.setEntity(entity);
                c.run();
            }
            else {
                LOGGER.warn("got response for[{}], but http status is malform", url);
            }
        } catch (Exception e) {
            LOGGER.error("http error while sending http get request", e);
        } finally {
            try {
                if(resp!=null) {
                    try {
                        EntityUtils.consume(resp.getEntity());
                    } finally {
                        resp.close();
                    }
                }
            } catch (IOException e) {
                LOGGER.error("http response close error.");
            }
            try {
                if(client!=null) client.close();
            } catch (IOException e) {
                LOGGER.error("http client close error.");
            }
        }
    }
    
    public static void https(String passwd) {
//    	char[] pwdChars = passwd.toCharArray();
//    	//指定读取证书格式为PKCS12
//    	KeyStore keyStore = KeyStore.getInstance("PKCS12");
//    	//读取本机存放的PKCS12证书文件
//    	Resource pkcResource = new ClassPathResource("weixin/apiclient_cert.p12");
//    	InputStream instream  = pkcResource.getInputStream();
//    	try {
//    	//指定PKCS12的密码(商户ID)
//    	keyStore.load(instream, pwdChars);
//    	} finally {
//    	instream.close();
//    	}
//    	SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, pwdChars).build();
//    	//指定TLS版本
//    	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//    					sslcontext,new String[] { "TLSv1" }, null, 
//    					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//		//设置httpclient的SSLSocketFactory
//		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
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
	
	public static abstract class HttpCallback implements Runnable {
	    private HttpEntity entity;
        public void setEntity(HttpEntity e) {
            this.entity = e;
        }
        public void run() {
            processEntity(HttpCallback.this.entity);
        }
        public abstract void processEntity(HttpEntity e);
	}
}

