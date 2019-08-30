package com.along.outboundmanage.utill;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import static com.along.outboundmanage.utill.GeneralUtils.replaceSpecialStr;
import static com.along.outboundmanage.utill.OrderUtil.retuenLogOrder;
import static com.along.outboundmanage.utill.OrderUtil.send;

public class RXTXUtil{
	private static final String DEMONAME = "串口测试";

	/**
	 * 检测系统中可用的端口
	 */
	private CommPortIdentifier portId;
	/**
	 * 获得系统可用的端口名称列表
	 */
	private static Enumeration<CommPortIdentifier> portList;
	/**
	 * 输入流
	 */
	private static InputStream inputStream;
	/**
	 * RS-232的串行口
	 */
	private static SerialPort serialPort;
	/**
	 * 串口返回信息
	 */
	 static List<String>resList=new ArrayList<>();

	/**
	 普通命令   0:无处理
	 * 	 *                11: 关闭脚扣主锁
	 * 	 *                12: 打开脚扣主锁
	 * 	 *                13: 根据遥控器下发的时间设置脚扣的当前时间
	 * 	 *                14: 关闭定点电击
	 * 	 *                15: 启动定点电击
	 * 	 *                16: 设备撤防
	 * 	 *                17: 设备布防
	 *          群组命令   20:无处理
	 * 	 *                21: 根据遥控器下发的分组信息将整个分组删除
	 * 	 *                22: 根据遥控器下发的分组信息将设备从指定分组中删除。
	 * 	 *                23: 根据遥控器下发的分组信息将设备添加到该遥控器的指定分组。
	 * 	 *                24: 结束分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，结束电击。
	 * 	 *                25: 启动分组电击。指定遥控器ID下发的分组编号相同的所有脚扣，启动电击。
	 * 	 *                26: 撤销分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均无效
	 * 	 *                27: 启动分组布防。指定遥控器ID下发的分组编号相同的所有脚扣，定点电击、分组电击、广播电击、防破拆电击均有效
	 * 	        查询命令
	 * 	 * 	               30: 查询id
	 * 	 * 	               40: 获取系统时间和组号
	 * 	 * 	               50: 获取系统电压,系统硬件版本、软件版本,获取系统状态
	 * 	         设置命令   80：设置防破拆电击启动参数
	 * 	         日志命令   90：发送了获取日志命令
	 */
	public static void main(String[] args){

		//for (int i = 0; i < 2002; i++) {
			//System.out.println("-----------第"+i+"条日志申请-----------------");

			String order=send("00000090","800001",90);
			//String order="AA140000005A0000000009000000000000000021";
			System.out.println(order);
			try {
				System.out.println("reve:" + executeLogOrder(order, "COM3", 115200));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		//}

		/*String[] arr={"00","02","04","08","10","20","40","80"};
		String[] arr2={"无处理","关闭脚扣主锁","打开脚扣主锁","设置脚扣的当前时间","关闭定点电击","启动定点电击","设备撤防","设备布防"};
		//for (int i = 0; i < 8; i++) {
		long startTime = System.currentTimeMillis();
			try {
				//System.out.println("-----------普通命令"+arr2[i]+"-----------------");
				String order=send("00000090","800001","01",arr[5],"00");
				System.out.println(order);
				System.out.println("reve:"+	executeOrder(order,"COM3",115200));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);*/
	//	}
	}
	/**
	 * 串口命令日志执行
	 * @param order 命令
	 * @param portName 端口名
	 * @param baudRate 波特率
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public synchronized  static String executeLogOrder(String order,String portName,int baudRate) throws UnsupportedEncodingException {
		String str="";

		if (serialPort==null) {
			openSerialPort(portName, baudRate);
		}else{

		}

		sendData(order);//发送数据
		//代替监听
		for (int i = 0; i <1000000; i++) {
			str=readData();
			if(str!=null&&str.contains("a5")&&str.indexOf("a5")+80<=str.length()){
				//System.out.println(i+" 秒： "+str);
				str=str.substring(str.indexOf("a5"),str.indexOf("a5")+80);
				//剔除空格换行符
				str=replaceSpecialStr(str).toUpperCase();
			//str.replaceAll(" ","").toUpperCase();
				System.out.println(i+" 秒： "+order);
				System.out.println(i+" 秒： "+str);
				String[] arr2 = str.split("(?<=\\G.{2})");
				if("09".equals(arr2[10])&&!"00".equals(arr2[11])){
					retuenLogOrder(str);
					str = arr2[0] + arr2[1] + "#" + arr2[2] + " " + arr2[3] + " " + arr2[4] + " " + arr2[5] + "#" + arr2[6] + " " + arr2[7] + " " + arr2[8] + " " + arr2[9]
							+ "#" + arr2[10] + " " + arr2[11] + " " + arr2[12] + "#" + arr2[13] + " " + arr2[14] + " " + arr2[15] + " " + arr2[16] + " " + arr2[17] + " " + arr2[18] + "$" + arr2[19];
					System.out.println(i + ":" + str);
					//return str;
				}
			}else{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}


		return str;
	}

	/**
	 * 串口命令执行
	 * @param order 命令
	 * @param portName 端口名
	 * @param baudRate 波特率
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public synchronized  static String executeOrder(String order,String portName,int baudRate) throws UnsupportedEncodingException {
		String str="";
		//获得系统端口列表
		//getSystemPort();
		//开启端口COM3，波特率115200
		//openSerialPort("COM3",115200);
		if (serialPort==null) {
			openSerialPort(portName, baudRate);
		}else{
			/*closeSerialPort();
			openSerialPort(portName, baudRate);*/
		}
		//发送消息
		//order=send("00000090","800001","03","80","00");

