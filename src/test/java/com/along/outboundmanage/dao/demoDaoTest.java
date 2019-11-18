package com.along.outboundmanage.dao;

import com.along.outboundmanage.OutboundmanageApplication;
import com.along.outboundmanage.model.HisGpsData;
import com.along.outboundmanage.model.NgpsData;
import com.along.outboundmanage.utill.GpsUtil.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.along.outboundmanage.utill.JdbcUtils.*;


@SpringBootTest(classes = OutboundmanageApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class demoDaoTest {
	private final Logger logger = LoggerFactory.getLogger(demoDaoTest.class);
	@Resource
	private demoDao demoDao;
	@Test
	public void demo(){

		long startTime = System.currentTimeMillis();//获取当前时间

		try {
			for (int i = 0; i <10 ; i++) {
				Map<String, HisGpsData> stringHisGpsDataMap = queryHisGpsList("select  taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, \n" +
						"longitude, latitude, color  from outbound_gpslogdemo where taskId=2  limit 500", null, "id,taskId,equip,equipCard,police,prisoner,stauts,errorStatus,uptime,type,longitude,latitude,lot,lat,speed,direction,color".split(","));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//List<NgpsData> dataList = demoDao.getDataList();
		long endTime = System.currentTimeMillis();//获取当前时间
		System.out.println(" 程序运行时间："+(endTime-startTime)+"ms");
		// demoDao.getDataDemoList();
		long endTime2 = System.currentTimeMillis();//获取当前时间
		System.out.println(" 程序运行时间："+(endTime2-endTime)+"ms");
	}


	@Test
	public void demo2(){
		long startTime = System.currentTimeMillis();//获取当前时间
		query("select id, taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, `type`, longitude, latitude, lot, lat, speed, direction, color " +
				" from outbound_gpslogdemo where taskId=2  limit 8000");

		//demoDao.getlongList();
		long endTime = System.currentTimeMillis();//获取当前时间
		logger.error(" 程序运行时间："+(endTime-startTime)+"ms");

		//demoDao.getlongDemoList();
		//long endTime2 = System.currentTimeMillis();//获取当前时间
		//logger.error(" 程序运行时间2："+(endTime2-endTime)+"ms");

	}
	@Test
	public  void demo3(){
		//创建1个拥有100线程的定长线程池，发送2000个请求
		//超出的线程会排队等候
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 20; i++) {
			newFixedThreadPool.submit(new Callable<List<Map<String, String>>>() {
				@Override
				public List<Map<String, String>> call() throws Exception {
					try {
						return  queryEntityList("select id, taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, `type`, longitude, latitude, lot, lat, speed, direction, color " +
								" from outbound_gpslogdemo where taskId=1  limit 1000", null, "id,taskId,equip,equipCard,police,prisoner,stauts,errorStatus,uptime,type,longitude,latitude,lot,lat,speed,direction,color".split(","));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

			});

		}
		newFixedThreadPool.shutdown();

	}

	@Test
	public void asyncGet()
			throws Exception {
		List<Map<String, String>> objectList = new ArrayList<>();
		List<String> list=new ArrayList<>();
		list.add("1000 ");
		list.add("1000 ");
		list.add("1000 ");
		list.add("1000 ");
		list.add("1000 ");
	//	list.add("A6 ");
	//	list.add("A7 ");
	//	list.add("A8 ");
	//	list.add("A9 ");
		long startTime = System.currentTimeMillis();//获取当前时间
		CompletableFuture[] futures = list.stream()
				.map(p -> CompletableFuture.supplyAsync(() -> {
							try {
								return  queryEntityList("select id, taskId, equip, equipCard, police, prisoner, stauts, errorStatus, uptime, `type`, longitude, latitude, lot, lat, speed, direction, color " +
										" from outbound_gpslogdemo where taskId=1  limit 1000", null, "id,taskId,equip,equipCard,police,prisoner,stauts,errorStatus,uptime,type,longitude,latitude,lot,lat,speed,direction,color".split(","));
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}

						})
				).toArray(CompletableFuture[]::new);
		CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futures);
		//completableFuture.get();
		//completableFuture.get(3000, TimeUnit.MILLISECONDS);
		for (CompletableFuture future:futures) {
			objectList.addAll((List<Map<String, String>>)future.get());
		}
		long startTime2 = System.currentTimeMillis();//获取当前时间
		System.out.println(startTime2-startTime  +"   "+objectList.size());
		//return objectList;
	}
	@Test
	public void  getMaxResult() throws Exception {
		long start = System.currentTimeMillis();//开始时间

		List<HisGpsData> list=new ArrayList<>();

		for (int i = 0; i < 100000; i++) {
			list.add(new HisGpsData.Builder().color("1")
					.equipCard("1")
					.prisoner("1")
					.police("1").build());
		}

		long end = System.currentTimeMillis();
		System.out.println("线程查询数据用时:"+(end-start)+"ms");
		//return result;
	}


}