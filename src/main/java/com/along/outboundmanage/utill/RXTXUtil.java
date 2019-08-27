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

import static com.along.outboundmanage.utill.EquipUtil.send;

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

	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		//init();
		//System.out.println(init());
		String[] arr={"00","02","04","08","10","20","40","80"};
		for (int i = 0; i < 8; i++) {
			try {
				System.out.println(	init(send("00000090","800001","01",arr[i],"00"),"COM3",115200));

				//System.out.println(	init(send("00000090","800001","03","40","00"),"COM3",115200));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}



	}

	/**
	 * 串口命令执行
	 * @param order 命令
	 * @param portName 端口名
	 * @param baudRate 波特率
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public synchronized  static String init(String order,String portName,int baudRate) throws UnsupportedEncodingException {
		String str="";
		//获得系统端口列表
		//getSystemPort();
		//开启端口COM3，波特率115200
		//openSerialPort("COM3",115200);
		if (serialPort==null) {
			openSerialPort(portName, baudRate);
		}
		//发送消息
		//order=send("00000090","800001","03","80","00");

		sendData(order);//发送数据
		//代替监听
		for (int i = 0; i <100; i++) {
			 str=readData();
			if(str!=null&&str.contains("a5")&&str.indexOf("a5")+80==str.length()){
				System.out.println(str);
				str=str.substring(str.indexOf("a5"),str.indexOf("a5")+80);
				str=str.replaceAll(" ","").toUpperCase();
				closeSerialPort();

				String[]arr2 = str.split("(?<=\\G.{2})");
				str= arr2[0]+arr2[1]+"#"+arr2[2]+" "+arr2[3]+" "+arr2[4]+" "+arr2[5]+"#"+arr2[6]+" "+arr2[7]+" "+arr2[8]+" "+arr2[9]
						+"#"+arr2[10]+" "+arr2[11]+" "+arr2[12]+"#"+arr2[13]+" "+arr2[14]+" "+arr2[15]+" "+arr2[16]+" "+arr2[17]+" "+arr2[18]+"$"+arr2[19];
				return str;
			}else{
				try {
					Thread.sleep(100);
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
						if(str.contains("a5")){
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
			CommPort commPort = portIdentifier.open(serialPortName, 2222);
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
				if(bytes!=null)
				  res=new String( bytes, "UTF-8");
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
		System.out.println(len+"  "+hexString);
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
			bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
					.digit(hexString.charAt(i + 1), 16));
		}
		return bytes;
	}


}
