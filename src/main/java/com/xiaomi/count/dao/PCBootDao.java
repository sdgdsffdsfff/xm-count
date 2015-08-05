package com.xiaomi.count.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 访问mycat 只读数据源
 * Created by lijie on 2015-07-07.
 */
@Repository
public class PCBootDao {

    @Autowired
    private JdbcTemplate jdbcTemplate1;

    /**
     * 查询某个时间点的总量
     *
     * @param sql    查询sql语句
     * @param values 查询参数
     * @return 返回总量
     */
    public List<Map<String, Object>> executeSql(String sql, Object... values) {
        return jdbcTemplate1.queryForList(sql, values);
    }

}
