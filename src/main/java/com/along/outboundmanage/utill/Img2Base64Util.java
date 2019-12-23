package com.along.outboundmanage.utill;

import java.io.*;

import org.apache.commons.codec.binary.Base64;


/**
 * 将图片转换为Base64<br>
 * 将base64编码字符串解码成img图片
 * @创建时间 2019-01-05 10:43:12
 *
 */
public class Img2Base64Util {

	public static void main(String[] args) {
		File file=new File("E:\\Project");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File fs = files[i];
			if(fs.getName().indexOf("1.")!=-1){
					System.out.println(fs.getName());
			}
		}
		/*String imgFile = "E:\\Project\\1.gif";//待处理的图片
		String imgbese=getImgStr(imgFile);
		System.out.println(imgbese.length());
		System.out.println(imgbese);
		String imgFilePath ="E:\\Project\\2.gif";//新生成的图片
		generateImage(imgbese,imgFilePath);*/
	}
	public  static String getUrl(String areaId){
		String url="";
		File file=new File("/usr/tomcat/tomcat8/gps/arealog");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File fs = files[i];
			if(fs.getName().indexOf("1.")!=-1){
				//System.out.println(fs.getName());
				return url="/usr/tomcat/tomcat8/gps/arealog/"+url;

			}
		}

		return null;
	}
	/**
	 * 将图片转换成Base64编码
	 * @param imgFile 待处理图片
	 * @return
	 */
	public static String getImgStr(String imgFile){
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		//读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr 图片数据
	 * @param imgFilePath 保存图片全路径地址
	 * @return
	 */
	public static boolean generateImage(String imgStr,String imgFilePath){
		if (imgStr == null) { //图像数据为空
			return false;
		}
		try{
			//Base64解码
			byte[] b = Base64.decodeBase64(imgStr);
			for(int i=0;i<b.length;++i){
				if(b[i]<0){//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}catch (Exception e){
			return false;
		}
	}
}