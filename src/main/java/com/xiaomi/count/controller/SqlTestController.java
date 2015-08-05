package com.xiaomi.count.controller;

import com.xiaomi.count.util.JdbcUtils;
import com.xiaomi.count.util.SqlFormatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 检查sql语言语法正确性,测试sql语言的执行
 * Created by lijie on 2015-07-24.
 */
@Controller
@RequestMapping(value = "/test")
public class SqlTestController {

    @RequestMapping(value = "/explain")
    @ResponseBody
    public String testSqlGrammar(String sql) {
        String message = "";

        if (sql == null) {
            sql = "";
        }

        sql = SqlFormatUtil.formatTimestamp(sql);
        sql = SqlFormatUtil.formatAlias(sql)[0];

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sql = "EXPLAIN " + sql;


        Connection connection = JdbcUtils.getConnection();
        Statement statement = null;
        try {
            if (connection != null) {
                statement = connection.createStatement();
                statement.execute(sql);
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        JdbcUtils.release(null, statement, connection);

        return message;
    }


}
