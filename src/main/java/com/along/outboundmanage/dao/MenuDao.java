package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {
	@Select("select m.id, menu_name as menuName, par_id as parId, type, url" +
			" from outbound_menu m" +
			" left join outbound_role_menu_rel r on r.menu_id=m.id" +
			" where r.role_id=#{roleId}")
	List<OutboundMenu> getMenuByRoleId(@Param("roleId") int roleId);
}
