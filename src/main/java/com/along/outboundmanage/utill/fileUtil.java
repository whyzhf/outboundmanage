package com.along.outboundmanage.utill;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class fileUtil {

	public static void write(){
		Writer w = null;
		BufferedWriter bw = null;
		long startTime = System.currentTimeMillis();
		try {
			// 写入文本
			File f = new File("E:\\gpsData\\demo1-json.txt");

			if (!f.exists()) {
				f.createNewFile();
			}



			w = new FileWriter(f, true);
			bw = new BufferedWriter(w);
			for (int i = 0; i <1000000 ; i++) {
				bw.write(" -------------------demo------------------------------");
			}

			long endTime = System.currentTimeMillis();
			System.out.println("运行时间:" + (endTime - startTime) + "ms");
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

	public static String read(String url){
		BufferedInputStream fis =null;
		BufferedReader reader = null;
		StringBuffer line = new StringBuffer();
		try {
			File file = new File(url);
			fis = new BufferedInputStream(new FileInputStream(file));
			reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件

			while( reader.ready()){
				line.append(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return line.toString();
	}
	public static void main(String args[]) {

		//read("E:\\gpsData\\demo.txt");


	}

	/**
	 * 按行分割文件
	 * @param sourceFilePath 为源文件路径
	 * @param targetDirectoryPath 文件分割后存放的目标目录
	 * @param rows 为多少行一个文件
	 */
	public static int splitFileByLine(String sourceFilePath, String targetDirectoryPath, int rows) {
		long startTime = System.currentTimeMillis();
		String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
		String splitFileName = targetDirectoryPath + File.separator + sourceFileName + "-demo-%s.txt";//切割后的文件名
		File targetDirectory = new File(targetDirectoryPath);
		if (!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}

		PrintWriter pw = null;//字符输出流
		String tempLine;
		int lineNum = 0;//本次行数累计 , 达到rows开辟新文件
		int splitFileIndex = 1;//当前文件索引

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
			pw = new PrintWriter(String.format(splitFileName, splitFileIndex));
			while ((tempLine = br.readLine()) != null) {
				if (lineNum > 0 && lineNum % rows == 0) {//需要换新文件
					pw.flush();
					pw.close();
					pw = new PrintWriter(String.format(splitFileName , ++splitFileIndex));
				}
				pw.write(tempLine + "\n");
				lineNum++;
			}
			long endTime = System.currentTimeMillis();
			System.out.println(splitFileIndex+"运行时间:" + (endTime - startTime) + "ms");
			return splitFileIndex;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			if (null != pw) {
				pw.flush();
				pw.close();
			}
		}

	}

}
