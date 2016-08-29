/** 
 * Project Name:		webboot 
 * Package Name:	com.guhanjie.filter 
 * File Name:			XSSRequestWrapper.java 
 * Create Date:		2015年12月21日 下午3:14:42 
 * Copyright (c) 2008-2015, Canglong All Rights Reserved.
 */  
package com.guhanjie.filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * Class Name:		XSSRequestWrapper<br/>
 * Description:		[跨站脚本请求过滤包装器]
 * @time				2015年12月21日 下午3:14:42
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
	
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private static Pattern[] patterns = new Pattern[]{
            // Script fragments
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // src='...'
            //Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            //Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // lonely script tags
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // eval(...)
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // expression(...)
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // javascript:...
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // vbscript:...
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // onload(...)=...
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    /**
     * Method Name:	stripXSS<br/>
     * Description:			[直接清除掉含有xss威胁的字符]
     * @author				guhanjie
     * @time					2015年12月21日 下午3:16:35
     * @param value
     * @return
     */
    private static String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Remove all sections that match a pattern
            for (Pattern scriptPattern : patterns) {
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
    
    /**
     * Method Name:	xssClean<br/>
     * Description:			[使用html转义字符替换]
     * @author				guhanjie
     * @time					2015年12月21日 下午3:17:34
     * @param str
     * @return
     */
    private static String xssClean(String str){
		if(str == null || str.length() == 0){
			return str;
		}
		str = str.replace("&", "&amp;");
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		str = str.replace("'", "&apos;");
		str = str.replace("\"","&quot;");
		return str;
	}
}