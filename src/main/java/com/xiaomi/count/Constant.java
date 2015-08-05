package com.xiaomi.count;

/**
 * 系统常用常量
 * Created by lijie on 2015-06-25.
 */
public class Constant {
    public static final String CURRENT_USER = "currentLoginUser";

    public static final String IP = "IP";
    public static final String CN_IP = "地区";

    public static final String AGENT = "AGENTID";
    public static final String CN_AGENT = "代理";

    public static final String VER = "VER";
    public static final String CN_VER = "版本";

    public static final String TIME = "TIME";
    public static final String CN_TIME = "时间";


    public static final String ALL = "全部";

    public static final String SUPER = "admin";
    public static final String IS_SUPER = "isSuper";

    public static final String FAILURE = "0";
    public static final String SUCCESS = "1";
    public static final String RUNNING = "2";

    public static final int LIMIT = 100000;

    public static final String TIMESTAMP = "$timestamp";
    public static final String ALIAS = "$alias";


    public static final String Immediately = "1";//任务立即执行
    public static final String delay = "2";//任务定时执行

    public static final String SQL = "1";//SQL 任务
    public static final String PYTHON = "2";//PYTHON 任务
    public static final String PATH = "/resources/upload/python";//python文件上传位置
    public static final String FUNCTION = "databaseTask";

    public static final String state = "1";//任务启动状态

    public static final String add = "2";//增量写入临时表
    public static final String update = "1";//覆盖写入临时表

    public static final String test = "1";//测试当前任务

}
