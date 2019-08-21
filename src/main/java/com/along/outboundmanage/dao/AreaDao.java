package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AreaDao {
	@Select("SELECT id, name, par_id as parId, initials,type, level FROM outbound_area")
	List<OutboundArea> getAllArea();
	//获取下级所有area_id
	@Select("SELECT id  FROM outbound_area where par_id=#{areaId}")
	List<Integer> getAreaIdsByPar(@Param("areaId") int areaId);

	@Select("SELECT id, name, par_id as parId, initials,type, level FROM outbound_area where par_id=#{areaId}")
	List<OutboundArea> getAllAreaDesc(@Param("areaId") int areaId);
}
