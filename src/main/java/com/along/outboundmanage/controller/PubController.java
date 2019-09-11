package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.service.EquipmentService;
import com.along.outboundmanage.service.PubService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pub")
public class PubController {
	@Resource
	private PubService pubService;
	@Resource
	private EquipmentService equipmentService;
	@ResponseBody
	@RequestMapping("/getPolices")
	public Result getPolices(@RequestBody Map<String, String> pubParam){
		String time =  pubParam.get("time");
		String areaId =  pubParam.get("areaId");

		return ResultGenerator.genSuccessResult(pubService.getPolices(time,areaId));
	}
	@ResponseBody
	@RequestMapping("/getWatchs")
	public Result getWatchs(@RequestBody Map<String, String> pubParam){
		String time =  pubParam.get("time");
		String areaId =  pubParam.get("areaId");
		List<OutboundEquipment> allEquipmentByForm = pubService.getWatchs(time,areaId);
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm.size()>1){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}
	@ResponseBody
	@RequestMapping("/getHandsetIds")
	public Result getHandsetIds(@RequestBody Map<String, String> pubParam){
		List<OutboundEquipment> allEquipmentByForm = pubService.getHandsetIds(pubParam.get("time"),pubParam.get("areaId"));
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm.size()>1){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}

	@ResponseBody
	@RequestMapping("/getPrisoners")
	public Result getPrisoners(@RequestBody Map<String, String> pubParam){
		String time =  pubParam.get("time");
		String areaId =  pubParam.get("areaId");
		return ResultGenerator.genSuccessResult(pubService.getPrisoners(time,areaId));
	}
	@ResponseBody
	@RequestMapping("/getGrapplersIds")
	public Result getGrapplersIds(@RequestBody Map<String, String> pubParam){
		List<OutboundEquipment> allEquipmentByForm = pubService.getGrapplersIds(pubParam.get("time"),pubParam.get("areaId"));
		allEquipmentByForm.add(0,new OutboundEquipment.Builder().card(" ").build());
		if(allEquipmentByForm.size()>1){
			return ResultGenerator.setCustomResult(200,"获取成功",allEquipmentByForm);
		}else{
			return ResultGenerator.setCustomResult(4000,"暂无可用设备");
		}
	}
}
