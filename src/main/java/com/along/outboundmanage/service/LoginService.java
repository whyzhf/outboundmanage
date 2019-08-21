package com.along.outboundmanage.service;

import com.along.outboundmanage.model.OutboundMenu;
import com.along.outboundmanage.model.OutboundSession;
import com.along.outboundmanage.model.OutboundUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LoginService {
	//判断是否可以登录
	OutboundUser checkLogin(OutboundUser user);

	//登录成功创建session
	OutboundSession getSession(int userId);

	//登录成功获取菜单
	Map<String, Object> getMenu(int roleId);

	List<OutboundSession> userList(int areaId);

	OutboundUser addUser(OutboundUser user);

	boolean delUser(String ids);
	//修改用户信息
	boolean updateUserById(OutboundUser user);

	List<OutboundUser> checkPassword(OutboundUser user);

	List<OutboundUser> checkUserName(String userName);
	Integer checkCard(String card);
	//添加用户角色信息
	boolean addUserRole(int userId, int roleId);
	//修改用户角色信息
	boolean upUserRole(int userId, int roleId);
	//删除用户角色信息
	boolean delUserRole(String userId);
}
