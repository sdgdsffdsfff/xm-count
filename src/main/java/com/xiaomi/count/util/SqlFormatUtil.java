package com.xiaomi.count.util;

import com.xiaomi.count.Constant;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析sql语句中的时间函数 别名函数 limit函数
 * Created by lijie on 2015-07-21.
 */
public class SqlFormatUtil {

    public static String formatTimestamp(String sql) {
        if (StringUtils.isEmpty(sql)) return null;

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        /**
         *  $(y, m, d, h, min, s)
         *  y: 年
         *  m: 月
         *  d: 日
         * h: 小时
         * min: 分钟
         * s: 秒
         */

        //年 时间戳
        String regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        boolean has = matcher.find();
        if (has) {
            calendar.setTime(date);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        //年月 时间戳
        regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql);
        has = matcher.find();
        if (has) {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        //年月日 时间戳
        regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql);
        has = matcher.find();
        if (has) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        //年月日 小时 时间戳
        regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql);
        has = matcher.find();
        if (has) {
            calendar.setTime(date);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        //年月日 小时 分钟 时间戳
        regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*,[\\s]*min[\\s]*\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql);
        has = matcher.find();
        if (has) {
            calendar.setTime(date);
            calendar.set(Calendar.SECOND, 0);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        //年月日 小时 分钟 秒 时间戳
        regex = "\\" + Constant.TIMESTAMP + "[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*,[\\s]*min[\\s]*,[\\s]*[s|S][\\s]*\\)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql);
        has = matcher.find();
        if (has) {
            calendar.setTime(date);
            sql = sql.replaceAll(regex, calendar.getTimeInMillis() / 1000 + "");
        }

        return sql;
    }

    /**
     * 格式化别名
     *
     * @param sql 原始sql语句
     * @return 返回格式化过后不包含别名的sql  和 别名
     */
    public static String[] formatAlias(String sql) {

        String[] sqlAndAlias = new String[2];

        String regex = "\\" + Constant.ALIAS + "[\\s]*\\(([\\s\\S]+)\\)[\\s]*[;]?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        boolean has = matcher.find();

        if (has) {
            //替换自定义函数 为空
            sql = sql.replaceAll(regex, "");
            sqlAndAlias[0] = sql;

            //别名
            if (matcher.groupCount() > 0) {
                sqlAndAlias[1] = matcher.group(1);
            }

        } else {
            sqlAndAlias[0] = sql;
            sqlAndAlias[1] = "";
        }

        return sqlAndAlias;
    }

    public static String formatLimit(String sql) {

        String regex = "limit[\\s]+[\\d]+[\\s]*,[\\s]*([\\d]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        boolean has = matcher.find();

        if (has) {
            if (matcher.groupCount() > 0) {
                return matcher.group(1);
            }
        }

        return null;
    }

}
