package com.along.outboundmanage.utill.GpsUtil;

import com.along.outboundmanage.service.GpsService;
import com.along.outboundmanage.utill.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Auther: why
 * @Data:2019/5/5
 * @Deacription:
 */
@Component
public class ScheduleUtil {

	private static GpsService gpsService;
	// 注入的时候，给类的 service 注入
	@Autowired
	public void setChatService(GpsService gpsService) {
		ScheduleUtil.gpsService= gpsService;
	}


	/**
	 * 定时器
	 * @author why
	 * @date 2019/11/14
	 * @param
	 * @return void
	 * @description  0点将当天轨迹表中数据导入历史表
	 *
	 *@Async("taskExecutor")
	 *@Scheduled(cron = "* * 17 * * ?")
	*/
	public void work(){
		//非正在进行任务的数据转入历史表
		gpsService.addhisData();
		//移除非正在进行任务的数据
		//gpsService.deleteGpslog();
		//System.out.println("start......任务1");
	}
	/**
	 * 定时器
	 * @author why
	 * @date 2019/5/5
	 * @param
	 * @return void
	 * @description  每3秒一次


	@Async("taskExecutor")
	@Scheduled(fixedRate = 3000)
	public void work2(){
		System.out.println("start......任务2");
	}
	 */
}