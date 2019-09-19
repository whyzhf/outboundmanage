package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPolice;
import com.along.outboundmanage.model.OutboundPoliceForSel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EquipmentDao {
	@Insert("INSERT INTO outbound_equipment" +
			" (card, name, `type`, status, form, area_id)" +
			" VALUES ( #{OutboundEquipment.card}, #{OutboundEquipment.name}," +
			" #{OutboundEquipment.type},#{OutboundEquipment.status},#{OutboundEquipment.form},#{OutboundEquipment.areaId})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundEquipment.id", keyColumn = "id")
	int addEquipment(@Param("OutboundEquipment") OutboundEquipment outboundEquipment);

	@UpdateProvider(type = SqlProvider.class, method = "upEquipment")
	boolean upEquipment(@Param("OutboundEquipment") OutboundEquipment outboundEquipment);

	@Delete("Delete from  outbound_equipment where id in (${id})")
	boolean delEquipment(@Param("id") String id);

	@Select("SELECT id, card, name, `type`, status, form, area_id" +
			" FROM outbound_equipment " +
			" where area_id =#{areaId} order by id desc ,form ")
	List<OutboundEquipment> getAllEquipment(@Param("areaId") int areaId);

	@Update("UPDATE outbound_equipment " +
			" SET `type`=#{type}, status=#{status}" +
			" WHERE id in (${id})")
	boolean upEquipmentTypeAndStatus(@Param("type") int type,@Param("status") int status,@Param("id") String id);
	//查询可使用的设备
	@Select("SELECT id, card, name, `type`, status, form, area_id" +
			" FROM outbound_equipment " +
			" where area_id =#{areaId} and `type`=0 and status=1 and form in (${form})")
	List<OutboundEquipment> getAllEquipmentByForm(@Param("areaId") int areaId,@Param("form") String form);


}