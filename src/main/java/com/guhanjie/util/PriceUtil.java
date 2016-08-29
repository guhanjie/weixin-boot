package com.guhanjie.util;

import java.text.DecimalFormat;

public class PriceUtil {
	/**
	 * 价钱工具类
	 * 
	 * @param num
	 * @param dev
	 * @param valueIfZero
	 * @return
	 */
	public static String format(Object num, int dev, String valueIfZero){
		if(num == null){
			return valueIfZero;
		}
		if(dev == 0){
			dev = 1;
		}
		double f = Double.parseDouble(num.toString());
		f = f/dev;
		
		if( f < 0.01){
			return valueIfZero;
		}		
		DecimalFormat df1 = new DecimalFormat("##########0.00");		
		return df1.format(f);
	}

	public static void main(String[] args) {
		System.out.println(format(111, 100, "免费"));
		System.out.println(format(1,100,"免费"));
		System.out.println(format(9,100,"免费"));
		System.out.println(format(11,100,"免费"));
		System.out.println(format(11,100,"免费"));
		System.out.println(format("1234567890123456789",100,"免费"));
		System.out.println(format("1234567890123456719",100,"免费"));
		System.out.println(format("1111111111111111111111",100,"免费"));
		System.out.println(format("9999999999999999999999",100,"免费"));
	}
}
