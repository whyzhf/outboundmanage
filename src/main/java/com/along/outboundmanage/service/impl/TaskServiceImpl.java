package com.along.outboundmanage.service.impl;



import com.along.outboundmanage.dao.*;
import com.along.outboundmanage.model.*;
import com.along.outboundmanage.service.TaskService;
import com.along.outboundmanage.utill.GeneralUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;
import static com.along.outboundmanage.utill.GeneralUtils.getJsonStr;


@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskDao taskDao;
    @Resource
    private CarDao carDao;
    @Resource
    private PoliceDao policeDao;
    @Resource
    private PrisonerDao prisonerDao;
    @Resource
    private EquipRelManageDao equipRelManageDao;
    @Resource
    private EquipmentDao equipmentDao;

    @Override
    public List<OutboundTask> getAllTask(int areaId) {
        return taskDao.getAllTaskByAreaId(areaId);
    }

    @Override
    public List<OutboundTask> getHisTaskList(int areaId) {
        return taskDao.getTaskByAreaIdStatus(areaId,"4");
    }

    @Override
    public List<OutboundTask> getCurrTaskList(int areaId) {
        return taskDao.getTaskByAreaIdStatus(areaId,"0,1,2,3");
    }

    @Override
    public OutboundTaskJson getTaskByIds(String taskIds) {
        return taskDao.getTaskByIds(taskIds);
    }

    @Override
    public List<OutboundTaskJson> getTaskByStatus2(String status, String areaId) {
        return taskDao.getAllTaskByStatus2(status,areaId);

    }
    @Override
    public List<OutboundTask> getTaskByStatus(String status, String areaId) {
        return taskDao.getAllTaskByStatus(status,areaId);

    }
    @Override
    public Integer getCountTask(String status, String areaId) {
        return taskDao.getCountTaskByStatus(status,areaId);

    }
  /*  @Override
    public boolean updateTaskById(OutboundTaskDesc outboundTaskDesc) {
        OutboundTask outboundTask =new OutboundTask(outboundTaskDesc.getId(),outboundTaskDesc.getName(),outboundTaskDesc.getOrigin(),outboundTaskDesc.getDestination(),
                outboundTaskDesc.getStartTime()==null?null:strToSqlDate(  outboundTaskDesc.getStartTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getEndTime()==null?null:strToSqlDate(  outboundTaskDesc.getEndTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getDescribe(),
                outboundTaskDesc.getRouteId(),outboundTaskDesc.getType(),outboundTaskDesc.getAreaId());

        int taskId = outboundTaskDesc.getId();
        if(taskDao.updateTaskById(outboundTask)){
            //添加关系表 Task - polic
            String police=outboundTaskDesc.getPoliceId();
            if (!GeneralUtils.isEmpty(police)){
                String[] arr=police.split(",");
                List<KandV> tpIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tpIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.delTaskPolic(taskId+"");
                taskDao.addTaskPolic(tpIds);
            }
            //添加关系表 Task - Prisoner
            String prisonerId = outboundTaskDesc.getPrisonerId();
            if (!GeneralUtils.isEmpty(prisonerId)){
                String[] arr=prisonerId.split(",");
                List<KandV> tprIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tprIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.delTaskPrisoner(taskId+"");
                taskDao.addTaskPrisoner(tprIds);
            }
            //添加关系表 Task - Car
            String carId = outboundTaskDesc.getCarId();
            if (!GeneralUtils.isEmpty(carId)){
                String[] arr=carId.split(",");
                List<KandV> tcIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tcIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.delTaskCar(taskId+"");
                taskDao.addTaskCar(tcIds);
            }


            return true;
        }else{

            return false;
        }

    }
   */
  @Override
  public boolean updateTaskById(OutboundTaskV2 outboundTaskDesc) {
      OutboundTask outboundTask =new OutboundTask(outboundTaskDesc.getId(),outboundTaskDesc.getName(),outboundTaskDesc.getOrigin(),outboundTaskDesc.getDestination(),
              outboundTaskDesc.getStartTime()==null?null:strToSqlDate(  outboundTaskDesc.getStartTime(),"yyyy-MM-dd HH:mm:ss")   ,
              outboundTaskDesc.getEndTime()==null?null:strToSqlDate(  outboundTaskDesc.getEndTime(),"yyyy-MM-dd HH:mm:ss")   ,
              outboundTaskDesc.getDescribe(),
              outboundTaskDesc.getRouteId(),outboundTaskDesc.getType(),outboundTaskDesc.getAreaId());

      int taskId = outboundTaskDesc.getId();
      if(taskDao.updateTaskById(outboundTask)){
          //清空关系表
          taskDao.delTaskPolic(taskId+"");
          taskDao.delTaskPrisoner(taskId+"");
          taskDao.delTaskCar(taskId+"");
              //添加关系表 Task - polic
          Integer[] pids=outboundTaskDesc.getPoliceIds();
          List<KandV> tpIds=new ArrayList<>();
          for (int i = 0; i < pids.length; i++) {
              tpIds.add(new KandV(taskId,Integer.valueOf(pids[i])));
          }
          taskDao.addTaskPolic(tpIds);

          //添加关系表 Task - Prisoner
          Integer[] priids=outboundTaskDesc.getPrisonerIds();
          List<KandV> tprIds=new ArrayList<>();
          for (int i = 0; i < priids.length; i++) {
              tprIds.add(new KandV(taskId,Integer.valueOf(priids[i])));
          }

          taskDao.addTaskPrisoner(tprIds);

          //添加关系表 Task - Car
          Integer[] cids=outboundTaskDesc.getCarIds();
          List<KandV> tcIds=new ArrayList<>();
          for (int i = 0; i < cids.length; i++) {
              tcIds.add(new KandV(taskId,Integer.valueOf(cids[i])));
          }
          taskDao.addTaskCar(tcIds);
          outboundTaskDesc.setId(taskId);
          return true;
      }else{
          outboundTaskDesc=null;
          return false;
      }

  }
  /**
     * 任务结束：
     *      1.修改任务状态
     *      2.备份任务数据
     *      3.清空设备关系设置
     * */
    @Override
    public boolean endTask(String ids,String status,String time) {
        new Thread(()->{
            String policIds="",equipIds="",prisonerIds="",carIds="";
            // 2.备份任务数据
            List<TaskHistory> res=new ArrayList<>();
            //获取完成任务的任务信息
            List<OutboundTask>list =taskDao.getAllTaskByIds(ids);
            Map<Integer, OutboundTask> firmap = list.stream().collect(Collectors.toMap(OutboundTask::getId, Function.identity()));
            String[] idarr=ids.split(",");
            for (int i=0;i<idarr.length;i++){
                Map<String, Object> map=getTaskDesc(Integer.parseInt(idarr[i]));
                List<OutboundPoliceForSel> allPolice= (List<OutboundPoliceForSel>) map.get("Police");
                if(allPolice!=null && !allPolice.isEmpty()) {
                    for (int j = 0; j < allPolice.size(); j++) {
                        TaskHistory th = new TaskHistory();
                        th.setUserId(allPolice.get(j).getUserId());
                        th.setTaskId(Integer.parseInt(idarr[i]));
                        th.setAreaId(allPolice.get(j).getAreaId());
                        th.setFirjson(getJsonStr(firmap.get(Integer.parseInt(idarr[i]))));
                        th.setSecjson(getJsonStr(map));
                        res.add(th);
                        policIds = policIds + allPolice.get(j).getId() + ",";
                        equipIds = equipIds + allPolice.get(j).getEquipmentCard() + "," + allPolice.get(j).getEquipmentCard2() + ",";
                    }
                }
                List<OutboundPrisoner> allPrisoner= (List<OutboundPrisoner>) map.get("prisoner");
                if(allPrisoner != null && !allPrisoner.isEmpty()) {
                    for (int j = 0; j < allPrisoner.size(); j++) {
                        prisonerIds = prisonerIds + allPrisoner.get(j).getId() + ",";
                        equipIds = equipIds + allPrisoner.get(j).getEquipmentCard() + ",";
                    }
                }
                List<OutboundCar> allCar= (List<OutboundCar>) map.get("car");
                if(allCar!=null && !allCar.isEmpty()) {
                    for (int j = 0; j < allCar.size(); j++) {
                        carIds = carIds + allCar.get(j).getId() + ",";
                    }
                }
            }
           if (res!=null && !res.isEmpty()){
               taskDao.addMyTaskHistory(res);
           }

           // 3.清空设备关系设置
                //警察和设备
                policIds=policIds.replaceAll("null,","");
                if (policIds.length()>1){
                    policIds=policIds.substring(0,policIds.length()-1);
                    taskDao.clearPoliceEquip(policIds);
                }

                //犯人和设备
                prisonerIds=prisonerIds.replaceAll("null,","");
                if (prisonerIds.length()>1){
                    prisonerIds=prisonerIds.substring(0,prisonerIds.length()-1);
                    taskDao.clearPrisonerEquip(prisonerIds);
                }
                //设备状态
                equipIds=equipIds.replaceAll("null,","");
          //  System.out.println("equipIds:"+equipIds);
                if (equipIds.length()>1){
                    equipIds=equipIds.substring(0,equipIds.length()-1);
                    String join = String.join("','",  equipIds.split(","));
                    taskDao.clearEquipStatusByCard("'"+join+"'");
                }
                //车辆状态
                carIds=carIds.replaceAll("null,","");
           // System.out.println("equipIds:"+equipIds);
                if (carIds.length()>1){
                    carIds=carIds.substring(0,carIds.length()-1);
                    taskDao.clearCarStatus(carIds);
                }
        }).start();

        return taskDao.upTaskStatusById(ids,status,time);
    }
    @Override
    public boolean startTask(String ids,String status,String time) {
        return taskDao.upTaskStatusById(ids,status,time);
    }
    @Override
    public boolean updateTaskStatusById(OutboundTask outboundTask) {
        return taskDao.updateTaskById(outboundTask);
    }

	@Override
	public boolean checkTaskId(OutboundTask outboundTask) {
		return taskDao.checkTaskId(outboundTask);
	}
    @Override
    public boolean delTask(String ids) {
        if( taskDao.delTask(ids)){
            taskDao.delTaskPolic(ids);
            taskDao.delTaskPrisoner(ids);
            taskDao.delTaskCar(ids);
        }
        return taskDao.delTask(ids);
    }

    @Override
    public Map<String, Object> insertTask(OutboundTaskDesc outboundTaskDesc) {
        OutboundTask outboundTask =new OutboundTask(outboundTaskDesc.getId(),outboundTaskDesc.getName(),outboundTaskDesc.getOrigin(),outboundTaskDesc.getDestination(),
                outboundTaskDesc.getStartTime()==null?null:strToSqlDate(  outboundTaskDesc.getStartTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getEndTime()==null?null:strToSqlDate(  outboundTaskDesc.getEndTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getDescribe(),
                outboundTaskDesc.getRouteId(),outboundTaskDesc.getType(),outboundTaskDesc.getAreaId());
        int id= 0;
        id= taskDao.insertTask(outboundTask);
       int taskId = outboundTask.getId();
        outboundTaskDesc.setId(taskId);
        if(id>0){//新增成功
            //添加关系表 Task - polic
            String police=outboundTaskDesc.getPoliceId();
            if (!GeneralUtils.isEmpty(police)){
                String[] arr=police.split(",");
                List<KandV> tpIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tpIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.addTaskPolic(tpIds);
            }
            //添加关系表 Task - Prisoner
            String prisonerId = outboundTaskDesc.getPrisonerId();
            if (!GeneralUtils.isEmpty(prisonerId)){
                String[] arr=prisonerId.split(",");
                List<KandV> tprIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tprIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.addTaskPrisoner(tprIds);
            }
            //添加关系表 Task - Car
            String carId = outboundTaskDesc.getCarId();
            if (!GeneralUtils.isEmpty(carId)){
                String[] arr=carId.split(",");
                List<KandV> tcIds=new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    tcIds.add(new KandV(taskId,Integer.valueOf(arr[i])));
                }
                taskDao.addTaskCar(tcIds);
            }
            return getTaskDesc(taskId);
        }else{
            outboundTaskDesc=null;
            return null;
        }

    }

    @Override
    public OutboundTaskV2 addTask(OutboundTaskV2 outboundTaskDesc) {
        OutboundTask outboundTask =new OutboundTask(outboundTaskDesc.getId(),outboundTaskDesc.getName(),outboundTaskDesc.getOrigin(),outboundTaskDesc.getDestination(),
                outboundTaskDesc.getStartTime()==null?null:strToSqlDate(  outboundTaskDesc.getStartTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getEndTime()==null?null:strToSqlDate(  outboundTaskDesc.getEndTime(),"yyyy-MM-dd HH:mm:ss")   ,
                outboundTaskDesc.getDescribe(),
                outboundTaskDesc.getRouteId(),outboundTaskDesc.getType(),outboundTaskDesc.getAreaId());
        int id= 0;
        id= taskDao.insertTask(outboundTask);
        int taskId = outboundTask.getId();
        outboundTaskDesc.setId(taskId);
        if(id>0){//新增成功
            //清空关系表
            taskDao.delTaskPolic(taskId+"");
            taskDao.delTaskPrisoner(taskId+"");
            taskDao.delTaskCar(taskId+"");
            //添加关系表 Task - polic
            Integer[] pids=outboundTaskDesc.getPoliceIds();
            List<KandV> tpIds=new ArrayList<>();
            for (int i = 0; i < pids.length; i++) {
                tpIds.add(new KandV(taskId,Integer.valueOf(pids[i])));
            }
            taskDao.addTaskPolic(tpIds);

            //添加关系表 Task - Prisoner
            Integer[] priids=outboundTaskDesc.getPrisonerIds();
                List<KandV> tprIds=new ArrayList<>();
                for (int i = 0; i < priids.length; i++) {
                    tprIds.add(new KandV(taskId,Integer.valueOf(priids[i])));
                }
                taskDao.addTaskPrisoner(tprIds);

            //添加关系表 Task - Car
            Integer[] cids=outboundTaskDesc.getCarIds();
            List<KandV> tcIds=new ArrayList<>();
            for (int i = 0; i < cids.length; i++) {
                tcIds.add(new KandV(taskId,Integer.valueOf(cids[i])));
            }
                taskDao.addTaskCar(tcIds);
            outboundTaskDesc.setId(taskId);
            return outboundTaskDesc;
        }else{
            outboundTaskDesc=null;
            return null;
        }
    }

    //重新分配干警和设备
    /*@Override
    public Map<String, Object> addpoliceEquip(Map<String, Object> map) {
        Integer[] policeIds= (Integer[]) map.get("policeIds");
        Integer[] handsetIds= (Integer[]) map.get("handsetIds");
        Integer[] watchIds= (Integer[]) map.get("watchIds");
        Integer[] watchId= new Integer[policeIds.length];
        if (policeIds.length>=watchIds.length){
            System.arraycopy(watchIds, 0, watchId, 0, watchId.length);
        }else {
            System.arraycopy(watchIds, 0, watchId, 0, policeIds.length);
        }
      //  Integer[] handsetIds= (Integer[]) map.get("handsetIds");
        Integer[] handsetId= new Integer[policeIds.length];
        if (policeIds.length>=handsetId.length){
            System.arraycopy(handsetIds, 0, handsetId, 0, handsetIds.length);
        }else {
            System.arraycopy(handsetIds, 0, handsetId, 0, policeIds.length);
        }
        List<OutboundPolice> list=new ArrayList<>();
        String pids="",eids="";
        for (int i = 0; i < policeIds.length; i++) {
            list.add(new OutboundPolice.Builder().id(policeIds[i]).equipmentId(watchId[i]).equipmentId2(handsetId[i]).build());
            pids+=policeIds[i]+",";
            eids+=watchId[i]+","+handsetId[i]+",";
        }
        //修改原来设备为可用
        pids=pids.substring(0,pids.length()-1);
            equipRelManageDao.updataEquip(pids);
        //添加干警设备关系
            equipRelManageDao.updataPoliceEquip(list);
        //修改设备状态
        eids=eids.substring(0,eids.length()-1);
        equipmentDao.upEquipmentTypeAndStatus(0,0,eids);
        //返回拼接字符串
        Map<String, Object> res=new HashMap<>();
        res.put("data",equipRelManageDao.getreturn(pids));
        return res;
    }
*/

    //重新分配干警和设备
    @Override
    public Map<String, Object> addpoliceEquip(Map<String, Object> map){
        List<OutboundPoliceForSel> policeList2=   (List<OutboundPoliceForSel>) map.get("policeIds");
        List<OutboundPoliceForSel> policeList=  new ArrayList<>();
        for (int i = 0; i < policeList2.size(); i++) {
            JSONObject jsonObject=JSONObject.fromObject(policeList2.get(i)); // 将数据转成json字符串
            //  OutboundPolice per = (OutboundPolice)JSONObject.toBean(jsonObject, OutboundPolice.class); //将json转成需要的对象
            policeList.add((OutboundPoliceForSel)JSONObject.toBean(jsonObject, OutboundPoliceForSel.class));
        }
        List<Integer>wList=(List<Integer>)map.get("watchIds");
        wList=wList.stream().distinct().collect(Collectors.toList());
        List<Integer>hList=(List<Integer>)map.get("handsetIds");
        hList=hList.stream().distinct().collect(Collectors.toList());
        String policeId="0",eids="0";
       /*  //如果传回的设备id已存在就删除
        for (int i = 0; i < policeList.size(); i++) {
            hList.remove( policeList.get(i).getEquipmentId());
            wList.remove( policeList.get(i).getEquipmentId2());
        }*/

        for (int i = 0; i < policeList.size(); i++) {//重新匹配设备
            if(wList!=null  && !wList.isEmpty() ){
             //   System.out.println(policeList.get(i).getEquipmentId2()+"##"+wList.contains(policeList.get(i).getEquipmentId2()));
                if( wList.contains(policeList.get(i).getEquipmentId2())){//如果已装配选中的设备，则该设备不再分配
                    wList.remove(policeList.get(i).getEquipmentId2());
                }else{//如果已装配的设备不再选中设备中，清空重新分配
                    policeList.get(i).setEquipmentId2(null);
                    //  wList.remove(0);
                }

            }else{
                policeList.get(i).setEquipmentId2(null);
            }

            if(hList!=null  && !hList.isEmpty() ){
                if( hList.contains(policeList.get(i).getEquipmentId())){//如果已装配选中的设备，则该设备不再分配
                    hList.remove(policeList.get(i).getEquipmentId());
                }else{//如果已装配的设备，不再选中设备中，清空重新分配
                    policeList.get(i).setEquipmentId(null);
                    //  wList.remove(0);
                }
            }else{
                policeList.get(i).setEquipmentId(null);
            }
            /*if(hList!=null && !hList.isEmpty() ){
                policeList.get(i).setEquipmentId(hList.get(0));
                hList.remove(0);
            }else{
                policeList.get(i).setEquipmentId(null);
            }
            if(wList!=null && !wList.isEmpty() ){
                policeList.get(i).setEquipmentId2(wList.get(0));
                wList.remove(0);
            }else{
                policeList.get(i).setEquipmentId2(null);
            }
            policeId=policeId+","+policeList.get(i).getId();
            eids=eids+","+policeList.get(i).getEquipmentId()+","+policeList.get(i).getEquipmentId2();
            */
        }
        for (int i = 0; i < policeList.size(); i++) {//重新匹配设备
            if(hList!=null  && !hList.isEmpty() ){
                if(policeList.get(i).getEquipmentId()!=null){//如果该已装配选中的设备，则该设备不再分配
                }else{//如果该已装配的设备，不再选中设备中，清空重新分配
                    policeList.get(i).setEquipmentId(hList.get(0));
                    hList.remove(0);
                }

            }else{
                //prisonerList.get(i).setEquipmentId(null);
            }

            if(wList!=null  && !wList.isEmpty() ){
                if(policeList.get(i).getEquipmentId2()!=null){//如果该已装配选中的设备，则该设备不再分配
                }else{//如果该已装配的设备，不再选中设备中，清空重新分配
                    policeList.get(i).setEquipmentId2(wList.get(0));
                    wList.remove(0);
                }

            }else{
                //prisonerList.get(i).setEquipmentId(null);
            }

            policeId=policeId+","+policeList.get(i).getId();
            eids=eids+","+policeList.get(i).getEquipmentId()+","+policeList.get(i).getEquipmentId2();
        }

       /* policeId=policeId.substring(0,policeId.length()-1);
        eids=eids.substring(0,eids.length()-1);*/
        //修改原来设备为可用
        equipRelManageDao.updataEquip(policeId);
        //添加干警设备关系
        equipRelManageDao.updataPoliceEquip(policeList);
        //修改设备状态
       // eids=eids.substring(0,eids.length()-1);
        equipmentDao.upEquipmentTypeAndStatus(0,0,eids);
      //返回拼接字符串addPoliceEquip
        Map<String, Object> res=new HashMap<>();
        res.put("data",equipRelManageDao.getreturn(policeId));
        return res;
    }

   /*@Override
    public Map<String, Object> addPrisonerEquip(Map<String, Integer[]> map) {
       Integer[] prisonerIds = (Integer[]) map.get("prisonerIds");
       Integer[] grapplersIds = (Integer[]) map.get("grapplersIds");
       Integer[] grapplersId = new Integer[prisonerIds.length];
       if (prisonerIds.length >= grapplersIds.length) {
           System.arraycopy(grapplersIds, 0, grapplersId, 0, grapplersIds.length);
       } else {
           System.arraycopy(grapplersIds, 0, grapplersId, 0, prisonerIds.length);
       }

       List<OutboundPrisoner> list = new ArrayList<>();
       String pids = "",eids="";
       for (int i = 0; i < prisonerIds.length; i++) {
           list.add(new OutboundPrisoner.Builder().id(prisonerIds[i]).equipmentId(grapplersIds[i]).build());
           pids += prisonerIds[i] + ",";
           eids+=grapplersId[i]+",";
       }
       //修改原来设备为可用
       pids = pids.substring(0, pids.length() - 1);
       equipRelManageDao.updatapriEquip(pids);
       //添加犯人设备关系
       equipRelManageDao.updataPrisonerEquip(list);
       //修改设备状态
       eids=eids.substring(0,eids.length()-1);
       equipmentDao.upEquipmentTypeAndStatus(0,0,eids);
       //返回拼接字符串
       Map<String, Object> res = new HashMap<>();
       res.put("data", equipRelManageDao.getPrireturn(pids));
       return res;
   }
*/
   @Override
   public Map<String, Object> addPrisonerEquip(Map<String, Object> map){
       List<OutboundPrisoner> prisonerList2=   (List<OutboundPrisoner>) map.get("prisonerIds");
       List<OutboundPrisoner> prisonerList=  new ArrayList<>();
       for (int i = 0; i < prisonerList2.size(); i++) {
           JSONObject jsonObject=JSONObject.fromObject(prisonerList2.get(i)); // 将数据转成json字符串
           //  OutboundPolice per = (OutboundPolice)JSONObject.toBean(jsonObject, OutboundPolice.class); //将json转成需要的对象
           prisonerList.add((OutboundPrisoner)JSONObject.toBean(jsonObject, OutboundPrisoner.class));
       }
       List<Integer>wList=(List<Integer>)map.get("grapplersIds");
       wList=wList.stream().distinct().collect(Collectors.toList());
       String policeId="0",eids="0";
       for (int i = 0; i < prisonerList.size(); i++) {//重新匹配设备
               if(wList!=null  && !wList.isEmpty() ){
                   if( wList.contains(prisonerList.get(i).getEquipmentId())){//如果该犯人已装配选中的设备，则该设备不再分配
                       wList.remove(prisonerList.get(i).getEquipmentId());
                   }else{//如果该犯人已装配的设备，不再选中设备中，清空重新分配
                       prisonerList.get(i).setEquipmentId(null);
                       //  wList.remove(0);
                   }

               }else{
                   prisonerList.get(i).setEquipmentId(null);
               }

       }
       for (int i = 0; i < prisonerList.size(); i++) {//重新匹配设备
           if(wList!=null  && !wList.isEmpty() ){
               if(prisonerList.get(i).getEquipmentId()!=null){//如果该犯人已装配选中的设备，则该设备不再分配

               }else{//如果该犯人已装配的设备，不再选中设备中，清空重新分配
                   prisonerList.get(i).setEquipmentId(wList.get(0));
                   wList.remove(0);
               }

           }else{
               //prisonerList.get(i).setEquipmentId(null);
           }


           policeId=policeId+","+prisonerList.get(i).getId();
           eids=eids+","+prisonerList.get(i).getEquipmentId();
       }


       /* policeId=policeId.substring(0,policeId.length()-1);
        eids=eids.substring(0,eids.length()-1);*/
       //修改原来设备为可用
       equipRelManageDao.updataEquip(policeId);
       //添加干警设备关系
       equipRelManageDao.updataPrisonerEquip(prisonerList);
       //修改设备状态
       // eids=eids.substring(0,eids.length()-1);
       equipmentDao.upEquipmentTypeAndStatus(0,0,eids);
       //返回拼接字符串
       Map<String, Object> res=new HashMap<>();
       res.put("data",equipRelManageDao.getPrireturn(policeId));
       return res;
   }


    @Override
    public List<OutboundTaskJson>  getMyCurrTaskByStatus(String status,String areaId,int userId){
        return taskDao.getMyCurrTaskByStatus(status, areaId, userId);
    }
    @Override
    public List<String> getAllMyTaskHistoryFir(int userId, String areaId) {
        return taskDao.getAllMyTaskHistoryFir(userId,areaId);
    }

    @Override
    public String getMyTaskHistorySec(int userId,int taskId, int areaId) {
        return taskDao.getMyTaskHistorySec(userId,taskId,areaId);
    }
    //任务详情
    @Override
    public Map<String, Object> getTaskDesc(int id) {
        Map<String, Object> resMap=new HashMap<>();
        //获取任务信息
        OutboundTaskV2 taskDesc = taskDao.getTaskDesc(id);
        resMap.put("task",taskDesc);
        //获取警察信息
        String tP=taskDesc.getPolice();
        if(tP!=null&& !tP.isEmpty()){
            List<OutboundPoliceForSel> allPoliceById = policeDao.getAllPoliceById(tP);

            taskDesc.setPolice( equipRelManageDao.getreturn(tP));
            String[] arr=tP.split(",");
            Integer[] arrd=new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                arrd[i]=Integer.valueOf(arr[i]);
            }
            resMap.put("Police",allPoliceById);
            taskDesc.setPoliceIds(arrd);
            Integer[] harr=new Integer[arrd.length];
            Integer[] warr=new Integer[arrd.length];
            for (int i = 0; i < allPoliceById.size(); i++) {
                harr[i]=allPoliceById.get(i).getEquipmentId();
                warr[i]=allPoliceById.get(i).getEquipmentId2();
                allPoliceById.get(i).setUserId(allPoliceById.get(i).getUserId());
            }
            taskDesc.setHandsetIds(harr);
            taskDesc.setWatchIds(warr);

        }
        //获取犯人信息
        String tPr=taskDesc.getPrisoner();
        if(tPr!=null&& !tPr.isEmpty()){
            List<OutboundPrisoner> allPrisonerById = prisonerDao.getAllPrisonerById(tPr);
            taskDesc.setPrisoner( equipRelManageDao.getPrireturn(tPr));
            String[] arr=tPr.split(",");
            Integer[] arrd=new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                arrd[i]=Integer.valueOf(arr[i]);
            }
            resMap.put("prisoner",allPrisonerById);
            taskDesc.setPrisonerIds(arrd);

            Integer[] garr=new Integer[arrd.length];
            for (int i = 0; i < allPrisonerById.size(); i++) {
                garr[i]=allPrisonerById.get(i).getEquipmentId();

            }
            taskDesc.setGrapplersIds(garr);
        }
        //获取车辆信息
        String tC=taskDesc.getCar();
        if(tC!=null&& !tC.isEmpty()){
            List<OutboundCar> allCarByIds = carDao.getAllCarByIds(tC);
            taskDesc.setCar( allCarByIds.stream().map(e->e.getCard()).collect(Collectors.joining(",")));
            String[] arr=tC.split(",");
            Integer[] arrd=new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                arrd[i]=Integer.valueOf(arr[i]);
            }
            taskDesc.setCarIds(arrd);
            resMap.put("car",allCarByIds);
        }
        return resMap;
    }
}
