package com.along.outboundmanage.dao;

import com.along.outboundmanage.model.OutboundRole;
import com.along.outboundmanage.model.OutboundSession;
import com.along.outboundmanage.model.OutboundUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {
	@Select("SELECT u.id as userId, u.user_name as userName,u.true_name as trueName, u.card as card,\n" +
			" u.area_id as areaId,a.name as areaName,a.par_id as parId,a.type as type,a.level as level,\n" +
			" r.id as roleId,r.role_name as roleName" +
			" FROM outbound_user u\n" +
			" left join outbound_user_role_rel rel on u.id=rel.user_id\n" +
			" left join outbound_role r on r.id=rel.role_id\n" +
			" left join outbound_area a on u.area_id=a.id\n" +
			" where u.area_id=#{areaId}")
	List<OutboundSession> getAllUserByAreaId(@Param("areaId") int areaId);

	@UpdateProvider(type = SqlProvider.class, method = "updataUser")
	boolean updateUserById(@Param("OutboundUser") OutboundUser use);

	@Insert("INSERT INTO outbound_user\n" +
			" (user_name, password, true_name, card, area_id)\n" +
			" VALUES(#{OutboundUser.userName}, #{OutboundUser.password},#{OutboundUser.trueName},#{OutboundUser.card},#{OutboundUser.areaId})")
	@Options(useGeneratedKeys = true, keyProperty = "OutboundUser.id", keyColumn = "id")
	int insertUser(@Param("OutboundUser") OutboundUser use);



	@Select("SELECT id, user_name, password, true_name, card, area_id\n" +
			" FROM outbound_user where user_name=#{OutboundUser.userName} and password=#{OutboundUser.password} order by id")
	List<OutboundUser> checkPassword(@Param("OutboundUser") OutboundUser use);

	@Select("SELECT id, user_name, password, true_name, card, area_id\n" +
			" FROM outbound_user where user_name=#{userName} order by id")
	List<OutboundUser> checkUserName(@Param("userName") String userName);

	@Select("SELECT id FROM outbound_user where card=#{card} order by id")
	Integer checkCard(@Param("card") String card);

	@Delete("delete from outbound_user where id in (${ids})")
	boolean delUser(@Param("ids") String ids);

	@Insert("INSERT INTO outbound_user_role_rel" +
			" ( user_id, role_id)" +
			" VALUES( #{userId},#{roleId})")
	boolean addUserRole(@Param("userId") int userId,@Param("roleId") int roleId);

	@Insert("UPDATE outbound_user_role_rel" +
			" SET  role_id=#{roleId}" +
			" WHERE user_id= #{userId}")
	boolean upUserRole(@Param("userId") int userId,@Param("roleId") int roleId);

	@Delete("DELETE FROM  outbound_user_role_rel" +
			" WHERE user_id in(${userId})")
	boolean delUserRole(@Param("userId") String userId);


}
