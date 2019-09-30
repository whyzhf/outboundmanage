package com.along.outboundmanage.utill.GpsUtil;

import com.along.outboundmanage.utill.GpsUtil.server.IntermediatePointServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动加载数据
 */
@Component
@Order(value = 1)
public class DuckLoad implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(DuckLoad.class);

	public void run(String... args){
		IntermediatePointServer intermediatePointServer = new IntermediatePointServer();
	}
}
