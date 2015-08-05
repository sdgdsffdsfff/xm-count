package com.xiaomi.count.service;

import com.xiaomi.count.dao.TempTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 临时表数据读取
 * Created by lijie on 2015-07-28.
 */
@Service
public class TempTableService {
    @Autowired
    private TempTableDao tempTableDao;

    public List<Map<String, Object>> queryForList(String sql) {
        return tempTableDao.queryForList(sql);
    }

    public List<String> queryForMetaData(String tableName) {
        return tempTableDao.queryForMetaData(tableName);
    }

    public boolean existTable(String tableName) {

        return tempTableDao.existTable(tableName);
    }


    public int queryForInt(String sql) {
        return tempTableDao.queryForInt(sql);
    }
}
