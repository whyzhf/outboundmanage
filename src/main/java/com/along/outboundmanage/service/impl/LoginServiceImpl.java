package com.along.outboundmanage.service.impl;


import com.along.outboundmanage.dao.*;
import com.along.outboundmanage.model.*;
import com.along.outboundmanage.service.LoginService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private PLoginDao PLoginDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SessionDao sessionDao;
    @Resource
    private MenuDao menuDao;
    @Resource
    private PoliceDao policeDao;
    @Override
    public OutboundUser checkLogin(OutboundUser user) {
        return PLoginDao.checkLogin(user);
    }

    @Override
    public OutboundUser getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public OutboundSession getSession(int userId) {
        return sessionDao.getSession(userId);
    }

    @Override
    public Map<String, Object> getMenu(int roleId) {
        return getTree(menuDao.getMenuByRoleId(roleId));
    }

    @Override
    public List<OutboundSession> userList(int areaId) {
        return userDao.getAllUserByAreaId(areaId);
    }



    @Override
    public OutboundUser addUser(OutboundUser user) {
        int id= 0;
        id= userDao.insertUser(user);
        if(id>0){
               /* OutboundPolice outboundPolice=new OutboundPolice();
                outboundPolice.setAreaId(user.getAreaId());
                outboundPolice.setCard(user.getCard());
                outboundPolice.setName(user.getTrueName());
                outboundPolice.setUserId(user.getId());*/
            OutboundPolice outboundPolice=new OutboundPolice
                                                .Builder()
                                                .areaId(user.getAreaId())
                                                .card(user.getCard())
                                                .name(user.getTrueName())
                                                .userId(user.getId())
                                                .build();
                policeDao.addPolice(outboundPolice);
            userDao.getAllUserById(user.getId());
        }else{
            user=null;

        }

        return user;

    }

    @Override
    public OutboundSession getAllUserById(int userId) {
        return userDao.getAllUserById(userId);
    }

    @Override
    public boolean delUser(String ids) {
        return userDao.delUser(ids);
    }

    @Override
    public boolean updateUserById(OutboundUser user) {

        return userDao.updateUserById(user);
    }

    @Override
    public List<OutboundUser> checkPassword(OutboundUser user) {
        return userDao.checkPassword(user);
    }

    @Override
    public List<OutboundUser> checkUserName(String userName) {
        return userDao.checkUserName(userName);
    }

    @Override
    public Integer checkCard(String card) {
        return userDao.checkCard(card);
    }

    @Override
    public boolean addUserRole(int userId, int roleId) {
        return userDao.addUserRole(userId,roleId);
    }

    @Override
    public boolean upUserRole(int userId, int roleId) {

        return userDao.upUserRole(userId,roleId);
    }

    @Override
    public boolean delUserRole(String userId) {
        return userDao.delUser(userId);
    }

    public Map<String, Object> getTree(List<OutboundMenu> list) {
        Map<String, Object> resmap=new HashMap<>();
        Map<Integer, OutboundMenu> map;
        List<OutboundMenu> treelist= new ArrayList<>();

        if (null==list||list.isEmpty()){
            return null;
        }
        map = list.stream().collect(Collectors.toMap(OutboundMenu::getId, a -> a,(k1, k2)->k1));
        // 将list集合对象转换为json的字符串
        // 如果id是父级的话就放入tree中treelist
        for (OutboundMenu menu : list) {

            if (null==map.get(menu.getParId())) {

                treelist.add(menu);
            } else {
                // 子级通过父id获取到父级的类型
                OutboundMenu parent = map.get(menu.getParId());
                // 父级获得子级，再将子级放到对应的父级中
                parent.addChildren(menu);
            }
        }
        resmap.put("data",treelist);
        return resmap;
    }
}
