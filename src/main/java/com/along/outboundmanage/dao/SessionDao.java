package com.along.outboundmanage.dao;

import com.along.outboundmanage.config.CacheExpire;
import com.along.outboundmanage.model.OutboundSession;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

public interface SessionDao {
	@Cacheable(value = "getSession"  ,key = "#p0",unless="#result == null")
	@CacheExpire(expire = 60*5)
	@Select("SELECT u.id as userId, u.user_name as userName,u.true_name as trueName, u.card as card,\n" +
			" u.area_id as areaId,a.name as areaName,a.par_id as parId,a.type as type,a.level as level,\n" +
			" r.id as roleId,r.role_name as roleName\t\n" +
			" FROM outbound_user u\n" +
			" left join outbound_user_role_rel rel on u.id=rel.user_id\n" +
			" left join outbound_role r on r.id=rel.role_id\n" +
			" left join outbound_area a on u.area_id=a.id\n" +
			" where u.id=#{userId}")
	OutboundSession getSession(@Param("userId") int userId);

}
