package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;
import com.along.outboundmanage.model.OutboundPrisoner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface EquipRelManageDao {
	@Update("update outbound_equipment\n" +
			" SET `type`=0, status=1\n" +
			" WHERE id in (\n" +
				" select equipment_id from outbound_police where id in(${ids})\n" +
				"  UNION" +
				" select equipment_id2 from outbound_police where id in(${ids})"+
			" )")
	boolean updataEquip(@Param("ids") String ids);


	@UpdateProvider(type = SqlProvider.class, method = "updataPoliceEquip")
	boolean updataPoliceEquip(@Param("list") List<OutboundPoliceForSel> list);

	@Select("SELECT GROUP_CONCAT(" +
			" concat_ws(\"\", concat_ws(\"(\",p.name,concat(p.card,\")\")) ,\n" +
			" concat_ws(\"(\",\"手表\",IFNULL(concat(e1.card,\")\"),\"无)\")),\n" +
			" concat_ws(\"(\",\"遥控器\",IFNULL(concat(e2.card,\")\"),\"无)\"))\n" +
			" ))\n" +
			" FROM outbound_police p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" left join outbound_equipment e2 on e2.id=p.equipment_id2\n" +
			" where p.id in (${ids})")
	String getreturn(@Param("ids") String ids);


	@Update("update outbound_equipment\n" +
			" SET `type`=0, status=1\n" +
			" WHERE id in (\n" +
			"   select equipment_id from outbound_prisoner where id in(${ids})\n" +
			" )")
	boolean updatapriEquip(@Param("ids") String ids);

	@UpdateProvider(type = SqlProvider.class, method = "updataPrisonerEquip")
	boolean updataPrisonerEquip(@Param("list") List<OutboundPrisoner> list);

	@Select("SELECT GROUP_CONCAT(" +
			" concat_ws(\"\", concat_ws(\"(\",p.name,concat(p.card,\")\")) ,\n" +
			" concat_ws(\"(\",\"脚扣\",IFNULL(concat(e1.card,\")\"),\"无)\"))\n" +
			" ))\n" +
			" FROM outbound_prisoner p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" where p.id in (${ids})")
	String getPrireturn(@Param("ids") String ids);

	@Select("SELECT p.id,0 as userId, p.card, p.name, p.equipment_id as equipmentId, p.equipment_id2 as equipmentId2, " +
			" p.area_id as areaId,e1.card as equipmentCard,e2.card as equipmentCard2\n" +
			" FROM outbound_police p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" left join outbound_equipment e2 on e2.id=p.equipment_id2" +
			" where p.id not in \n" +
			" (SELECT ifnull(police_id,0)\n" +
			"  FROM outbound_task  t\n" +
			" left join outbound_task_police_rel r on t.id=r.task_id \n" +
			" where start_time like \"${time}%\" and area_id=${areaId}\n" +
			"    and t.id <> ${taskId} and t.status<4" +
			" )\n" +
			" and p.area_id=${areaId}")
	List<OutboundPoliceForSel> getPolices(@Param("taskId") String taskId, @Param("time") String time, @Param("areaId")String areaId);

	//查询可使用的设备
	@Select("SELECT id, card, name, `type`, status, form, area_id" +
			" FROM outbound_equipment " +
			" where (area_id =#{areaId} and `type`=0 and status=1   or id in(${id}) ) and form = #{form}")
	List<OutboundEquipment> getAllEquipment(@Param("areaId") int areaId, @Param("form") int form, @Param("id") String id);

	@Select("SELECT p.id, p.card, p.name, p.equipment_id as equipmentId, p.area_id as areaId,e.card as equipmentCard" +
			" FROM outbound_prisoner p " +
			" left join outbound_equipment e on e.id=p.equipment_id" +
			" where  p.id not in \n" +
			" (SELECT ifnull(prisoner_id,0)\n" +
			"    FROM outbound_task  t\n" +
			"    left join outbound_task_prisoner_rel r on t.id=r.task_id \n" +
			"   where start_time like \"${time}%\" and area_id=${areaId}" +
			"    and t.id <> ${taskId} and t.status<4 " +
			" )\n" +
			"  and  p.area_id=${areaId} and p.ifdel=0")
	List<OutboundPrisoner> getPrisoners(@Param("taskId") String taskId,@Param("time") String time,@Param("areaId")String areaId);
}
