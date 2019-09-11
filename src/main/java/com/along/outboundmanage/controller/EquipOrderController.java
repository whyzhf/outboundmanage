package com.along.outboundmanage.controller;

import com.along.outboundmanage.model.ExceptionEntity.Result;
import com.along.outboundmanage.model.ExceptionEntity.ResultGenerator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/eorder")
public class EquipOrderController {

	@ResponseBody
	@RequestMapping("/pubsubdueOrder")
	public Result pubsubdueOrder(@RequestBody Map<String, Object> pubParam){
		String userId = (String) pubParam.get("userId");
		String[] equipIds = (String[]) pubParam.get("equipIds");
		return ResultGenerator.genSuccessResult("ok");
	}
}
