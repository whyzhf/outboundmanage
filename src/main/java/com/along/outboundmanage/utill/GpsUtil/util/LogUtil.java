package com.along.outboundmanage.utill.GpsUtil.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 写入日志工具类
 * @author Administrator
 *
 */

public  class LogUtil {
	/**
	 * 写入日志信息
	 * @param msg 消息

	 */
	public synchronized static void writeLog(String msg){
		InputStream is = LogUtil.class.getResourceAsStream("/app.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException ex) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());   
		Writer w = null;
		BufferedWriter bw = null;
		try {
			String FileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
			File dir = new File(p.getProperty("console.log.local"));
			
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			File f = new File(dir+"\\"+FileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			w = new FileWriter(f, true);
			bw = new BufferedWriter(w);
			bw.write(dateTime+" -> "+msg + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	
}
