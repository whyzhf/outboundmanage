package com.along.outboundmanage.service.impl;

import com.along.outboundmanage.dao.AreaDao;
import com.along.outboundmanage.dao.ViewDao;
import com.along.outboundmanage.model.*;
import com.along.outboundmanage.service.ViewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.*;

@Service
public class ViewsServiceImpl implements ViewsService {
	@Resource
	ViewDao viewDao;
	@Resource
	AreaDao areaDao;
	@Override
	public EchartsData getCustody(String area) {
		String data = getLastMonth("yyyy-MM");
		EchartsData result=new EchartsData();
		String name=data.replace("-","年");
		if ("16".equals(area)){
			area="";
			result.setTitleName(name+"月全省监所在押情况");
		}else{
			String id="0";
			List<Integer> ids = areaDao.getAreaIdsByPar(Integer.valueOf(area));
			id=ids.stream().map(e->e+"").collect(Collectors.joining(","));
			area=" and area_id in("+id+") ";
			result.setTitleName(name+"月全市监所在押情况");

		}

		int countFirDay = viewDao.getCountByDate(data+"-01  23:59:59", area);
		List<PCount> countList = viewDao.getAddDate(area);
		Map<String, PCount> countMap=new HashMap<>();
		if (null!=countList) {
			countMap = countList.stream().collect(Collectors.toMap(PCount::getData, Function.identity()));
		}
		int year=Integer.valueOf(data.split("-")[0]);
		int mon=Integer.valueOf(data.split("-")[1]);
		int lasData=Integer.valueOf(getLastDayOfMonth(year,mon,"dd"));
		int[] xAxisData=new int[lasData];
		int[] seriesData=new int[lasData];
		xAxisData[0]=1;
		seriesData[0]=countFirDay;
		for (int i=1;i<lasData;i++){
			xAxisData[i]=i+1;
			if (countMap.get((i+1)+"")!=null){
				seriesData[i]=seriesData[i-1]+countMap.get((i+1)+"").getCoun();
			}else{
				seriesData[i]=seriesData[i-1];
			}

		}
		result.setxAxisData(xAxisData);
		Series series =new Series();
		series.setName("在押情况");
		series.setType("line");
		series.setStack("总量");
		series.setData(seriesData);
		List<Series> list=new ArrayList<>();
		list.add(series);
		result.setSeries(list);
		return result;
	}

	@Override
	public EchartsData getOut(String area) {
		//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
		String type=" and type in (0) ";
		return getPub(area,type,"外出就医");
	}

	@Override
	public EchartsData getDesignate(String area) {
		//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
		String type=" and type in (1) ";
		return getPub(area,type,"指认现场");
	}

