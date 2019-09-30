/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.along.outboundmanage.utill.GpsUtil.server;



import com.along.outboundmanage.utill.GpsUtil.util.ConvertData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


/**
 *
 * @author Administrator
 */
public class ClientTest {

	public static void main(String args[]) throws IOException {
		ClientTest client = new ClientTest();
		client.initClient(8899);
		client.lister();
	//	pp();
	}


	Selector sel;

	public void initClient(int port) throws IOException {
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		sel = Selector.open();
		boolean connect = sc.connect(new InetSocketAddress("localhost", port));
		sc.register(sel, SelectionKey.OP_CONNECT);
		System.out.println("初始化客户端成功");
	}

	public void lister() throws IOException {
		while(true){
			sel.select();
			// 监听key
			Iterator<SelectionKey> keys = sel.selectedKeys().iterator();
			System.out.println(keys.hasNext());
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				// 删除已选key，防止重复处理
				keys.remove();
				// 表示请求连接的key
				if (key.isConnectable()) {
					// 请求连接
					SocketChannel sc = (SocketChannel) key.channel();
					if (sc.finishConnect()) {
						System.out.println("客户端连接成功");
						sc.configureBlocking(false);
						sc.register(sel, SelectionKey.OP_READ);
						ByteBuffer bf = ByteBuffer.allocate(1024);
						byte[] bs = new byte[] {
								(byte)0X7E,(byte)0X02,(byte)0X00,(byte)0X00,(byte)0X5C,
								(byte)0X08,(byte)0X57,(byte)0X03,(byte)0X58,(byte)0X00,(byte)0X03,
								(byte)0X00,(byte)0X00,
								(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,
								(byte)0X80,(byte)0X0C,(byte)0X00,(byte)0X01,
								(byte)0X01,(byte)0X57,(byte)0XC1,(byte)0XE6,
								(byte)0X06,(byte)0XCA,(byte)0XA5,(byte)0XF0,
								(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,
								(byte)0X00,(byte)0X00,(byte)0X19,(byte)0X09,
								(byte)0X21,(byte)0X16,(byte)0X59,(byte)0X15,
								(byte)0X01,(byte)0X04,(byte)0X00,(byte)0X00,
								(byte)0X00,(byte)0X0E,(byte)0X30,(byte)0X01,
								(byte)0X10,(byte)0XFE,(byte)0X35,(byte)0XE6,
								(byte)0X02,(byte)0X00,(byte)0X01,(byte)0X27,(byte)0X07,(byte)0X00,(byte)0X20,(byte)0X57,(byte)0X36,(byte)0X36,(byte)0X38,(byte)0X47,(byte)0X42,(byte)0X2D,(byte)0X56,(byte)0X31,(byte)0X2E,(byte)0X30,(byte)0X32,(byte)0X44,(byte)0X3B,(byte)0X4D,(byte)0X30,(byte)0X3A,(byte)0X31,(byte)0X3B,(byte)0X4F,(byte)0X4E,(byte)0X3A,(byte)0X32,(byte)0X36,(byte)0X2C,(byte)0X34,(byte)0X31,(byte)0X38,(byte)0X33,(byte)0X36,(byte)0X3B,(byte)0X30,(byte)0X20,(byte)0X00,(byte)0X0A,(byte)0X89,(byte)0X86,(byte)0X06,(byte)0X19,(byte)0X11,(byte)0X00,(byte)0X12,(byte)0X66,(byte)0X00,(byte)0XFF,(byte)0XED,(byte)0X7E
					};
						System.out.println(bs.length);
						sc.write(bf.wrap(bs));
						System.out.println("客户端向服务端发送消息1");
					}

				}
				if (key.isReadable()) {
					System.out.println("开始读取数据...");
					SocketChannel sc = (SocketChannel) key.channel();
					ByteBuffer bf = ByteBuffer.allocate(1024);
					int len;
					StringBuilder sb = new StringBuilder();
					try {
						while ((len = sc.read(bf)) > 0) {
							bf.flip();// 翻转指针
							byte[] data = bf.array();
							for (int i = 0; i < len; i++) {
								sb.append(ConvertData.byteToHex(data[i]) + " ");
							}
							bf.clear();
						}

						if (!sb.toString().trim().equals("")) {
							// 去掉前后空格;
							System.out.println(sb.toString().trim());
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						key.cancel();
						sc.close();
					}

				}
			}
		//	break;
		}
	}
}
