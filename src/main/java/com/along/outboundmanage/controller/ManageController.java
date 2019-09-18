package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.*;
import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/manage")
public class ManageController {
	@Resource
	private CarService carService;
	@Resource
	private AreaService areaService;
	@Resource
	private PoliceService policeService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private EquipmentService equipmentService;
	@ResponseBody
	@RequestMapping("/getAreaTree")
	public Result getAreaTree(){
		return ResultGenerator.genSuccessResult(areaService.getAllArea());
	}
	/*****************************警察管理**********************************************/
	/*@ResponseBody
	@RequestMapping("/getPoliceList")
	public Result getPoliceList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Integer pageSize, int areaId){
		PageHelper.startPage(pageNum,pageSize);
		List<OutboundPoliceForSel> allPolice = policeService.getAllPolice(areaId);
		PageInfo<OutboundPoliceForSel> pageInfo = new PageInfo<>(allPolice);
		return ResultGenerator.genSuccessResult(pageInfo);
	}*/

	@ResponseBody
	@RequestMapping("/getPoliceList")
	public Result getPoliceList(@RequestBody Map<String, Integer> pubParam){
		//pubParam.forEach((K,V)-> System.out.println(K+" : "+V));

		PageHelper.startPage(pubParam.get("pageNum"),pubParam.get("pageSize"));
		List<OutboundPoliceForSel> allPolice = policeService.getAllPolice(pubParam.get("areaId"));
		PageInfo<OutboundPoliceForSel> pageInfo = new PageInfo<>(allPolice);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	@ResponseBody
	@RequestMapping("/getPolice")
	public Result getPolice(@RequestBody Map<String, Integer> pubParam){
		//pubParam.forEach((K,V)-> System.out.println(K+" : "+V));


		List<OutboundPoliceForSel> allPolice = policeService.getAllPolice(pubParam.get("areaId"));

		return ResultGenerator.genSuccessResult(allPolice);
	}

	@ResponseBody
	@RequestMapping("/addPolice")
	public Result addPolice(@RequestBody OutboundPolice outboundPolice){
		System.out.println(outboundPolice.getEquipmentId2());
		if ( null == outboundPolice.getEquipmentId()||outboundPolice.getEquipmentId() == 0 ) {
			outboundPolice.setEquipmentId(null);
		}
		if ( null == outboundPolice.getEquipmentId2()||outboundPolice.getEquipmentId2() == 0 ) {
			outboundPolice.setEquipmentId2(null);
		}
		OutboundPoliceForSel returnPolice=policeService.addPolice(outboundPolice);
		if(returnPolice==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnPolice);
		}
	}

