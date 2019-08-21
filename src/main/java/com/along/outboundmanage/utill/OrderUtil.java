package com.along.outboundmanage.utill;

import java.util.HashMap;
import java.util.Map;

import static com.along.outboundmanage.utill.GeneralUtils.getJsonStr;
import static com.along.outboundmanage.utill.HexadecimalUtil.get10HexNum;

/**
 * 发送命令
 */
public class OrderUtil {

	public static void main(String[] args) {

	}

	/**
	 * 机器连接
	 * */
	public static void getConnect(){

	}

	/**
	 * 命令组
	 */
	private static String CMD1="";
	private static String CMD2="";
	private static String CMD3="";


	/**
	 *  发送命令
	 * @param equip :接收命令的设备ID 脚扣：0-799999   遥控器：800000-899999  手环：900000-949999
	 * @param userId ：命令发送人ID 950000-999999
	 * @param CMD1 ：命令1
	 * @param CMD2 ：命令2
	 * @param CMD3 ：命令3
	 * @return
	 */
	public static String send(String equip,String userId,String CMD1,String CMD2,String CMD3){
		return "";
	}

	/**
	 * 接收命令
	 * @param str ：返回命令串
	 * @return
	 */
	public static String Receive(String str){
		return "";
	}

	/**
	 * 普通命令
	 * @param param:
	 *                0:无处理
	 *                1: 关闭脚扣主锁
	 *                2: 打开脚扣主锁
	 *                3: 根据遥控器下发的时间设置脚扣的当前时间
	 *                4: 关闭定点电击
	 *                5: 启动定点电击
	 *                6: 设备撤防
	 *                7: 设备布防
	 */
	public static  void CreatePubOrder(int param){
		 CMD1="01";
		/**
		 * 命令说明 0000 0000
		 * CMD2
		 *   Duty Shock Start
		 *      0000 0000：无处理
		 * 		0X80:1000 0000：设备布防。遥控器发送的定点电击、分组电击、广播电击、防破拆电击均有效。
		 * 	Duty Shock Stop
		 *     	0000 0000：无处理
		 * 		0X40:0100 0000：设备撤防。遥控器发送的定点电击、分组电击、广播电击、防破拆电击均无效。
		 * 	ID Shock Start
		 * 	    0000 0000：无处理
		 * 		0X20:0010 0000：启动定点电击。
		 * 	ID Shock Stop
		 * 	    0000 0000：无处理
		 * 		0X10:0001 0000：关闭定点电击
		 * 	Set Time
		 *      0000 0000：无处理
		 * 		0X08:0000 1000：根据遥控器下发的时间设置脚扣的当前时间
		 * 	Lock Open
		 *     	0000 0000：无处理
		 * 		0X04:0000 0100：打开脚扣主锁。
		 * 	Lock Close
		 *     	0000 0000：无处理
		 * 		0X02:0000 0010：关闭脚扣主锁。
		 */
		String[] arr={"00","02","04","08","10","20","40","80"};
		 CMD2=arr[param];
		 CMD3="00";

	}

	/**
	 * 普通命令反馈
	 * @param order
	 * @return
	 */
	public static  String retuenCreatePubOrder(String order){
		//拆分字符串
		String[]arr = order.split("(?<=\\G.{2})");
		if ("00".equals(arr[12])){
			return "fail";
		}else if ("01".equals(arr[12])){
			return "Equipment unprotected";
		}else if ("FF".equals(arr[12])){
			return "Success";
		}
		return "-1";
	}