		sendData(order);//发送数据
		//代替监听

		for (int i = 0; i <1000; i++) {
			 str=readData();
			//System.out.println(str);
			if(str!=null&&str.contains("a5")&&str.indexOf("a5")+80<=str.length()){
				//System.out.println(i+" 秒： "+str);
				str=str.substring(str.indexOf("a5"),str.indexOf("a5")+80);
				str=str.replaceAll(" ","").toUpperCase();
				System.out.println(i+" 秒： "+order);
				System.out.println(i+" 秒： "+str);
				if(order.substring(2,20).equals(str.substring(2,20))) {
					String[] arr2 = str.split("(?<=\\G.{2})");
					str = arr2[0] + arr2[1] + "#" + arr2[2] + " " + arr2[3] + " " + arr2[4] + " " + arr2[5] + "#" + arr2[6] + " " + arr2[7] + " " + arr2[8] + " " + arr2[9]
							+ "#" + arr2[10] + " " + arr2[11] + " " + arr2[12] + "#" + arr2[13] + " " + arr2[14] + " " + arr2[15] + " " + arr2[16] + " " + arr2[17] + " " + arr2[18] + "$" + arr2[19];
					System.out.println(i + ":" + str);

					return str;
				}
			}else{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


			//System.out.println(i+"::"+str);
		}
		/*//设置监听
		setListenerToSerialPort( new SerialPortEventListener(){
			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {
				if(serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
						String str=readData();
						if(str!=null&&str.contains("a5")&&str.indexOf("a5")+80==str.length()){
							str=str.substring(str.indexOf("a5"),str.indexOf("a5")+80);
							str=str.replaceAll(" ","").toUpperCase();
							//System.exit(0);
							closeSerialPort();

						}
				}


			}
		});*/

		return str;
	}

	/**
	 * 获得系统可用的端口名称列表
	 * @return 可用端口名称列表
	 */
	@SuppressWarnings("unchecked")
	public static void getSystemPort(){
		List<String> systemPorts = new ArrayList<>();
		//获得系统可用的端口
		portList = CommPortIdentifier.getPortIdentifiers();
		while(portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();//获得端口的名字
			systemPorts.add(portName);
		}

	}
	/**
	 * 开启串口
	 * @param serialPortName 串口名称
	 * @param baudRate 波特率
	 * @return 串口对象
	 */
	public static void openSerialPort(String serialPortName,int baudRate) {
		try {
				//通过端口名称得到端口
				CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
				//打开端口，（自定义名字，打开超时时间）
				CommPort commPort = portIdentifier.open(serialPortName, 5000);
				//判断是不是串口
				if (commPort instanceof SerialPort) {
					serialPort = (SerialPort) commPort;
			    	//设置串口参数（波特率，数据位8，停止位1，校验位无）
			    	serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			    //	System.out.println("开启串口成功，串口名称："+serialPortName);
				}else {
					//是其他类型的端口
					throw new NoSuchPortException();
				}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 关闭串口
	 *
	 */
	public static void closeSerialPort() {
		if(serialPort != null) {
			serialPort.close();
			System.out.println("关闭了串口："+serialPort.getName());
			serialPort = null;
		}
	}
	/**
	 * 向串口发送数据
	 * @param order 发送的命令
	 */
	public static void sendData( String order) {
		//16进制表示的字符串转换为字节数组
		byte[] data =hexStringToByteArray(order);
		OutputStream os = null;
		try {
			os = serialPort.getOutputStream();//获得串口的输出流
			os.write(data);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流操作
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从串口读取数据
	 * @return 读取的数据
	 */
	public static String  readData() {
		String res="";
		InputStream is = null;
		byte[] bytes = null;
		try {
			is = serialPort.getInputStream();//获得串口的输入流
			int bufflenth = is.available();//获得数据长度
			while (bufflenth != 0) {
				bytes = new byte[bufflenth];//初始化byte数组
				is.read(bytes);
				bufflenth = is.available();
			}
			try {
				if(bytes!=null) {
					res = new String(bytes, "UTF-8");
				}
				//System.out.println(res);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return res;
	}
	/**
	 * 给串口设置监听
	 * @param listener
	 */
	public static void setListenerToSerialPort( SerialPortEventListener listener) {
		/*//设置监听
		setListenerToSerialPort( new SerialPortEventListener(){
			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {
				if(serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
						String str=readData();
						if(str.contains("a5")){
							str=str.substring(str.indexOf("a5"),str.indexOf("a5")+80);
							str=str.replaceAll(" ","").toUpperCase();
							//System.exit(0);
							closeSerialPort();

						}
				}


			}
		});*/
		try {
			//给串口添加事件监听
			serialPort.addEventListener(listener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		serialPort.notifyOnDataAvailable(true);//串口有数据监听
		serialPort.notifyOnBreakInterrupt(true);//中断事件监听

	}

	/**
	 * 16进制表示的字符串转换为字节数组
	 *
	 * @param hexString 16进制表示的字符串
	 * @return byte[] 字节数组
	 */
	public static byte[] hexStringToByteArray(String hexString) {
		hexString = hexString.replaceAll(" ", "");
		int len = hexString.length();
		byte[] bytes = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
			bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
					.digit(hexString.charAt(i + 1), 16));
		}
		return bytes;
	}


}
