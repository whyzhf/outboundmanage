package com.along.outboundmanage.service;

import com.along.outboundmanage.model.EchartsData;
import com.along.outboundmanage.model.EchartsMapData;
import com.along.outboundmanage.model.EchartsPieData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViewsService {

	/********************************省厅视图*************************************/
	//地域图
	List<EchartsMapData> getMap(String area);
	//占比图
	EchartsPieData getPie(String area);
	//在押
	EchartsData getCustody(String area);
	//外出
	EchartsData getOut(String area);
	//指认
	EchartsData getDesignate(String area);
	//出庭，投牢，审讯
	EchartsData getOthers(String area);

	/********************************地市视图*************************************/
	//地域图
	List<EchartsMapData> getSecMap(String area);
	//占比图
	EchartsPieData getSecPie(String area);
	//在押
	EchartsData getSecCustody(String area);
	//外出
	EchartsData getSecOut(String area);
	//指认
	EchartsData getSecDesignate(String area);
	//出庭，投牢，审讯
	EchartsData getSecOthers(String area);

}