	public EchartsData getPub(String area,String type,String pname) {
		//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
		String data = getLastMonth("yyyy-MM");
		String time=data+"%";
		EchartsData result=new EchartsData();
		String name=data.replace("-","年");
		if ("16".equals(area)){
			area="";
			result.setTitleName(name+"月全省监所"+pname);
		}else{
			String id="0";
			List<Integer> ids = areaDao.getAreaIdsByPar(Integer.valueOf(area));
			id=ids.stream().map(e->e+"").collect(Collectors.joining(","));
			area=" and area_id in("+id+") ";
			result.setTitleName(name+"月全市监所"+pname);

		}
		int year=Integer.valueOf(data.split("-")[0]);
		int mon=Integer.valueOf(data.split("-")[1]);
		int firData=Integer.valueOf(getFirstDayOfMonth(year,mon,"dd"));
		int lasData=Integer.valueOf(getLastDayOfMonth(year,mon,"dd"))+1;
		int[] xAxisData=new int[lasData-1];
		int[] seriesData=new int[lasData-1];

		List<ViewCount> countList = viewDao.getDateByData(time, type, area);
		Map<String, ViewCount> collect = countList.stream().collect(Collectors.toMap(ViewCount::getData, Function.identity()));
		for (int i=firData;i<lasData;i++){
			if (collect.get(i>9?data+"-"+i:data+"-0"+i)!=null){
				xAxisData[i-1]=i;
				seriesData[i-1]=collect.get(i>9?data+"-"+i:data+"-0"+i).getCount();
			}else{
				xAxisData[i-1]=i;
				seriesData[i-1]=0;
			}
		}
		result.setxAxisData(xAxisData);
		Series series =new Series();
		series.setName(pname);
		series.setType("line");
		series.setStack("总量");
		series.setData(seriesData);
		List<Series> list=new ArrayList<>();
		list.add(series);
		result.setSeries(list);
		return result;
	}
	@Override
	public EchartsData getOthers(String area) {

		String type=" and type in (2,3,4) ";
		String data = getLastMonth("yyyy-MM");
		String time=data+"%";
		if ("16".equals(area)){
			area="";
		}else{
			String id="0";
			List<Integer> ids = areaDao.getAreaIdsByPar(Integer.valueOf(area));
			id=ids.stream().map(e->e+"").collect(Collectors.joining(","));
			area=" and area_id in("+id+") ";
		}
		EchartsData result=new EchartsData();
		int year=Integer.valueOf(data.split("-")[0]);
		int mon=Integer.valueOf(data.split("-")[1]);
		int firData=Integer.valueOf(getFirstDayOfMonth(year,mon,"dd"));
		int lasData=Integer.valueOf(getLastDayOfMonth(year,mon,"dd"))+1;
		int[] xAxisData=new int[lasData-1];
		int[] seriesData2=new int[lasData-1];
		int[] seriesData3=new int[lasData-1];
		int[] seriesData4=new int[lasData-1];
		List<ViewCount> countList = viewDao.getDateByData(time, type, area);
		Map<String, ViewCount> collect = countList.stream().collect(Collectors.toMap(e->e.getData()+"#"+e.getType(), Function.identity()));
		for (int i=firData;i<lasData;i++){
			String key=i>9?data+"-"+i:data+"-0"+i;
			if (collect.get(key+"#2")!=null){
				xAxisData[i-1]=i;
				seriesData2[i-1]=collect.get(key+"#2").getCount();
			}else {
				xAxisData[i-1]=i;
				seriesData2[i-1]=0;
			}

			if (collect.get(key+"#3")!=null){
				xAxisData[i-1]=i;
				seriesData3[i-1]=collect.get(key+"#3").getCount();
			}else {
				xAxisData[i-1]=i;
				seriesData3[i-1]=0;
			}
			if (collect.get(key+"#4")!=null){
				xAxisData[i-1]=i;
				seriesData4[i-1]=collect.get(key+"#4").getCount();
			}else{
				xAxisData[i-1]=i;
				seriesData4[i-1]=0;
			}
		}
		result.setxAxisData(xAxisData);
		//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
		Series series2 =new Series();
		series2.setName("出庭作证");
		series2.setType("line");
		series2.setStack("总量");
		series2.setData(seriesData2);

		Series series3 =new Series();
		series3.setName("投牢");
		series3.setType("line");
		series3.setStack("总量");
		series3.setData(seriesData3);

		Series series4 =new Series();
		series4.setName("审讯");
		series4.setType("line");
		series4.setStack("总量");
		series4.setData(seriesData4);
		List<Series> list=new ArrayList<>();
		list.add(series2);
		list.add(series3);
		list.add(series4);

		result.setLegendData(new String[]{"出庭作证","投牢","审讯"});
		result.setSeries(list);
		return result;
	}
	@Override
	public EchartsPieData getPie(String area){
		EchartsPieData result=new EchartsPieData();
		String type=" and type in (0,1,2,3,4) ";
		String data = getLastMonth("yyyy-MM");
		String time=data;
		String timename=time.replace("-","年");
		if ("16".equals(area)){
			area="";
			//result.setTitleName(timename+"月全省统计");
			result.setTitleName("全局统计");
		}else{
			result.setTitleName(timename+"月全市统计");
			String id="0";
			List<Integer> ids = areaDao.getAreaIdsByPar(Integer.valueOf(area));
			id=ids.stream().map(e->e+"").collect(Collectors.joining(","));
			area=" and area_id in("+id+") ";
		}

		//0：外出就医，1：指认现场，2：出庭作证，3: 投牢，4：审讯，5：其他
		Pie p0=new Pie(0,"外出就医");
		Pie p1=new Pie(0,"指认现场");
		Pie p2=new Pie(0,"出庭作证");
		Pie p3=new Pie(0,"投牢");
		Pie p4=new Pie(0,"审讯");


		List<ViewCount> countList = viewDao.getPreByData(time, type, area);
		for (int i = 0; i <countList.size() ; i++) {
			if (countList.get(i).getType()==0){
				p0.setValue(countList.get(i).getCount());
			}else if (countList.get(i).getType()==1){
				p1.setValue(countList.get(i).getCount());
			}else if (countList.get(i).getType()==2){
				p2.setValue(countList.get(i).getCount());
			}else if (countList.get(i).getType()==3){
				p3.setValue(countList.get(i).getCount());
			}else if (countList.get(i).getType()==4){
				p4.setValue(countList.get(i).getCount());
			}
		}

		List<Pie> list=new ArrayList<>();
		list.add(p0);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);

