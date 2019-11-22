package com.along.outboundmanage.utill.GpsUtil;


import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.model.HisGpsData;
import com.along.outboundmanage.model.HistData;
import org.checkerframework.checker.units.qual.K;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.along.outboundmanage.utill.GpsUtil.MyThread.asyncGet;

/**
 * 读取文件数据
 * */
public class FileUtil {
	public static void main(String[] args) throws Exception {
		//System.out.println("548".split("-")[0]);
		Map<String, HisGpsData> stringHisGpsDataMap = new FileUtil().readFileReturnMap2("E:\\gpsData\\548-json.txt");
	//	System.out.println(stringHisGpsDataMap.size());
		stringHisGpsDataMap.forEach((K,V)->{
			System.out.println(K+" # "+V.toString());
		});

	}

	//获取单个文件里的值
	public static 	Map<String,HisGpsData> getData2(String taskId){
		//String path =SysUtil.LOCAL_DATA_LOCATION;
		String path =SysUtil.WEB_DATA_LOCATION;
		String fileName=FindFile(taskId);
		String url=path+"/"+fileName;
		try {
			//return readFile(url);
			return readFileReturnMap2(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取单个文件里的值
	public static 	List<HistData> getData(String taskId){
		//String path =SysUtil.LOCAL_DATA_LOCATION;
		String path =SysUtil.WEB_DATA_LOCATION;
		String fileName=FindFile(taskId);
		String url=path+"/"+fileName;
		try {
			//return readFile(url);
			return readFileReturnMap(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//获取多个文件里的值
	public static List<String> getDataList(String taskId) throws Exception {
		String path =SysUtil.WEB_DATA_LOCATION;
		//String path =SysUtil.WEB_DATA_LOCATION;
		List<String> fileNames=FindFiles(taskId,path);
		return asyncGet(fileNames);
	}



	//读取文件
	public static	List<HistData>  readFileReturnMap(String path) throws IOException {
		Map<String,List<JSONObject>> map=new HashMap<>();
		List<JSONObject> list=null;
		int i=0;
		long start = System.currentTimeMillis();
		File file = new File(path );
		String equip="";
	//	System.out.println("start.....");
		FileInputStream fis = new FileInputStream(file);
		UnicodeReader unicodeReader = new UnicodeReader(fis, Charset.defaultCharset().name());
		BufferedReader br = new BufferedReader(unicodeReader);
		String tempString=null;
		while (( tempString = br.readLine()) != null) {
			//System.out.println(br.readLine());
			//br.readLine();
			JSONObject jsonObject = JSONObject.parseObject(tempString);
			equip=jsonObject.get("equip")+"";

			if(map.get(equip)!=null){
				map.get(equip).add(jsonObject);
			}else{

				list=new ArrayList<>();
				list.add(jsonObject);
				map.put(jsonObject.get("equip")+"",list);
			}
			i++;
			//res.add(jsonObject.toString());
		}
		List<HistData> resList=new ArrayList<>();

		map.forEach((K,V)->{
			resList.add(new HistData(K,V));
		});

		unicodeReader.close();

		fis.close();
		br.close();
		long end = System.currentTimeMillis();
		System.out.println(i/10000.0+" w条数据   readTxt1方法，使用内存="+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/ 1024 / 1024 + "M"+",使用时间秒="+(end-start)/1000.0+"s");

		return resList;
	}


	//读取文件
	public static	Map<String,HisGpsData>  readFileReturnMap2(String path) throws IOException {
		Map<String,HisGpsData> map=new HashMap<>();
		int i=0;
		long start = System.currentTimeMillis();
		File file = new File(path );
		//	System.out.println("start.....");
		FileInputStream fis = new FileInputStream(file);
		UnicodeReader unicodeReader = new UnicodeReader(fis, Charset.defaultCharset().name());
		BufferedReader br = new BufferedReader(unicodeReader);
		String tempString=null;
		String equipCard=null;
		List<BigDecimal[]>gpslist=null;
		List<String>timelist=null;
		while (( tempString = br.readLine()) != null ) {
			if ("".equals(tempString)){
				break;
			}
			JSONObject jsonObject = JSONObject.parseObject(tempString);
		//	System.out.println(jsonObject.get("longitude"));
			equipCard=jsonObject.get("equipCard")+"";
			if(map.get(equipCard)!=null){
				map.get(equipCard).getGpsData().add(new BigDecimal[]{new BigDecimal(jsonObject.get("longitude")+""),new BigDecimal(jsonObject.get("latitude")+"")});
				map.get(equipCard).getGpsTime().add(jsonObject.get("uptime")+"");
			}else{//首次加载
				gpslist=new ArrayList<>();
				timelist=new ArrayList<>();
				timelist.add(jsonObject.get("uptime")+"");
				gpslist.add(new BigDecimal[]{new BigDecimal(jsonObject.get("longitude")+""),new BigDecimal(jsonObject.get("latitude")+"")});
				map.put(equipCard,new HisGpsData.Builder().color(jsonObject.get("color")+"")
						.equipCard(jsonObject.get("equipCard")+"")
						.prisoner(jsonObject.get("prisoner")+"")
						.police(jsonObject.get("police")+"")
						.gpsData(gpslist)
						.gpsTime(timelist)
						.taskId(Integer.valueOf(jsonObject.get("taskId")+""))
						.taskName(jsonObject.get("taskName")+"")
						.build()

				);
			}
			i++;
			//res.add(jsonObject.toString());
		}

		unicodeReader.close();
		fis.close();
		br.close();
		long end = System.currentTimeMillis();
		System.out.println(i/10000.0+" w条数据   readTxt1方法，使用内存="+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/ 1024 / 1024 + "M"+",使用时间秒="+(end-start)/1000.0+"s");
		return map;
	}

	//读取文件
	public static List<String> readFile(String path) throws IOException {
		List<String> list=new ArrayList<>();
		int i=0;
		long start = System.currentTimeMillis();
		File file = new File(path );
		/*Reader in = new FileReader(file);
		BufferedReader br = new BufferedReader(in);*/
		FileInputStream fis = new FileInputStream(file);
		UnicodeReader unicodeReader = new UnicodeReader(fis, Charset.defaultCharset().name());
		BufferedReader br = new BufferedReader(unicodeReader);
		String tempString=null;
		while (( tempString = br.readLine()) != null) {
			//System.out.println(br.readLine());
			//br.readLine();
			//JSONObject jsonObject = JSONObject.parseObject(br.readLine());
			list.add(tempString);
			i++;
		}

		//in.close();
		unicodeReader.close();
		fis.close();
		br.close();
		long end = System.currentTimeMillis();
	//	System.out.println(i/10000.0+" w条数据   readTxt1方法，使用内存="+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/ 1024 / 1024 + "M"+",使用时间毫秒="+(end-start));

		return list;
	}




	//获取文件名
	public static List<String> FindFiles(String taskId,String path){
		List<String> pathList=new ArrayList<>();
		// 设置日期转换格式
		SimpleDateFormat smp = new SimpleDateFormat("yyMMddHHmmss");
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().startsWith(taskId+"-") && !files[i].isDirectory()) {
					pathList.add(path+"/"+files[i].getName());
				}
			}
		}
		// 打印符合要求的文件名
		return pathList;
	}


	//获取最新文件名
	public static String FindFile(String taskId){
		// 设置日期转换格式
		SimpleDateFormat smp = new SimpleDateFormat("yyMMddHHmmss");
		String path =SysUtil.WEB_DATA_LOCATION;
		//String path =SysUtil.LOCAL_DATA_LOCATION;
		File file = new File(path);
		// 定义文件修改时间
		long modify_time = 0;
		long tmp = 0;
		String fileName = "";
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().startsWith(taskId+"-") && !files[i].isDirectory()) {
					//获取文件修改日期
					modify_time = Long.parseLong(smp.format(new Date(files[i].lastModified())));
					//对比获得最新修改的文件
					if (modify_time > tmp) {
						fileName = files[i].getName();
						tmp = modify_time;
					}
				}
			}
		}
		// 打印符合要求的文件名
		System.out.println(fileName);
		return fileName;
	}


}
