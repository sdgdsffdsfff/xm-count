package com.xiaomi.count.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 临时表 数据读取
 * Created by lijie on 2015-07-28.
 */
@Repository
public class TempTableDao {
    @Autowired
    private JdbcTemplate jdbcTemplate2;

    public List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate2.queryForList(sql);
    }

    public List<String> queryForMetaData(String tableName) {

        List<String> columnList = new ArrayList<String>();
        DatabaseMetaData databaseMetaData = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate2.getDataSource().getConnection();
            databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                columnList.add(resultSet.getString("COLUMN_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return columnList;
    }

    public boolean existTable(String tableName) {

        boolean exist = false;

        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate2.getDataSource().getConnection();
            resultSet = connection.getMetaData().getTables(null, null, tableName, null);
            if (resultSet.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exist;
    }


    public int queryForInt(String sql) {

        sql = "select count(*)  " + sql.substring(sql.indexOf("from"),sql.indexOf("limit"));
        return jdbcTemplate2.queryForObject(sql, Integer.class);
    }
}
