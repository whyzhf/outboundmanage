package com.along.outboundmanage.utill;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: why
 * @Data:2019/5/5
 * @Deacription:
 */
@Component
public class ScheduleUtil {

	/**
	 * 定时器
	 * @author why
	 * @date 2019/11/14
	 * @param
	 * @return void
	 * @description  0点将当天轨迹表中数据导入历史表
	 *
	 *@Async("taskExecutor")
	 *@Scheduled(cron = "* * 0 * * ?")
	*/
	public void work(){
		System.out.println("start......任务1");
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