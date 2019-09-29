package com.along.outboundmanage.dao;

import com.along.outboundmanage.OutboundmanageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
@SpringBootTest(classes = OutboundmanageApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CrossDaoTest {
	@Resource
	private CrossDao CrossDao;
	@Test
	public void getlist2(){
		long startTime = System.currentTimeMillis();//获取当前时间
		List<Map<String,Object>> dataList= CrossDao.getlist("2019-09-05 13:50:49","2019-09-05 14:00:49");
		long endTime = System.currentTimeMillis();//获取当前时间
		System.out.println(" 程序运行时间："+(endTime-startTime)+"ms"+" 条数："+dataList.size());
		long startTime2 = System.currentTimeMillis();//获取当前时间
		List<Map<String,Object>> dataList2= CrossDao.getlist("2019-09-05 13:50:49","2019-09-05 14:50:49");
		long endTime2 = System.currentTimeMillis();//获取当前时间
		System.out.println(" 程序运行时间2："+(endTime2-startTime2)+"ms"+" 条数2："+dataList2.size());
	}
	@Test
	public void getlist(){
		long startTime = System.currentTimeMillis();//获取当前时间
		List<Map<String,Object>> dataList=new ArrayList<>();
		ExecutorService exe = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 6; i++) {
			String data=times( "2019-09-05 13:50:49",i);
			String data2=times( "2019-09-05 13:50:49",i+1);
			exe.execute(new Thread(
				()->{
					dataList.addAll( CrossDao.getlist(data,data2));
				})
			);
		}

		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				System.out.println("结束了！");
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis();//获取当前时间
		System.out.println(" 程序运行时间："+(endTime-startTime)+"ms"+" 条数："+dataList.size());
	}

	public static String times(String time,int index){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, index*10);
		System.out.println(sdf.format(ca.getTime()));
		return sdf.format(ca.getTime());
	}

}