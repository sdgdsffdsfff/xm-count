package com.xiaomi.count.service;

import com.xiaomi.count.dao.UserDao;
import com.xiaomi.count.model.Business;
import com.xiaomi.count.model.Rs;
import com.xiaomi.count.model.User;
import com.xiaomi.count.model.UserBz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户管理
 * Created by lijie on 2015-06-16.
 */
@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBzService userBzService;
    @Autowired
    private BusinessService businessService;

    public void saveAllAuths(User user, String[] rs, Map<String, String> bz) {
        //栏目权限
        Set<Rs> rsSet = new HashSet<Rs>();
        for (String rsId : rs) {
            Rs rsEntity = new Rs();
            rsEntity.setId(Integer.valueOf(rsId));
            rsSet.add(rsEntity);
        }
        user.setRsSet(rsSet);
        userDao.update(user);

        //业务权限
        String hql = "delete from UserBz where userId=?";
        userBzService.queryHql(hql, user.getId());

        for (String key : bz.keySet()) {
            Business business = businessService.get(Integer.valueOf(key));
            UserBz userBz = new UserBz();
            userBz.setUserId(user.getId());
            userBz.setId(business.getId());
            userBz.setName(business.getName());
            userBz.setOrderNo(business.getOrderNo());
            userBz.setCode(bz.get(key));
            userBzService.save(userBz);
        }

    }
}
