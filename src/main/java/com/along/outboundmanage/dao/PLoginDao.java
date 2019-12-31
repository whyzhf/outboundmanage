package com.along.outboundmanage.dao;

import com.along.outboundmanage.config.CacheExpire;
import com.along.outboundmanage.model.OutboundUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

public interface PLoginDao {
	@Cacheable(value = "checkLogin" ,key = "#p0.toString()",unless="#result == null")
	@CacheExpire(expire = 60*5)
	@Select("SELECT id, user_name, password, true_name, card, area_id" +
			" FROM outbound_user where user_name=#{OutboundUser.userName} and password=#{OutboundUser.password}")
	OutboundUser checkLogin(@Param("OutboundUser") OutboundUser user);

	@Select("SELECT logname FROM outbound_area where id=#{id}")
	String getlogname(@Param("id") int id);
}