	@ResponseBody
	@RequestMapping("/upPolice")
	public Result upPolice(@RequestBody OutboundPolice outboundPolice){
		if (outboundPolice.getEquipmentId() == 0) {
			outboundPolice.setEquipmentId(null);
		}
		if (outboundPolice.getEquipmentId2() == 0) {
			outboundPolice.setEquipmentId2(null);
		}
		boolean flag=policeService.upPolice(outboundPolice);
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}else{
			return ResultGenerator.setCustomResult(200,"修改成功");
		}
	}

	@ResponseBody
	@RequestMapping("/delPolice")
	public Result delPolice(@RequestBody PubParam pubParam){
		boolean flag=policeService.delPolice(pubParam.getIds());
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}else{
			return ResultGenerator.setCustomResult(200,"删除成功");
		}
	}
	/*****************************设备管理**********************************************/
	@ResponseBody
	@RequestMapping("/getEquipmentList")
	public Result getEquipmentList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundEquipment> allList = equipmentService.getAllEquipment(pubParam.getAreaId());
		PageInfo<OutboundEquipment> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	@ResponseBody
	@RequestMapping("/addEquipment")
	public Result addEquipment(@RequestBody OutboundEquipment outboundEquipment){
		OutboundEquipment returnEquipment=equipmentService.addEquipment(outboundEquipment);
		if(returnEquipment==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnEquipment);
		}
	}

	@ResponseBody
	@RequestMapping("/upEquipment")
	public Result upEquipment(@RequestBody OutboundEquipment outboundEquipment){
		boolean flag=equipmentService.upEquipment(outboundEquipment);
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}else{
			return ResultGenerator.setCustomResult(200,"修改成功");
		}
	}

	@ResponseBody
	@RequestMapping("/delEquipment")
	public Result delEquipment(@RequestBody PubParam pubParam){
		boolean flag=equipmentService.delEquipment(pubParam.getIds());
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}else{
			return ResultGenerator.setCustomResult(200,"删除成功");
		}
	}
	//遥控器和手表
	@ResponseBody
	@RequestMapping("/getEquipmentForPolice")
	public Result getEquipmentForPolice(@RequestBody PubParam pubParam){
		List<OutboundEquipment> allEquipmentByForm = equipmentService.getAllEquipmentByForm(pubParam.getAreaId(), "1,2");
		if(allEquipmentByForm != null && !allEquipmentByForm.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}
	//遥控器
	@ResponseBody
	@RequestMapping("/getHandsetForPolice")
	public Result getHandsetForPolice(@RequestBody PubParam pubParam){
		List<OutboundEquipment> allEquipmentByForm = equipmentService.getAllEquipmentByForm(pubParam.getAreaId(), "1");
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm != null && !allEquipmentByForm.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}

	//手表
	@ResponseBody
	@RequestMapping("/getWatchForPolice")
	public Result getWatchForPolice(@RequestBody PubParam pubParam){
		List<OutboundEquipment> allEquipmentByForm = equipmentService.getAllEquipmentByForm(pubParam.getAreaId(), "2");
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm != null && !allEquipmentByForm.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}
	@ResponseBody
	@RequestMapping("/getEquipmentForPrisoner")
	public Result getEquipmentForPrisoner(@RequestBody PubParam pubParam){
		List<OutboundEquipment> allEquipmentByForm = equipmentService.getAllEquipmentByForm(pubParam.getAreaId(), "0");
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm != null && !allEquipmentByForm.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用脚扣");
		}
	}
	/*****************************犯人管理**********************************************/
	@ResponseBody
	@RequestMapping("/getPrisonerList")
	public Result getPrisonerList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundPrisoner> allList = prisonerService.getAllPrisoner(pubParam.getAreaId());

		PageInfo<OutboundPrisoner> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@ResponseBody
	@RequestMapping("/getPrisoner")
	public Result getPrisoner(@RequestBody PubParam pubParam){
		List<OutboundPrisoner> allList = prisonerService.getAllPrisoner(pubParam.getAreaId());
		if(allList != null && !allList.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allList);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用脚扣");
		}
	}
	@ResponseBody
	@RequestMapping("/addPrisoner")
	public Result addPrisoner(@RequestBody OutboundPrisoner outboundPrisoner){
		if (outboundPrisoner.getEquipmentId()==0){
			outboundPrisoner.setEquipmentId(null);
		}
		OutboundPrisoner returnPrisoner=prisonerService.addPrisoner(outboundPrisoner);
		if(returnPrisoner==null){

			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnPrisoner);
		}
	}

	@ResponseBody
	@RequestMapping("/upPrisoner")
	public Result upPrisoner(@RequestBody OutboundPrisoner outboundPrisoner){
		boolean flag=prisonerService.upPrisoner(outboundPrisoner);
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}else{
			return ResultGenerator.setCustomResult(200,"修改成功");
		}
	}

	@ResponseBody
	@RequestMapping("/delPrisoner")
	public Result delPrisoner(@RequestBody PubParam pubParam){
		boolean flag=prisonerService.delPrisoner(pubParam.getIds());
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}else{

			return ResultGenerator.setCustomResult(200,"删除成功");
		}
	}

	/*****************************车辆管理**********************************************/
	@ResponseBody
	@RequestMapping("/getCarList")
	public Result geCarList(@RequestBody PubParam pubParam){
		PageHelper.startPage(pubParam.getPageNum(),pubParam.getPageSize());
		List<OutboundCar> allList = carService.getAllCar(pubParam.getAreaId());
		PageInfo<OutboundCar> pageInfo = new PageInfo<>(allList);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
	@ResponseBody
	@RequestMapping("/addCar")
	public Result addCar(@RequestBody OutboundCar outboundCar){
		OutboundCar returnPrisoner=carService.addCar(outboundCar);
		if(returnPrisoner==null){
			return ResultGenerator.setCustomResult(4000,"新增失败");
		}else{
			return ResultGenerator.genSuccessResult(returnPrisoner);
		}
	}

	@ResponseBody
	@RequestMapping("/upCar")
	public Result upCar(@RequestBody OutboundCar outboundCar){
		boolean flag=carService.upCar(outboundCar);
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"修改失败");
		}else{
			return ResultGenerator.setCustomResult(200,"修改成功");
		}
	}

	@ResponseBody
	@RequestMapping("/delCar")
	public Result delCar(@RequestBody PubParam pubParam){
		boolean flag=carService.delCar(pubParam.getIds());
		if(!flag){
			return ResultGenerator.setCustomResult(4000,"删除失败");
		}else{
			return ResultGenerator.setCustomResult(200,"删除成功");
		}
	}


	@ResponseBody
	@RequestMapping("/getCar")
	public Result getCar(@RequestBody PubParam pubParam){
		List<OutboundCar> allList = carService.getCarAble(pubParam.getAreaId());

		if(allList != null && !allList.isEmpty()){
			return ResultGenerator.setCustomResult(200,"获取成功",allList);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用脚扣");
		}
	}
}
