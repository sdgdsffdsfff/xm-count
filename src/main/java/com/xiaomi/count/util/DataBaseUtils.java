package com.xiaomi.count.util;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 创建数据库表,删除表,清空表,更新表字段
 * Created by lijie on 2015-07-27.
 */
public class DataBaseUtils {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.38.220:3306/xm_count?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567";


    private DataBaseUtils() {
    }

    static {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 释放资源
    public static void release(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createTable(Map<String, Object> map, String tableName) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS ");
        stringBuilder.append(tableName);
        stringBuilder.append(" ( ");
        for (String key : map.keySet()) {
            Object value = map.get(key);
            String type = value.getClass().getSimpleName();
            if (type.equals("Date")) {
                stringBuilder.append(key).append(" datetime,");
            } else if (type.equals("BigInteger")) {
                stringBuilder.append(key).append(" bigint(15),");
            } else if (type.equals("Long")) {
                stringBuilder.append(key).append(" int(13),");
            } else if (type.equals("Integer")) {
                stringBuilder.append(key).append(" int(10),");
            } else if (type.equals("String")) {
                stringBuilder.append(key).append(" text,");
            } else {

            }

        }
        String sql = stringBuilder.toString();
        if (sql.contains(",")) {
            sql = sql.substring(0, sql.lastIndexOf(","));
        }
        sql = sql + " ) ENGINE=InnoDB DEFAULT CHARSET=utf8";

        Statement statement = null;

        Connection connection = DataBaseUtils.getConnection();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DataBaseUtils.release(null, statement, connection);

    }

    public static void dropTable(String tableName) {

        String sql = "DROP TABLE IF EXISTS " + tableName;

        Statement statement = null;

        Connection connection = DataBaseUtils.getConnection();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DataBaseUtils.release(null, statement, connection);
    }

    public static void deleteTable(String tableName) {
        String sql = "DELETE FROM  " + tableName;

        Statement statement = null;

        Connection connection = DataBaseUtils.getConnection();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DataBaseUtils.release(null, statement, connection);
    }

    public static void insert(List<Map<String, Object>> list, String tableName) {
        if (list.size() > 0) {

            int params = 0;

            String sql = "insert into " + tableName + " (";
            Map<String, Object> map = list.get(0);

            for (String key : map.keySet()) {
                sql += key + ",";
                params++;
            }

            if (sql.contains(",")) {
                sql = sql.substring(0, sql.lastIndexOf(","));
            }
            sql += " ) values (";

            for (int i = 0; i < params; i++) {
                sql += "?,";
            }

            if (sql.contains(",")) {
                sql = sql.substring(0, sql.lastIndexOf(","));
            }

            sql += " )";

            Connection connection = DataBaseUtils.getConnection();
            PreparedStatement preparedStatement = null;

            if (connection != null) {
                try {
                    connection.setAutoCommit(false);
                    preparedStatement = connection.prepareStatement(sql);


                    int index;
                    int total = 0;
                    for (Map<String, Object> objectMap : list) {
                        index = 1;
                        for (String key : objectMap.keySet()) {
                            Object value = objectMap.get(key);
                            preparedStatement.setObject(index++, value);
                        }
                        preparedStatement.addBatch();

                        total++;
                        try {
                            if (total % 1000 == 0 || total == list.size()) {
                                preparedStatement.executeBatch();
                                connection.commit();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            DataBaseUtils.release(null, preparedStatement, connection);
        }
    }

}
