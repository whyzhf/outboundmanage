package com.along.outboundmanage.utill;

import com.along.outboundmanage.model.EquipLog;

import java.util.HashMap;
import java.util.Map;

import static com.along.outboundmanage.utill.HexadecimalUtil.get10HexNum;
import static com.along.outboundmanage.utill.HexadecimalUtil.low8;

/**
 * 解析命令
 */
public class AnalysisOrderUtil {
	//异常反馈map
	private static Map<String,String> ExceptionMap=new HashMap<>();
	private static  void  initExceptionMap(){
		ExceptionMap.put("10","主锁异常打开");
		ExceptionMap.put("11","主锁异常恢复");
		ExceptionMap.put("12","电池仓异常打开");
		ExceptionMap.put("13","电池仓异常恢复");
		ExceptionMap.put("14","绷带异常断开");
		ExceptionMap.put("15","绷带异常恢复");
		ExceptionMap.put("16","设备电量不足");
		ExceptionMap.put("17","设备充满电");
		ExceptionMap.put("18","电击点遮挡");
		ExceptionMap.put("19","电击点遮挡取消");
		ExceptionMap.put("1A","电击失败");
		ExceptionMap.put("1B","恢复出厂设置");
		ExceptionMap.put("1C","添加分组");
		ExceptionMap.put("1D","删除分组");
		ExceptionMap.put("1E","删除分组ID");
		ExceptionMap.put("1F","分组布防");
		ExceptionMap.put("20","分组撤防");
		ExceptionMap.put("21","设置防脱逃模式");
		ExceptionMap.put("22","启动防脱逃模式");
		ExceptionMap.put("23","停止防脱逃模式");
		ExceptionMap.put("24","脚铐从当前遥控器的防脱逃分组中剔除");
		ExceptionMap.put("25","当前遥控器的防脱逃分组解散");
		ExceptionMap.put("26","启动防脱逃电击");
		ExceptionMap.put("27","停止防脱逃电击");
		ExceptionMap.put("28","启动防脱逃模式失败");
		ExceptionMap.put("29","停止防脱逃模式失败");
		ExceptionMap.put("2A","防脱逃设置失败");
		ExceptionMap.put("2B","防脱逃删除失败");
		ExceptionMap.put("2C","防脱逃设置成功");
		ExceptionMap.put("2D","防脱逃删除成功");
		ExceptionMap.put("2E","防脱逃越界");
		ExceptionMap.put("2F","防脱逃可控");
		ExceptionMap.put("30","设备充电到4V");
		ExceptionMap.put("31","主锁未关好");
		ExceptionMap.put("32","任何电击强行停止");
	}
	//日志反馈map
	private static Map<String,String> LogMap=new HashMap<>();
	private static  void  initLogMap(){
		LogMap.put("01","设备布防");
		LogMap.put("02","设备撤防");
		LogMap.put("03","打开主锁");
		LogMap.put("04","关闭主锁");
		LogMap.put("05","定点电击");
		LogMap.put("06","定点电击取消");
		LogMap.put("07","分组电击");
		LogMap.put("08","分组电击取消");
		LogMap.put("09","广播电击");
		LogMap.put("0A","广播电击取消");
		LogMap.put("0B","设置设备时间");
		LogMap.put("0C","设置防破拆参数");
		LogMap.put("0D","主锁打开失败");
		LogMap.put("0E","主锁关闭失败");
		LogMap.put("0F","设置设备ID");
		LogMap.put("10","主锁异常打开");
		LogMap.put("11","主锁异常恢复");
		LogMap.put("12","电池仓异常打开");
		LogMap.put("13","电池仓异常恢复");
		LogMap.put("14","绷带异常断开");
		LogMap.put("15","绷带异常恢复");
		LogMap.put("16","设备电量不足");
		LogMap.put("17","设备充满电");
		LogMap.put("18","电击点遮挡");
		LogMap.put("19","电击点遮挡取消");
		LogMap.put("1A","电击失败");
		LogMap.put("1B","恢复出厂设置");
		LogMap.put("1C","添加分组");
		LogMap.put("1D","删除分组");
		LogMap.put("1E","删除分组ID");
		LogMap.put("1F","分组布防");
		LogMap.put("20","分组撤防");
		LogMap.put("21","设置防脱逃模式（不启动）");
		LogMap.put("22","启动防脱逃模式（广播方式）");
		LogMap.put("23","停止防脱逃模式（广播方式）");
		LogMap.put("24","脚铐从当前遥控器的防脱逃分组中剔除");
		LogMap.put("25","当前遥控器的防脱逃分组解散");
		LogMap.put("26","启动防脱逃电击");
		LogMap.put("27","停止防脱逃电击");
		LogMap.put("28","启动防脱逃模式失败");
		LogMap.put("29","停止防脱逃模式失败");
		LogMap.put("2A","防脱逃设置失败");
		LogMap.put("2B","防脱逃删除失败");
		LogMap.put("2C","防脱逃设置成功");
		LogMap.put("2D","防脱逃删除成功");
		LogMap.put("2E","防脱逃越界");
		LogMap.put("2F","防脱逃可控");
		LogMap.put("30","");
		LogMap.put("31","");
		LogMap.put("32","电击强行停止");
	}
	/**
	 * 异常命令解析
	 * CMD1		0xA0
	 * Parameter1	0x10	主锁异常打开
	 * 	0x11	主锁异常恢复
	 * 	0x12	电池仓异常打开
	 * 	0x13	电池仓异常恢复
	 * 	0x14	绷带异常断开
	 * 	0x15	绷带异常恢复
	 * 	0x16	设备电量不足
	 * 	0x17	设备充满电（用于脚铐充满时通过串口反馈给充电宝和遥控器）
	 * 	0x18	电击点遮挡
	 * 	0x19	电击点遮挡取消
	 * 	0x1A	电击失败
	 * 	0x1B	恢复出厂设置
	 * 	0x1C	添加分组
	 * 	0x1D	删除分组
	 * 	0x1E	删除分组ID
	 * 	0x1F	分组布防
	 * 	0x20	分组撤防
	 * 	0x21	设置防脱逃模式（不启动）
	 * 	0x22	启动防脱逃模式（广播方式）
	 * 	0x23	停止防脱逃模式（广播方式）
	 * 	0x24	脚铐从当前遥控器的防脱逃分组中剔除
	 * 	0x25 	当前遥控器的防脱逃分组解散
	 * 	0x26	启动防脱逃电击
	 * 	0x27	停止防脱逃电击
	 * 	0x28	启动防脱逃模式失败
	 * 	0x29	停止防脱逃模式失败
	 * 	0x2A	防脱逃设置失败
	 * 	0x2B	防脱逃删除失败
	 * 	0x2C	防脱逃设置成功
	 * 	0x2D	防脱逃删除成功
	 * 	0x2E	防脱逃越界
	 * 	0x2F	防脱逃可控
	 * 	0x30	设备充电到4V（用于脚铐充到4V时通过串口反馈给充电宝）
	 * 	0x31	主锁未关好
	 * 	0x32	任何电击强行停止
	 */
	public static String ExceptionOrder(String order){
		initExceptionMap();

		String comm= EquipUtil.getCommand(order,"A5");
		//拆分字符串
		String[]arr = comm.split("(?<=\\G.{2})");
		String equip02=arr[6]+arr[7]+arr[8]+arr[9];
		int equ=get10HexNum(equip02);
		if (899999==equ||"A0".equals(arr[10])){
			return ExceptionMap.get(arr[11]);
		}
		return "-1";
	}

	/**
	 * 解析日志
	 * @param order
	 */
	public static void returnLogOrder(String order) {
		initLogMap();
		String comm = EquipUtil.getCommand(order, "A5");
		//拆分字符串
		String[] arr = comm.split("(?<=\\G.{2})");
		if ("FF".equals(arr[11])) {
			//日志传输结束
		} else {
			EquipLog el = new EquipLog.Builder()
					.id(0)
					.logId((get10HexNum(arr[14]) << 4) + get10HexNum(arr[12]))
					.equipId(get10HexNum(arr[2] + arr[3] + arr[4] + arr[5]))
					.equipId02(get10HexNum(arr[6] + arr[7] + arr[8] + arr[9]))
					.order(arr[10] + arr[11] + arr[12])
					.desc(LogMap.get(arr[11]))
					.datetime(get10HexNum(arr[13]) + "-" + low8(arr[14]) + "-" + get10HexNum(arr[15])
							+ " " + get10HexNum(arr[16]) + ":" + get10HexNum(arr[17]) + ":" + get10HexNum(arr[18]))
					.build();
		}
	}
}
