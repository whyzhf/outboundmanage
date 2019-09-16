package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
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
			"   select concat_ws(\",\",GROUP_CONCAT(equipment_id),GROUP_CONCAT(equipment_id2)) from outbound_police where id in(${ids})\n" +
			" )")
	boolean updataEquip(@Param("ids") String ids);


	@UpdateProvider(type = SqlProvider.class, method = "updataPoliceEquip")
	boolean updataPoliceEquip(@Param("list") List<OutboundPolice> list);

	@Select("SELECT GROUP_CONCAT(" +
			" concat_ws(\"\", concat_ws(\"(\",p.name,concat(p.card,\")\")) ,\n" +
			" concat_ws(\"(\",\"手表\",IFNULL(concat(e1.card,\")\"),\"(无)\")),\n" +
			" concat_ws(\"(\",\"遥控器\",IFNULL(concat(e2.card,\")\"),\"(无)\"))\n" +
			" ))\n" +
			" FROM outbound_police p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" left join outbound_equipment e2 on e2.id=p.equipment_id2\n" +
			" where p.id in (${ids})")
	String getreturn(@Param("ids") String ids);


	@Update("update outbound_equipment\n" +
			" SET `type`=0, status=1\n" +
			" WHERE id in (\n" +
			"   select GROUP_CONCAT(equipment_id) from outbound_prisoner where id in(${ids})\n" +
			" )")
	boolean updatapriEquip(@Param("ids") String ids);

	@UpdateProvider(type = SqlProvider.class, method = "updataPrisonerEquip")
	boolean updataPrisonerEquip(@Param("list") List<OutboundPrisoner> list);

	@Select("SELECT GROUP_CONCAT(" +
			" concat_ws(\"\", concat_ws(\"(\",p.name,concat(p.card,\")\")) ,\n" +
			" concat_ws(\"(\",\"脚扣\",IFNULL(concat(e1.card,\")\"),\"(无)\"))\n" +
			" ))\n" +
			" FROM outbound_prisoner p\n" +
			" left join outbound_equipment e1 on e1.id=p.equipment_id\n" +
			" where p.id in (${ids})")
	String getPrireturn(@Param("ids") String ids);


	@Select("select id,name,card,equipment_id,equipment_id2 from outbound_police  \n" +
			" where id not in \n" +
			" (SELECT ifnull(police_id,0)\n" +
			"  FROM outbound_task  t\n" +
			" left join outbound_task_police_rel r on t.id=r.task_id \n" +
			" where start_time like \"${time}%\" and area_id=${areaId}\n" +
			"    and t.id <> ${taskId}" +
			" )\n" +
			" and area_id=${areaId}")
	List<OutboundPolice> getPolices(@Param("taskId") String taskId,@Param("time") String time,@Param("areaId")String areaId);

	//查询可使用的设备
	@Select("SELECT id, card, name, `type`, status, form, area_id" +
			" FROM outbound_equipment " +
			" where area_id =#{areaId} and `type`=0 and status=1 and form = #{form} or id in(${id})")
	List<OutboundEquipment> getAllEquipment(@Param("areaId") int areaId, @Param("form") int form, @Param("id") String id);


	@Select("select id,name,card,equipment_id from outbound_prisoner  \n" +
			" where id not in \n" +
			" (SELECT ifnull(prisoner_id,0)\n" +
			"    FROM outbound_task  t\n" +
			"    left join outbound_task_prisoner_rel r on t.id=r.task_id \n" +
			"   where start_time like \"${time}%\" and area_id=${areaId}" +
			"    and t.id <> ${taskId}" +
			" )\n" +
			"  and area_id=${areaId}")
	List<OutboundPrisoner> getPrisoners(@Param("taskId") String taskId,@Param("time") String time,@Param("areaId")String areaId);
}
