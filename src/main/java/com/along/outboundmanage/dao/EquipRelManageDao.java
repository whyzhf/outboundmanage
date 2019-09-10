package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPrisoner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

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
}
