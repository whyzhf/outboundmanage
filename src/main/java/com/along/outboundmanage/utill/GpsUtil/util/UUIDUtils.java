package com.along.outboundmanage.utill.GpsUtil.util;

import java.util.UUID;

/**
 * 
 * @author JASON
 *
 */
public class UUIDUtils {

	
	/** 
	* 获得指定数目的UUID 
	* @param number int 需要获得的UUID数量 
	* @return String[] UUID数组 
	*/ 
	public static String[] getUUIDs(int number){ 
		if(number < 1){ 
			return null; 
		} 
		String[] retArray = new String[number]; 
		for(int i=0;i<number;i++){ 
			retArray[i] = getUUID(); 
		} 
		return retArray; 
	}

	/** 
	* 获得一个UUID 
	* @return String UUID 
	*/ 
	public static String getUUID(){ 
		String uuid = UUID.randomUUID().toString(); 
		//去掉“-”符号 
		return uuid.replaceAll("-", "");
	}
	
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			System.out.println(uuid);
		}
	}
	

}
