package com.along.outboundmanage.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

public interface CrossDao {
	@Cacheable(value = "getlist" , key="#star+'_'+#end")
	@Select("select id,lot from cross_gps_data" +
			" where curr_time " +
			"between '${star}' and '${end}'")
	List<Map<String,Object>> getlist(@Param("star") String star,@Param("end") String end);

	@Select("select * from cross_gps_data where curr_time between '2018-09-05 13:50:49' and '2020-09-05 14:50:49' limit #{index}")
	List<Map<String,Object>> getlistLimt(@Param("index") Integer index);
}