		result.setSeries(list);
		return result;
	}

	public List<EchartsMapData> getMap(String area){
		List<EchartsMapData> res=new ArrayList<>();
		//获取地级市
		List<OutboundArea> areaList=areaDao.getAllAreaDesc(Integer.valueOf(area));
		//获取各地级市总人数
		List<CountByCity> countByCity = viewDao.getCountByCity();
		Map<Integer, CountByCity> countByCityMap=new HashMap<>();
		if (null!=countByCity) {
			countByCityMap = countByCity.stream().collect(Collectors.toMap(CountByCity::getAreaId, Function.identity()));
		}
		//获取各地级市状态
		List<CountTypeByCity> typeByCityList = viewDao.getTypeByCity(getNowData("yyyy-MM-dd"));
		Map<Integer, CountTypeByCity> typeByCityMap = new HashMap<>();

		if (null!=typeByCityList && !typeByCityList.isEmpty() && null!=typeByCityList.get(0)) {
			 typeByCityMap = typeByCityList.stream().collect(Collectors.toMap(CountTypeByCity::getAreaId, Function.identity()));
		}
		int areaId=0;
		EchartsMapData emd=null;
		Map<String,Integer> typeMap=null;
		for (int i = 0; i < areaList.size(); i++) {
			emd=new EchartsMapData();
			emd.setName(areaList.get(i).getName());
			typeMap=new HashMap<>();
			typeMap.put("在押人员总数",0);
			typeMap.put("外出就医",0);
			typeMap.put("指认现场",0);
			typeMap.put("出庭作证",0);
			typeMap.put("投牢",0);
			typeMap.put("审讯",0);
			areaId=areaList.get(i).getId();
			if(null!=countByCityMap.get(areaId)){
				typeMap.put("在押人员总数",countByCityMap.get(areaId).getCount());

			}
			if(null!=typeByCityMap.get(areaId)){
				CountTypeByCity ctb=typeByCityMap.get(areaId);
				//3:1&4:2
				String[] type=ctb.getType().split("&");
				for (int j = 0; j < type.length; j++) {
					//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
					if ("0".equals(type[j].split(":")[0])){
						typeMap.put("外出就医",Integer.valueOf(type[j].split(":")[1]));
					} else if ("1".equals(type[j].split(":")[0])){
						typeMap.put("指认现场",Integer.valueOf(type[j].split(":")[1]));
					}else if ("2".equals(type[j].split(":")[0])){
						typeMap.put("出庭作证",Integer.valueOf(type[j].split(":")[1]));
					}else if ("3".equals(type[j].split(":")[0])){
						typeMap.put("投牢",Integer.valueOf(type[j].split(":")[1]));
					}else if ("4".equals(type[j].split(":")[0])){
						typeMap.put("审讯",Integer.valueOf(type[j].split(":")[1]));
					}
				}
			}
			typeMap.put("监所内人员",typeMap.get("在押人员总数")-typeMap.get("外出就医")-typeMap.get("指认现场")-typeMap.get("出庭作证")-typeMap.get("投牢")-typeMap.get("审讯"));
			emd.setValue(typeMap);
			res.add(emd);
		}
		return  res;
	}
	/*****************************地市视图**********************************************/

	@Override
	public EchartsPieData getSecPie(String area) {
		return getPie(area);
	}

	@Override
	public EchartsData getSecCustody(String area) {
		return  getCustody(area);
	}

	@Override
	public EchartsData getSecOut(String area) {
		return getOut(area);
	}

	@Override
	public EchartsData getSecDesignate(String area) {
		return getDesignate(area);
	}

	@Override
	public EchartsData getSecOthers(String area) {
		return getOthers(area);
	}

	public List<EchartsMapData> getSecMap(String area){
		List<EchartsMapData> res=new ArrayList<>();
		//获取监所
		List<OutboundArea> areaList=areaDao.getAllAreaDesc(Integer.valueOf(area));
		//获取各监所市总人数
		List<CountByCity> countByCity = viewDao.getSecCountByCity(Integer.valueOf(area));
		Map<Integer, CountByCity> countByCityMap=new HashMap<>();
		if (null!=countByCity) {
			countByCityMap = countByCity.stream().collect(Collectors.toMap(CountByCity::getAreaId, Function.identity()));
		}
		//获取各监所状态
		List<CountTypeByCity> typeByCityList = viewDao.getSecTypeByCity(getNowData("yyyy-MM-dd"),Integer.valueOf(area));
		Map<Integer, CountTypeByCity> typeByCityMap = new HashMap<>();

		if (null!=typeByCityList && !typeByCityList.isEmpty() && null!=typeByCityList.get(0)) {
			typeByCityMap = typeByCityList.stream().collect(Collectors.toMap(CountTypeByCity::getAreaId, Function.identity()));
		}
		int areaId=0;
		EchartsMapData emd=null;
		Map<String,Integer> typeMap=null;
		for (int i = 0; i < areaList.size(); i++) {
			emd=new EchartsMapData();
			emd.setName(areaList.get(i).getName());
			typeMap=new HashMap<>();
			typeMap.put("在押人员总数",0);
			typeMap.put("外出就医",0);
			typeMap.put("指认现场",0);
			typeMap.put("出庭作证",0);
			typeMap.put("投牢",0);
			typeMap.put("审讯",0);
			areaId=areaList.get(i).getId();
			if(null!=countByCityMap.get(areaId)){
				typeMap.put("在押人员总数",countByCityMap.get(areaId).getCount());

			}
			if(null!=typeByCityMap.get(areaId)){
				CountTypeByCity ctb=typeByCityMap.get(areaId);
				//3:1&4:2
				String[] type=ctb.getType().split("&");
				for (int j = 0; j < type.length; j++) {
					//0：外出就医，1：指认现场，2：出庭作证，3投牢，4：审讯，5：其他
					if ("0".equals(type[j].split(":")[0])){
						typeMap.put("外出就医",Integer.valueOf(type[j].split(":")[1]));
					} else if ("1".equals(type[j].split(":")[0])){
						typeMap.put("指认现场",Integer.valueOf(type[j].split(":")[1]));
					}else if ("2".equals(type[j].split(":")[0])){
						typeMap.put("出庭作证",Integer.valueOf(type[j].split(":")[1]));
					}else if ("3".equals(type[j].split(":")[0])){
						typeMap.put("投牢",Integer.valueOf(type[j].split(":")[1]));
					}else if ("4".equals(type[j].split(":")[0])){
						typeMap.put("审讯",Integer.valueOf(type[j].split(":")[1]));
					}
				}
			}
			typeMap.put("监所内人员",typeMap.get("在押人员总数")-typeMap.get("外出就医")-typeMap.get("指认现场")-typeMap.get("出庭作证")-typeMap.get("投牢")-typeMap.get("审讯"));
			emd.setValue(typeMap);
			res.add(emd);
		}
		return  res;
	}

}
