/** 
 * Project Name:		webboot 
 * Package Name:	com.guhanjie.util 
 * File Name:			XssSecurityUtils.java 
 * Create Date:		2015年12月21日 下午3:35:41 
 * Copyright (c) 2008-2015, Canglong All Rights Reserved.
 */  
package com.guhanjie.util;

import java.util.regex.Pattern;

/**
 * Class Name:		XssSecurityUtils<br/>
 * Description:		[description]
 * @time				2015年12月21日 下午3:35:41
 * @author			canglong
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class XssSecurityUtil {

    private static Pattern[] patterns = new Pattern[]{
            // 删除script标签
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // 删除src='...'表达式
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // 删除</script>标签
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            // 删除<script ...>标签
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // 删除eval(...)表达式
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // 删除expression(...)表达式
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // 删除javascript:...表达式
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // 删除vbscript:...表达式
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // 删除onload=表达式
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };
    
    /**
     * Method Name:	stripXSS<br/>
     * Description:			[直接清除掉含有xss威胁的字符]
     * @author				guhanjie
     * @time					2015年12月21日 下午3:16:35
     * @param value
     * @return
     */
    public static String stripXSS(String value) {
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
	public static String xssClean(String str){
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
