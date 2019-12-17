package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundCar;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CarDao {
	@Select("Select id  FROM outbound_car  WHERE card =#{card}")
	Integer checCar(@Param("card") String card);

	@Insert("INSERT INTO outbound_car" +
			" ( card, `type`, status, area_id)" +
			" VALUES( #{OutboundCar.card}, #{OutboundCar.type}," +
			" #{OutboundCar.status},#{OutboundCar.areaId})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundCar.id", keyColumn = "id")
	int addCar(@Param("OutboundCar") OutboundCar outboundCar);

	@UpdateProvider(type = SqlProvider.class, method = "upCar")
	boolean upCar(@Param("OutboundCar") OutboundCar outboundCar);

	@Delete("Delete from  outbound_car where id in (${id})")
	boolean delCar(@Param("id") String id);

	@Select("SELECT id, card, `type`, status, area_id" +
			" FROM outbound_car " +
			" where area_id =#{areaId}")
	List<OutboundCar> getAllCar(@Param("areaId") int areaId);

	@Select("SELECT id, card, `type`, status, area_id" +
			" FROM outbound_car " +
			" where id in (${ids})")
	List<OutboundCar> getAllCarByIds(@Param("ids") String ids);

	@Select("SELECT id, card, `type`, status, area_id" +
			" FROM outbound_car " +
			" where area_id =#{areaId} and `type`=0 and status=1 ")
	List<OutboundCar> getCarAble(@Param("areaId") int areaId);
}