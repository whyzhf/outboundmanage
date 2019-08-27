package com.along.outboundmanage.utill;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import static com.along.outboundmanage.utill.EquipUtil.send;

public class RXTXtest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//获得系统端口列表
		getSystemPort();
		//开启端口COM2，波特率9600
		final SerialPort serialPort = openSerialPort("COM3",115200);
		//启动一个线程每2s向串口发送数据，发送1000次hello
		new Thread(()->{
			String s = "AA14000C34FC000C35010C80001208020A31211F";
			s=send("00000090","800001","03","80","00");
			System.out.println(s);
			byte[] bytes =hexStringToByteArray(s);
			//byte[] bytes = s.getBytes();
			RXTXtest.sendData(serialPort, bytes);//发送数据

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 1;
				while(i<1000) {
					String s = "AA14000C34FC000C35010C80001208020A31211F";
					s=send("00000090","800001","03","80","00");
					System.out.println(s);
					byte[] bytes =hexStringToByteArray(s);
					//byte[] bytes = s.getBytes();
					RXTXtest.sendData(serialPort, bytes);//发送数据
					i++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();*/
		//设置串口的listener
		RXTXtest.setListenerToSerialPort(serialPort, new SerialPortEventListener() {
			@Override
			public void serialEvent(SerialPortEvent arg0) {
				if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
					byte[] bytes = RXTXtest.readData(serialPort);
					//readData2(serialPort);
					System.out.println("收到的数据长度："+bytes.length);
					try {
						if (bytes.length==82)
						System.out.println("收到的数据："+new String(bytes, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		});
//        closeSerialPort(serialPort);
	}
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if(hex.length() < 2){
				sb.append(0);
			}
			sb.append(hex);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 获得系统可用的端口名称列表
	 * @return 可用端口名称列表
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getSystemPort(){
		List<String> systemPorts = new ArrayList<>();
		//获得系统可用的端口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while(portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();//获得端口的名字
			systemPorts.add(portName);
		}
		System.out.println("系统可用端口列表："+systemPorts);
		return systemPorts;
	}

	/**
	 * 开启串口
	 * @param serialPortName 串口名称
	 * @param baudRate 波特率
	 * @return 串口对象
	 */
	public static SerialPort openSerialPort(String serialPortName,int baudRate) {
		try {
			//通过端口名称得到端口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
			//打开端口，（自定义名字，打开超时时间）
			CommPort commPort = portIdentifier.open(serialPortName, 2222);
			//判断是不是串口
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				//设置串口参数（波特率，数据位8，停止位1，校验位无）
				serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				System.out.println("开启串口成功，串口名称："+serialPortName);
				return serialPort;
			}
			else {
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
		return null;
	}

	/**
	 * 关闭串口
	 * @param serialPort 要关闭的串口对象
	 */
	public static void closeSerialPort(SerialPort serialPort) {
		if(serialPort != null) {
			serialPort.close();
			System.out.println("关闭了串口："+serialPort.getName());
			serialPort = null;
		}
	}

	/**
	 * 向串口发送数据
	 * @param serialPort 串口对象
	 * @param data 发送的数据
	 */
	public static void sendData(SerialPort serialPort, byte[] data) {
		OutputStream os = null;
		try {
			os = serialPort.getOutputStream();//获得串口的输出流
			os.write(data);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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

	public static void  readData2(SerialPort serialPort){
		//1.定义变量

		InputStream inputStream = null;//端口输入流

		try{

			//3.获取串口的输入流对象
			inputStream = serialPort.getInputStream();

			//4.从串口读入数据
			//定义用于缓存读入数据的数组
			byte[] cache = new byte[1024];
			//记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
			int availableBytes = 0;

			//无限循环，每隔20毫秒对串口COM21进行一次扫描，检查是否有数据到达
			while(true){
				//获取串口COM21收到的可用字节数
				availableBytes = inputStream.available();
				//如果可用字节数大于零则开始循环并获取数据
				while(availableBytes > 0){
					//从串口的输入流对象中读入数据并将数据存放到缓存数组中
					inputStream.read(cache);
					//将获取到的数据进行转码并输出
					for(int j = 0;j < cache.length && j < availableBytes; j++){
						//因为COM11口发送的是使用byte数组表示的字符串，
						//所以在此将接收到的每个字节的数据都强制装换为char对象即可，
						//这是一个简单的编码转换，读者可以根据需要进行更加复杂的编码转换。
						System.out.print((char)cache[j]);
					}
					System.out.println();
					//更新循环条件
					availableBytes = inputStream.available();
				}
				//让线程睡眠20毫秒
				Thread.sleep(20);
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		} catch (IOException e) {
			//如果获取输出流失败，则抛出该异常
			e.printStackTrace();
		}
	}

	/**
	 * 从串口读取数据
	 * @param serialPort 要读取的串口
	 * @return 读取的数据
	 */
	public static byte[] readData(SerialPort serialPort) {
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

		return bytes;
	}

	/**
	 * 给串口设置监听
	 * @param serialPort
	 * @param listener
	 */
	public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
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
