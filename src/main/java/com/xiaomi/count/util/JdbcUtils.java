package com.xiaomi.count.util;

import java.sql.*;

/**
 * 数据库连接工具类
 * Created by lijie on 2015-07-24.
 */
public class JdbcUtils {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://121.40.155.223:3306/vnmqdbs1?useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "t540p@xmnet123.";


    private JdbcUtils() {
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

}
