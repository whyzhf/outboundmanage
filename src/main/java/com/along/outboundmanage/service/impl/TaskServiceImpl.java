package com.along.outboundmanage.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.along.outboundmanage.dao.CarDao;
import com.along.outboundmanage.dao.PoliceDao;
import com.along.outboundmanage.dao.PrisonerDao;
import com.along.outboundmanage.dao.TaskDao;
import com.along.outboundmanage.model.*;
import com.along.outboundmanage.service.TaskService;
import com.along.outboundmanage.utill.GeneralUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.along.outboundmanage.utill.DataUtil.strToSqlDate;



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

    @Override
    public List<OutboundTask> getAllTask(int areaId) {
        return taskDao.getAllTaskByAreaId(areaId);
    }

    @Override
    public List<OutboundTask> getTaskByStatus(String status, String areaId) {
        return taskDao.getAllTaskByStatus(status,areaId);

    }
    @Override
    public Integer getCountTask(String status, String areaId) {
        return taskDao.getCountTaskByStatus(status,areaId);

    }
    @Override
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
                        th.setUserId(allPolice.get(i).getUserId());
                        th.setTaskId(Integer.parseInt(idarr[i]));
                        th.setAreaId(allPolice.get(i).getAreaId());
                        th.setFirjson(JSONObject.toJSONString(firmap.get(Integer.parseInt(idarr[i]))));
                        th.setSecjson(JSONObject.toJSONString(map));
                        res.add(th);

                        policIds = policIds + allPolice.get(i).getId() + ",";
                        equipIds = equipIds + allPolice.get(i).getEquipmentCard() + "," + allPolice.get(i).getEquipmentCard2() + ",";

                    }
                }
                List<OutboundPrisoner> allPrisoner= (List<OutboundPrisoner>) map.get("prisoner");
                if(allPrisoner != null && !allPrisoner.isEmpty()) {
                    for (int j = 0; j < allPrisoner.size(); j++) {
                        prisonerIds = prisonerIds + allPrisoner.get(i).getId() + ",";
                        equipIds = equipIds + allPrisoner.get(i).getEquipmentCard() + ",";
                    }
                }
                List<OutboundCar> allCar= (List<OutboundCar>) map.get("car");
                if(allCar!=null && !allCar.isEmpty()) {
                    for (int j = 0; j < allCar.size(); j++) {
                        carIds = carIds + allCar.get(i).getId() + ",";
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
                if (equipIds.length()>1){
                    equipIds=equipIds.substring(0,equipIds.length()-1);
                    taskDao.clearEquipStatus(equipIds);
                }
                //车辆状态
                carIds=carIds.replaceAll("null,","");
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
    public List<OutboundTask>  getMyCurrTaskByStatus(String status,String areaId,int userId){
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
        OutboundTaskDesc taskDesc = taskDao.getTaskDesc(id);
        resMap.put("task",taskDesc);
        //获取警察信息
        String tP=taskDesc.getPoliceId();
        if(tP!=null&& !tP.isEmpty()){
            List<OutboundPoliceForSel> allPoliceById = policeDao.getAllPoliceById(tP);
            taskDesc.setPoliceId( allPoliceById.stream().map(e->e.getName()+"("+e.getCard()+")").collect(Collectors.joining(",")));
            resMap.put("Police",allPoliceById);
        }else{
            resMap.put("Police",null);
        }
        //获取犯人信息
        String tPr=taskDesc.getPrisonerId();
        if(tPr!=null&& !tPr.isEmpty()){
            List<OutboundPrisoner> allPrisonerById = prisonerDao.getAllPrisonerById(tPr);
            taskDesc.setPrisonerId( allPrisonerById.stream().map(e->e.getName()+"("+e.getCard()+")").collect(Collectors.joining(",")));
            resMap.put("prisoner", allPrisonerById);
        }else{
            resMap.put("prisoner",null);
        }
        //获取车辆信息
        String tC=taskDesc.getCarId();
        if(tC!=null&& !tC.isEmpty()){
            List<OutboundCar> allCarByIds = carDao.getAllCarByIds(tC);
            taskDesc.setCarId( allCarByIds.stream().map(e->e.getCard()).collect(Collectors.joining(",")));
            resMap.put("car",allCarByIds);
        }else{
            resMap.put("car",null);
        }
        return resMap;
    }
}
