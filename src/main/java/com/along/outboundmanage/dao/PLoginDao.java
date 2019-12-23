package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PLoginDao {

	@Select("SELECT id, user_name, password, true_name, card, area_id" +
			" FROM outbound_user where user_name=#{OutboundUser.userName} and password=#{OutboundUser.password}")
	OutboundUser checkLogin(@Param("OutboundUser") OutboundUser user);

	@Select("SELECT logname FROM outbound_area where id=#{id}")
	String getlogname(@Param("id") int id);
}
