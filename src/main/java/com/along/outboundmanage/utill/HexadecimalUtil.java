package com.along.outboundmanage.utill;

import org.thymeleaf.util.StringUtils;

import static com.along.outboundmanage.utill.DataUtil.getNowData;

/**
 * 进制装换
 * */
public class HexadecimalUtil {
	public static void main(String[] args) {
		System.out.println(low8("01"));
		System.out.println( (byte)26);
	}
	/**
	 * 十六进制转十进制
	 * @param num
	 * @return
	 */
	public static Integer get10HexNum(String num){
		if (num.contains("0X")){
			num=num.replace("0X","");
		}
		return Integer.parseInt(num.substring(0),16);
	}

	/**
	 * 十进制转十六进制
	 * @param num
	 * @return
	 */
	public static String get16Num(Object num){

		return Integer.toHexString(Integer.parseInt(num+""));
	}
	/**
	 * 十进制转十六进制,设置长度，不足补0
	 * @param num
	 * @return
	 */
	public static String get16NumAdd0 (String num,int len){
		String str=Integer.toHexString(Integer.parseInt(num)).toUpperCase();
		String res="";
		if (len>=str.length()){
			res=StringUtils.repeat("0",(len-str.length()))+str;
		}else{
			return str;
		}
		return res;
	}
	/**
	 * 当前时间转16进制
	 * 返回 “16进制时间值-时间值总和”
	 * */
	public static String get16NumByTime (String strFormat){
		String str=getNowData(strFormat);
		String[] arr=str.split("-");
		int sum=0;
		for (int i = 0; i < arr.length; i++) {
			sum+=Integer.parseInt(arr[i]);
			arr[i]=get16NumAdd0(arr[i],2);

		}
		return String.join("",arr)+"-"+sum;
	}
	//num & 0xff
	public static int  low8(Object num){
		return Integer.parseInt(num+"") & 0xff;
	}
	//获取高四位
	public static int getHeight4(byte data){
		int height;
		height = ((data & 0xf0) >> 4);
		return height;
	}
}
