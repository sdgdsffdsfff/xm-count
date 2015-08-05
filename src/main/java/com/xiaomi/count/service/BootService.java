package com.xiaomi.count.service;

import com.xiaomi.count.dao.PCBootDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * mycat 数据库数据统计
 * Created by lijie on 2015-07-07.
 */
@Service
public class BootService {

    @Autowired
    private PCBootDao pcBootDao;

    public List<Map<String, Object>> executeSql(String sql,Object... values) {
        return pcBootDao.executeSql(sql, values);
    }

}
