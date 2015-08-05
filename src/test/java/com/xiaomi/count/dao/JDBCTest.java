package com.xiaomi.count.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class JDBCTest {

    @Autowired
    private JdbcTemplate jdbcTemplate2;


    @Test
    public void testGetTotal() throws Exception {

        String sql = "select * from agent";
        List<Map<String, Object>> list = jdbcTemplate2.queryForList(sql);
        System.out.println(list.size());
    }


    @Test
    public void testFind() throws SQLException {
//        DatabaseMetaData databaseMetaData = jdbcTemplate2.getDataSource().getConnection().getMetaData();
//        ResultSet rs = databaseMetaData.getColumns(null, null, "1_15", null);
//        while (rs.next()) {
//            System.out.println(rs.getString("COLUMN_NAME"));
//        }
//        System.out.println(rs);
        try {
            ResultSet rs = jdbcTemplate2.getDataSource().getConnection().getMetaData().getTables(null, null, "1_15", null);
            if (rs.next()) {
                System.out.println("cuzai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}


//new ResultSetExtractor<List<KeyValue<String, String>>>() {
//@Override
//public List<KeyValue<String, String>> extractData(
//        ResultSet rs) throws SQLException,
//        DataAccessException {
//        ResultSetMetaData metaData = rs.getMetaData();
//        int count = metaData.getColumnCount();
//        List<KeyValue<String, String>> l = new ArrayList<KeyValue<String, String>>();
//        for (int i = 0; i < count; i++) {
//        String fieldName = metaData.getColumnName(i + 1).toLowerCase();
//        int type = metaData.getColumnType(i + 1);
//        String typeName = metaData.getColumnTypeName(i + 1).toLowerCase();
//        System.out.println(fieldName + " : " + type + " : "
//        + typeName);
//        l.add(new KeyValue<String, String>(fieldName,
//        typeName));
//        }
//        return l;
//        }
//        });