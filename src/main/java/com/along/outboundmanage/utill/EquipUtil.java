package com.along.outboundmanage.utill;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.along.outboundmanage.utill.HexadecimalUtil.*;
import static com.along.outboundmanage.utill.HexadecimalUtil.get16NumAdd0;

public class EquipUtil {
	public static void main(String[] args) {
		//System.out.println("AA14000C34FC000C35010C80001208020A31211F");
		//System.out.println(send("799996","800001","0C","80","00"));
		//System.out.println(Receive("A514000C34FC000000000D010000000000000008"));
		String str="AA14000C34FC000C35010C80001208020A31211F";
		String[]arr = str.split("(?<=\\G.{2})");
		int sum=0;
		for (int i = 1; i < arr.length-1; i++) {
			System.out.println(arr[i]+" = "+get10HexNum(arr[i]));
			sum+=get10HexNum(arr[i]);
		}
		System.out.println(sum+" = "+get16Num(sum));
		System.out.println("low8 "+get16Num(low8(sum)));
	}
	/**
	 * 机器连接
	 * */
	public static void getConnect(){

	}
	/**
	 * 发送命令
	 * 1.命令头（“AA”）,命令长度（“14”）
	 * 2.设备ID get16NumAdd0()（00 00 00 00），遥控器ID（00 00 00 00）
	 * 3.命令（00 00 00）
	 * 4.时间get16NumByTime()（00 00 00 00 00 00）
	 * 5,校验位（00）
	 * */
	public static String send(String equip,String equip02,String CMD1,String CMD2,String CMD3){
		StringBuffer sb=new StringBuffer(40);
		int sum=20;
		//添加head,len
		sb.append("AA14");
		//添加设备id
		//sum+=Integer.parseInt(equip);
		String str=get16NumAdd0(equip,8);
		//拆分字符串
		String[]arr = str.split("(?<=\\G.{2})");
		sum=sum+(get10HexNum(arr[0])+get10HexNum(arr[1])+get10HexNum(arr[2])+get10HexNum(arr[3]));
		sb.append(str);
		//添加遥控器id
		//sum+=Integer.parseInt(equip02);
		String str2=get16NumAdd0(equip02,8);
		//拆分字符串
		String[]arr2 = str2.split("(?<=\\G.{2})");
		sum=sum+(get10HexNum(arr2[0])+get10HexNum(arr2[1])+get10HexNum(arr2[2])+get10HexNum(arr2[3]));
		sb.append(get16NumAdd0(equip02,8));
		//添加命令
		//CMD1
		sum+=get10HexNum(CMD1);
		sb.append(CMD1);
		//CMD2
		sum+=get10HexNum(CMD2);
		sb.append(CMD2);
		//CMD3
		sum+=get10HexNum(CMD3);
		sb.append(CMD3);
		//添加时间
		String time=get16NumByTime("yy-MM-dd-HH-mm-ss");
		//time="1208020A3121-118";
		sum+=Integer.parseInt(time.split("-")[1]);
		sb.append(time.split("-")[0]);
		//添加校验位
		//System.out.println(sum+"::"+get16Num(sum));
		sb.append(get16Num(low8(sum)));
		return sb.toString();
	}
	/**
	 * 接收命令
	 * 1.获取有效命令串getCommand（）
	 * 2.命令串校验check（）
	 * 3.解析命令串
	 * */
	public static String Receive(String str){
		String comm=getCommand(str,"A5");
		comm=check(comm);
		if ("-1".equals(comm)){//校验失败
			return "error";
		}else if("00".equals(comm)){//执行失败
			return "fail";
		}else if("FF".equals(comm)){//执行成功
			return "Success";
		}else{
			return "other error ";
		}
	}
	/**
	 * 用于建立十六进制字符的输出的大写字符数组
	 */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final Map<Character,Character> DIGITS_INVERT=new HashMap<>();
	public static void initMap(){
		for (int i = 0; i < DIGITS_UPPER.length; i++) {
			DIGITS_INVERT.put(DIGITS_UPPER[i],DIGITS_UPPER[15-i]);
		}
	}
	/**
	 * 充电模块
	 * 1.代发数据取反
	 * 2. 11字节和19字节互换
	 * */
	public static String Invert(String str){
		initMap();
		char[]strArr=str.toCharArray();
		for (int i = 0; i < strArr.length; i++) {
				strArr[i]=DIGITS_INVERT.get(strArr[i]);
		}
		return new String(strArr);
	}

	/**
	* 校验
	* 校验：除命令头两个字节和校验字节外所有字节的累加和低8位。
	* */
	public static String  check(String string){
		//拆分字符串
		String[]arr = string.split("(?<=\\G.{2})");
		int sum=0;

		//求和
		for (int i =1; i < arr.length-1; i++) {
			System.out.println(arr[i]+" "+get10HexNum(arr[i]));
			sum+=get10HexNum(arr[i]);
		}
		System.out.println(get16Num((low8(get16Num(sum))+"")));
		//校验
		if(get16Num((low8(get16Num(sum))+"")).equals(arr[arr.length - 1])){
			return arr[12];
		}else{
			return "-1";
		}
	}

	/**
	 * 获取命令
	 * */
	public static String getCommand(String string,String head){
		int start=string.indexOf(head);
		return string.substring(start,start+40);
	}
}
