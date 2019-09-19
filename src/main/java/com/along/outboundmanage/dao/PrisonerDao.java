package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundEquipment;
import com.along.outboundmanage.model.OutboundPrisoner;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PrisonerDao {
	@Insert("INSERT INTO outbound_prisoner" +
			" ( card, name, equipment_id, area_id,intime,ifdel)" +
			" VALUES( #{OutboundPrisoner.card}, #{OutboundPrisoner.name}," +
			"#{OutboundPrisoner.equipmentId},#{OutboundPrisoner.areaId},now(),0)")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundPrisoner.id", keyColumn = "id")
	int addPrisoner(@Param("OutboundPrisoner") OutboundPrisoner outboundPrisoner);

	@UpdateProvider(type = SqlProvider.class, method = "upPrisoner")
	boolean upPrisoner(@Param("OutboundPrisoner") OutboundPrisoner outboundPrisoner);
	//需修改，将要删除的犯人修改状态，依旧保存犯人数据，（统计在押人数）
	//@Delete("Delete from  outbound_prisoner where id in (${id})")
	@Update("UPDATE outbound_prisoner  SET outtime=now(), ifdel=1" +
			" WHERE id in (${id})")
	boolean delPrisoner(@Param("id") String id);

	@Select("SELECT p.id, p.card, p.name, p.equipment_id as equipmentId, p.area_id as areaId,e.card as equipmentCard" +
			" FROM outbound_prisoner p " +
			" left join outbound_equipment e on e.id=p.equipment_id" +
			" where p.area_id =#{areaId} and p.ifdel=0 order  by p.id desc ")
	List<OutboundPrisoner> getAllPrisoner(@Param("areaId") int areaId);

	@Select("SELECT id, card, name, equipment_id, area_id\n" +
			" FROM outbound_prisoner\n" +
			" WHERE id in (${id}) ")
	List<OutboundPrisoner> getPrisonerByIds(@Param("id") String id);

	@Select("SELECT p.id, p.card, p.name, p.equipment_id as equipmentId, p.area_id as areaId,e.card as equipmentCard" +
			" FROM outbound_prisoner p " +
			" left join outbound_equipment e on e.id=p.equipment_id" +
			" where p.id in (${ids})")
	List<OutboundPrisoner> getAllPrisonerById(@Param("ids") String ids);
}