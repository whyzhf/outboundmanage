/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.along.outboundmanage.utill.GpsUtil.server;

import com.along.outboundmanage.utill.GpsUtil.conf.SysUtil;
import com.along.outboundmanage.utill.GpsUtil.pojo.GpsDescData;
import com.along.outboundmanage.utill.GpsUtil.util.ConvertData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;*/

/**
 *
 * @author Administrator
 */
public class IntermediatePointServer{

	private static Map<String,Map<String,List<GpsDescData>>> GPS_DATA=new HashMap<>();

	public static void gpsData(GpsDescData gdd){
		if(GPS_DATA.get(gdd.getOutboundRoadlog().getTaskId()+"")!=null){//判断该任务是否存在
			if (GPS_DATA.get(gdd.getOutboundRoadlog().getTaskId()+"").get(gdd.getOutboundRoadlog().getEquipmentId()+"")!=null){//判断该任务中是否存在该设备
				GPS_DATA.get(gdd.getOutboundRoadlog().getTaskId()+"").get(gdd.getOutboundRoadlog().getEquipmentId()+"").add(gdd);
			}else{
				List<GpsDescData> list= new ArrayList<>();
				list.add(gdd);
				GPS_DATA.get(gdd.getOutboundRoadlog().getTaskId()+"").put(gdd.getOutboundRoadlog().getEquipmentId()+"",list);
			}
		}else{
			Map<String,List<GpsDescData>> DATA=new HashMap<>();
			List<GpsDescData> list= new ArrayList<>();
			list.add(gdd);
			DATA.put(gdd.getOutboundRoadlog().getEquipmentId()+"",list);
			GPS_DATA.put(gdd.getOutboundRoadlog().getTaskId()+"",DATA);
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new IntermediatePointServer().openNettyServer(8989);
		new IntermediatePointServer().openServer(8989);
	}

	/**
	 * 服务器相关
	 */
	private ServerSocketChannel ssc;
	private Selector sel;

/*
	private CrossGpsService service = new CrossGpsService();
	Thread t = new Thread(service);
	{
		t.start();
	}
*/

	/**
	 * 启动服务
	 */
	private void openServer(int port) {
		try {

			ssc = ServerSocketChannel.open();
			ssc.bind(new InetSocketAddress(port));
			ssc.configureBlocking(false);
			sel = Selector.open();
			ssc.register(sel, SelectionKey.OP_ACCEPT);
			System.out.println("初始化服务器...");
		} catch (IOException ex) {
			Logger.getLogger(IntermediatePointServer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * 监听sel多路复合选择器
	 */
	private void listen() {
		System.out.println("开始监听...");
		while (true) {
			try {
				sel.select();
				Iterator<SelectionKey> keys = sel.selectedKeys().iterator();
				while (keys.hasNext()) {
					SelectionKey key = keys.next();
					// 删除已选key，防止重复处理
					keys.remove();
					// sel.selectNow();
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel sc = server.accept();
						sc.configureBlocking(false);
						sc.register(sel, SelectionKey.OP_READ);

						System.out.println(sc.socket().getInetAddress().getHostAddress() + ":" + sc.socket().getPort()
								+ "->tcp连接成功");
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
									// sb.append(byteToHex(data[i]) + " ");
								}
								bf.clear();
								//System.out.println("11111111111");
							}

							if (!sb.toString().trim().equals("")) {

								// 去掉前后空格
								// addClient(sc, sb.toString().trim());
								//saveMsgToLog(sc, sb.toString().trim());
								//saveData(sb.toString().trim());
								// System.out.println(sb.toString().trim());
							}

						} catch (Exception ex) {
							ex.printStackTrace();
							key.cancel();
							//sc.close();

							sc.socket().close();
							sc.close();
						}

					}

				}
			} catch (IOException ex) {
				Logger.getLogger(IntermediatePointServer.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

	/**
	 * 将消息写入日志文件
	 * 
	 * @param msg
	 */
	private synchronized void saveMsgToLog(ChannelHandlerContext sc, String msg) {
		String hexStr = msg;
		String txt = ConvertData.getHexMsgToString(msg);
		InetSocketAddress isa = (InetSocketAddress) sc.channel().remoteAddress();
		StringBuilder sb = new StringBuilder();

		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		String address = isa.getAddress().getHostAddress();
		String port = isa.getPort() + "";
		sb.append(time + ";");
		sb.append(address + ";");
		sb.append(port + ";");
		// sb.append();

		Writer w = null;
		BufferedWriter bw = null;
		Writer w1 = null;
		BufferedWriter bw1 = null;
		try {
			String FileName = new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()) + ".txt";
			String FileName1 = new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()) + "-hex.txt";
			File dir = new File(SysUtil.LOG_LOCATION);

			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 写入文本
			File f = new File(dir + "\\" + FileName);
			File f1 = new File(dir + "\\" + FileName1);
			if (!f.exists()) {
				f.createNewFile();
			}
			w = new FileWriter(f, true);
			bw = new BufferedWriter(w);
			bw.write(sb.toString() + txt + "\r\n");
			// 写入16进制
			if (!f1.exists()) {
				f1.createNewFile();
			}
			w1 = new FileWriter(f1, true);
			bw1 = new BufferedWriter(w1);
			bw1.write(sb.toString() + hexStr + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				w.close();
				bw1.close();
				w1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 保存到数据库
	 * 
	 * @param hexData
	 */
	private void saveData(ChannelHandlerContext ctx, String hexData) {
		// gps上传数据
		if (hexData.startsWith("7E 02 00")) {
			// 检查数据有效性
			if (ConvertData.checkData(hexData)) {
				//saveMsgToLog(ctx, hexData);
				String data = ConvertData.getHexMsgToString(hexData);
				String[] strArr = data.split(";");
				System.out.println(data);
				//service.updateData(strArr);

			} else {
				System.out.println("数据格式校验未通过...");
			}

		} else if (hexData.startsWith("7E 07 04")) {// 批量上传或是补传

		} else {
			// 不处理
		}

	}

	/**
	 * 基于netty启动服务，并开启监听
	 *
	 */

	public void openNettyServer(int port) {

		EventLoopGroup group = new NioEventLoopGroup();// 连接服务对象
		EventLoopGroup workGroup = new NioEventLoopGroup();// 读写服务对象
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group, workGroup).channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					.option(ChannelOption.SO_BACKLOG, 2048)//serverSocketchannel的设置，链接缓冲池的大小
					.childOption(ChannelOption.SO_KEEPALIVE, true)//socketchannel的设置,维持链接的活跃，清除死链接
					.childOption(ChannelOption.TCP_NODELAY, true)//socketchannel的设置,关闭延迟发送
					.childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {

						@Override
						public void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
							// ch.pipeline().addLast(new
							// GpsMsgEncoder(1024,3,2,10,0));//处理断包
							ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
								@Override
								protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bf) throws Exception {
									// System.out.println("开始读取数据...");
									StringBuilder sb = new StringBuilder();
									try {
										byte[] data = new byte[bf.readableBytes()];
										bf.readBytes(data);

										for (int i = 0; i < data.length; i++) {
											sb.append(ConvertData.byteToHex(data[i]) + " ");
										}

										// 转义
										String hexStr = ConvertData.replaceData(sb.toString().trim());
										//saveMsg(ctx, hexStr);
										//数据处理
										//saveMsgToLog(ctx, hexStr);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}

								/**
								 * 活动状态
								 */
								@Override
								public void channelActive(ChannelHandlerContext ctx) throws Exception {
									System.out.println(ctx.channel().remoteAddress() + "->tcp连接成功");
								}

								/**
								 * 断开状态
								 */
								@Override
								public void channelInactive(ChannelHandlerContext ctx) throws Exception {
							
								}

								@Override
								public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
								}


								/**
								 * 异常
								 */
								@Override
								public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
										throws Exception {
									cause.printStackTrace();
									ctx.close();// 关闭客户端
								}

								@Override
								public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
									// TODO Auto-generated method stub
									super.channelRead(ctx, msg);
								}
							});
						}
					});

			// 绑定端口，并开启异步阻塞
			ChannelFuture f = b.bind(port).sync();
			System.out.println(f.toString());
			System.out.println("服务器启动，开始监听：" + f.channel().localAddress());
			// 等待客户端关闭，并阻塞，阻止main运行结束
			f.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param ctx
	 * @param hexData
	 */
	private void saveMsg(ChannelHandlerContext ctx, String hexData) {
		if (!hexData.equals("")) {
			// 去掉前后空格
			saveData(ctx, hexData);


		}
	}


}
