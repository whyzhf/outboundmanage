package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface PoliceDao {
	@Insert("INSERT INTO outbound_police\n" +
			" (card, name, equipment_id, equipment_id2, area_id,user_id)\n" +
			" VALUES( #{OutboundPolice.card}, #{OutboundPolice.name}, #{OutboundPolice.equipmentId}, #{OutboundPolice.equipmentId2}, #{OutboundPolice.areaId}, #{OutboundPolice.userId})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundPolice.id", keyColumn = "id")
	int addPolice(@Param("OutboundPolice") OutboundPolice outboundPolice);

	@UpdateProvider(type = SqlProvider.class, method = "upPolice")
	boolean upPolice(@Param("OutboundPolice") OutboundPolice outboundPolice);


	@Delete("Delete from  outbound_police where id in (${id})")
	boolean delPolice(@Param("id") String id);

	/*@Update("UPDATE outbound_police\n" +
			"SET  equipment_id=null, equipment_id2=null,ifdel=1\n" +
			"WHERE id in (${id})")
	boolean delPolice(@Param("id") String id);*/

	@Select("Select user_id  FROM outbound_police  WHERE card =#{card}")
	Integer findPoliceUserId(@Param("card") String card);

	@Select("SELECT p.id,p.user_id, p.card, p.name, p.equipment_id as equipmentId, p.equipment_id2 as equipmentId2, " +
			" p.area_id as areaId,e1.card as equipmentCard,e2.card as equipmentCard2\n" +
			" FROM outbound_police p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" left join outbound_equipment e2 on e2.id=p.equipment_id2" +
			" where p.area_id =#{areaId} order by p.id desc")
	List<OutboundPoliceForSel> getAllPolice(@Param("areaId") int areaId);

	@Select("SELECT id, card, name, equipment_id, equipment_id2, user_id,area_id" +
			" FROM outbound_police where id in (${ids})")
	List<OutboundPolice> getPoliceByIds(@Param("ids") String ids);


	@Select("SELECT p.id,p.user_id, p.card, p.name, p.equipment_id as equipmentId, p.equipment_id2 as equipmentId2, " +
			" p.area_id as areaId,e1.card as equipmentCard,e2.card as equipmentCard2\n" +
			" FROM outbound_police p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" left join outbound_equipment e2 on e2.id=p.equipment_id2" +
			" where p.id in (${ids})")
	List<OutboundPoliceForSel> getAllPoliceById(@Param("ids") String ids);


}