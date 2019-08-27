package com.along.outboundmanage.utill;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import static com.along.outboundmanage.utill.EquipUtil.send;
import static com.along.outboundmanage.utill.HexadecimalUtil.*;

public class FirdayUtil implements SerialPortEventListener{
	private static final String DEMONAME = "串口测试";

	/**
	 * 检测系统中可用的端口
	 */
	private CommPortIdentifier portId;
	/**
	 * 枚举类
	 */
	private Enumeration portList;
	/**
	 * 输入流
	 */
	private static InputStream inputStream;
	/**
	 * RS-232的串行口
	 */
	private SerialPort serialPort;
	/**
	 * 串口返回信息
	 */
	private static String test = "";

	/**
	 * 初始化
	 *
	 * @param baudRate 波特率
	 */
	public void init(int baudRate) {
		//获取系统中可用的端口

		portList = CommPortIdentifier.getPortIdentifiers();
		if(portList==null){
			System.out.println("sssssssss");
		}else{
			System.out.println("res:"+portList);
			System.out.println("res:"+portList.hasMoreElements());
		}

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL && portId.getName().equals("COM3")) {
				System.out.println("发现端口："+portId.getName());
				try {
					serialPort = (SerialPort) portId.open(DEMONAME,20000);

					System.out.println("打开端口。。。");
					//设置串口监听
					serialPort.addEventListener(new FirdayUtil());
					//设置开启监听
					serialPort.notifyOnDataAvailable(true);
					//设置波特率、数据位、停止位、检验位
					serialPort.setSerialPortParams(baudRate,
							SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					OutputStream outputStream = serialPort.getOutputStream();
					byte[] out=send("00000090","800001","0D","01","00").getBytes();
					outputStream.write("AA14000C34FC000C35010C80001208020A31211F".getBytes());
					outputStream.flush();
					//4.3.关闭输出流
					//outputStream.close();
					//获取输入流
					inputStream = serialPort.getInputStream();
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (TooManyListenersException e) {
					e.printStackTrace();
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 监听函数
	 * @param serialPortEvent
	 */
	public void serialEvent(SerialPortEvent serialPortEvent) {
		switch (serialPortEvent.getEventType()) {
			//获取到有效信息
			case SerialPortEvent.DATA_AVAILABLE :
				readComm();
				break;
			default:
				break;
		}
	}

	/**
	 * 读取串口信息
	 */
	private void readComm() {
		byte[] readBuffer = new byte[20];
		try {
			int len = 0;
			while ((len = inputStream.read(readBuffer)) != -1) {
				test += new String(readBuffer,0, len).trim();
				break;
			}
			for (byte b : readBuffer) {
				if (b < 0) {
					//byte的范围是-128到+127
					int i = 128 + (int) b + 127 + 1;
					//转换成16进制
					System.out.println("读取的信息："+Integer.toHexString(i));
				}else {
					System.out.println("读取的信息："+Integer.toHexString(b));
				}

			}
			System.out.println("test: "+test);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void writeComm() {
		try {
			//4.1.获取串口的输出流对象
			System.out.println(serialPort.getName());
			OutputStream outputStream = serialPort.getOutputStream();
			//4.2.通过串口的输出流向串口写数据“Hello World!”：
			//使用输出流往串口写数据的时候必须将数据转换为byte数组格式或int格式，
			//当另一个串口接收到数据之后再根据双方约定的规则，对数据进行解码。
			outputStream.write(new byte[]{'A','A','1','4','0',
					'0','0','0','0','0','0','0'});
			outputStream.flush();
			//4.3.关闭输出流
			outputStream.close();

			//5.关闭串口
			serialPort.close();

		} catch (IOException e) {
			//如果获取输出流失败，则抛出该异常
			e.printStackTrace();
		}

	}
	private void close() {
		serialPort.close();
	}

	public static void main(String[] args) {
		new FirdayUtil().init(115200);
		//new FirdayUtil().writeComm();

	}
}


