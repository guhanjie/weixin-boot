package com.guhanjie.util;

import org.apache.commons.codec.binary.Base64;

public class BASE64Util {

	/**
     * 解密
     */
    public static String decodeStr(String pwd)
    {
        byte[] debytes = Base64.decodeBase64(pwd.getBytes());
        return new String(debytes);
    }

    /**
     * 加密
     */
    public static String encodeStr(String pwd)
    {
        byte[] enbytes = Base64.encodeBase64Chunked(pwd.getBytes());
        return new String(enbytes);
    }
}