	/**
	 * 群组命令
	 * @param param:
	 *                0:无处理
	 *                1: 根据遥控器下发的分组信息将整个分组删除
	 *                2: 根据遥控器下发的分组信息将设备从指定分组中删除。
	 *                3: 根据遥控器下发的分组信息将设备添加到该遥控器的指定分组。
	 *                4: 结束分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，结束电击。
	 *                5: 启动分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，启动电击。
	 *                6: 撤销分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均无效
	 *                7: 启动分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均有效
	 */
	public static  void CreateGroupOrder(int param){
		CMD1="02";
		/**
		 * 命令说明 0000 0000
		 * CMD2
		 *   Duty Group Start
		 *      0000 0000：无处理
		 * 		0X80:1000 0000：启动分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均有效
		 * 	Duty Group Stop
		 *     	0000 0000：无处理
		 * 		0X40:0100 0000：撤销分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均无效
		 * 	Group Shock Start
		 * 	    0000 0000：无处理
		 * 		0X20:0010 0000：启动分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，启动电击。
		 * 	Group Shock Stop
		 * 	    0000 0000：无处理
		 * 		0X10:0001 0000：结束分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，结束电击。
		 * 	Add GroupID
		 *      0000 0000：无处理
		 * 		0X08:0000 1000：根据遥控器下发的分组信息将设备添加到该遥控器的指定分组。
		 * 	Delete GroupID
		 *     	0000 0000：无处理
		 * 		0X04:0000 0100：根据遥控器下发的分组信息将设备从指定分组中删除。
		 * 	DeleteGroup
		 *     	0000 0000：无处理
		 * 		0X02:0000 0010：根据遥控器下发的分组信息将整个分组删除
		 */
		String[] arr={"00","02","04","08","10","20","40","80"};
		CMD2=arr[param];
		CMD3="00";
	}
	/**
	 * 群组命令反馈
	 * @param order
	 * @return
	 */
	public static  String retuenCreateGroupOrder(String order){
		//拆分字符串
		String[]arr = order.split("(?<=\\G.{2})");
		if ("00".equals(arr[13])){
			return "fail";
		}else if ("01".equals(arr[13])){
			return "Equipment unprotected";
		}else if ("FF".equals(arr[13])){
			return "Success";
		}
		return "-1";
	}
	/**
	 * 查询命令
	 * @param param
	 */
	public static  void SelectOrder(int param){
		/**
		 * CMD1
		 *  0x03	获取MAC地址（STM32L431唯一ID，12字节）
		 * 	0x04	获取系统时间和组号
		 * 	0x05	获取系统电压
		 * 		    获取系统硬件版本、软件版本
		 * 		    获取系统状态
		 * CMD2
		 *  MAC ID
		 *      0：无处理。
		 * 		0X80:1000 0000：查询设备自身唯一ID号，用于编程设备遥控器可控制ID。
		 * 	Power Check
		 * 	    0：无处理。
		 * 		0X40:0100 0000：查询设备当前电量信息。
		 * 	Device Time
		 * 	    0：无处理。
		 * 		0X20:0010 0000：查询脚扣设备当前时间。
		 * 	Device Sys Status
		 * 	    0：无处理。
		 * 		0X10:0001 0000：查询脚扣系统状态
		 * 	Device Hard Vision
		 * 	    0：无处理。
		 * 		0X08:0000 1000：查询脚扣硬件版本
		 * 	Device Software Vision
		 * 	    0：无处理。
		 * 		0X04:0000 0100：查询脚扣软件版本
		 */
		if (0==param){
			//查询ID
			CMD1="03";
			CMD2="80";
			CMD3="00";
		}else if (1==param) {
			//查询系统时间和组号
			CMD1 = "04";
			CMD2 = "80";
			CMD3 = "00";
		}else if (2==param) {
			//获取系统电压,系统硬件版本、软件版本,获取系统状态
			CMD1 = "05";
			CMD2 = "5C";
			CMD3 = "00";
		}
	}
	/**
	 * 查询命令反馈(未完成)
	 *      获取系统电压
	 * 		获取系统硬件版本、软件版本
	 * 		获取系统状态
	 * @param order
	 * @return
	 */
	public static  String retuenSelectOrderBy0x05(String order){
		//拆分字符串
		String[]arr = order.split("(?<=\\G.{2})");
		Map<String,Object> resMap=new HashMap<>();
		resMap.put("power",get10HexNum(arr[12]));
		resMap.put("sysStatus",get10HexNum(arr[12]));
		resMap.put("hardVision",get10HexNum(arr[12]));
		resMap.put("softwareVision",get10HexNum(arr[12]));
		return getJsonStr(resMap);
	}
	/**
	 * 设置设备ID命令
	 * @param param
	 */
	public static  void setIDOrder(int param){

	}

	/**
	 * 设置防破拆命令
	 * @param param
	 */
	public static  void setPreventOrder(int param){

	}
	/**
	 * 设置防破拆命令反馈
	 * @param order
	 * @return
	 */
	public static  String retuenSetPreventOrder(String order){
		//拆分字符串
		String[]arr = order.split("(?<=\\G.{2})");
		if ("00".equals(arr[12])){
			return "fail";
		}else if ("FF".equals(arr[12])){
			return "Success";
		}
		return "-1";
	}
	/**
	 * 发送获取日志命令
	 *
	 */
	public static  void getLogOrder(){
		CMD1 = "09";
		CMD2 = "00";
		CMD3 = "00";
	}
}
