package com.along.outboundmanage.service.impl;

import com.along.outboundmanage.dao.RoadLogDao;
import com.along.outboundmanage.model.OutboundRoadlog;
import com.along.outboundmanage.service.RoadLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoadLogServiceImpl implements RoadLogService {
	@Resource
	private RoadLogDao roadLogDao;

	//存储路线（gps位置）
	@Override
	public boolean saveGpsRoute(OutboundRoadlog outboundRoadlog) {
		return roadLogDao.insertRoadLog(outboundRoadlog);
	}
	//存储围栏（gps位置）
	@Override
	public boolean saveFence(OutboundRoadlog outboundRoadlog) {
		return roadLogDao.insertRoadLog(outboundRoadlog);
	}
	//清除记录（gps位置）
	@Override
	public boolean delRoadLog(int id) {
		return roadLogDao.delRoadLog(id);
	}


	/**
	 * 获取当前位置
	 * @author why
	 * @description
	 *   1.解析数据
	 *   2.发送前端
	 *   3.异步存入数据库
	 * */
	public OutboundRoadlog getCurrRoad() {
		//获取gps定位信息
		//解析数据
		new Thread(()->{
			//存入数据库
		}).start();
		return null;
	}
	/**
	 * 历史轨迹
	 * @author why
	 * @description
	 *   历史路线信息可考虑将查询结果（在任务完成后，或定期）封装成json存入数据库或写在磁盘
	 * */
	public List<OutboundRoadlog> getHisRoad(){
		return null;
	}

	/**
	 * 抓捕路线
	 * @author why
	 * @description
	 *   该操作与路线查询一致
	 * */
	public List<OutboundRoadlog> getArrestRoute(){
		return null;
	}

	/**
	 * 制伏
	 * @author why
	 * @description
	 *   1.连接硬件
	 *   2.发送制伏命令
	 *   3.返回状态
	 * */
	public int getSubdue(){

		return 0;
	}
}
