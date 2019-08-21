package com.along.outboundmanage.dao;

import com.along.outboundmanage.OutboundmanageApplication;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPrisoner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = OutboundmanageApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PrisonerDaoTest {
	@Resource
	private PrisonerDao PrisonerDao;
	@Resource
	private PoliceDao PoliceDao;
	@Test
	public void getEquipmentID(){
		List<OutboundPrisoner> prisonerList= PrisonerDao.getPrisonerByIds("100");
		List<String> list = prisonerList.stream().map(e->e.getEquipmentId()+"").collect(Collectors.toList());
		String str=String.join(",",  list);

		System.out.println("wwww:"+str);
	}
	@Test
	public void getEquipID(){
		List<OutboundPolice> prisonerList= PoliceDao.getPoliceByIds("100");
		List<String> list = prisonerList.stream().map(e->
				(e.getEquipmentId()!=null?e.getEquipmentId():"0")+","+(e.getEquipmentId2()!=null?e.getEquipmentId2():"0")
		).collect(Collectors.toList());
		String str=String.join(",",  list);
		System.out.println("wwww:"+str);
	}
	@Test
	public  void demo(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		date = calendar.getTime();
		String accDate = format.format(date);
		System.out.println(accDate);
	}

	@Test
	public  void demo1(){

	}
}