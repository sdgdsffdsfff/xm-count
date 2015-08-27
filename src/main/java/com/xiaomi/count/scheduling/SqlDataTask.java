package com.xiaomi.count.scheduling;

import com.xiaomi.count.Constant;
import com.xiaomi.count.bean.AgentSeeker;
import com.xiaomi.count.bean.BarSeeker;
import com.xiaomi.count.bean.IPSeeker;
import com.xiaomi.count.model.History;
import com.xiaomi.count.model.Task;
import com.xiaomi.count.service.BootService;
import com.xiaomi.count.service.HistoryService;
import com.xiaomi.count.util.DataBaseUtils;
import com.xiaomi.count.util.SqlFormatUtil;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据库查询任务写入任务 根据配置sql执行
 * Created by lijie on 2015-07-17.
 */
public class SqlDataTask implements Runnable {

    private Task task;
    private BootService bootService;
    private HistoryService historyService;
    private IPSeeker ipSeeker;
    private AgentSeeker agentSeeker;
    private BarSeeker barSeeker;

    private History history;

    public SqlDataTask(Task task, BootService bootService, HistoryService historyService, IPSeeker ipSeeker, AgentSeeker agentSeeker, BarSeeker barSeeker, History history) {
        super();
        this.task = task;
        this.bootService = bootService;
        this.historyService = historyService;
        this.ipSeeker = ipSeeker;
        this.agentSeeker = agentSeeker;
        this.barSeeker = barSeeker;
        this.history = history;
    }

    public void run() {

        //可能有多sql语句执行
        String originalSql = task.getSql();
        //格式化时间函数
        originalSql = SqlFormatUtil.formatTimestamp(originalSql);
        //格式化别名函数
        String[] sqlAndAlias = SqlFormatUtil.formatAlias(originalSql);
        originalSql = sqlAndAlias[0];
        //别名
        String[] alias = sqlAndAlias[1].split(",");
        //格式化limit函数
        String limit = SqlFormatUtil.formatLimit(originalSql);

        //判断创建测试表 还是正式表
        String table = (Constant.test.equals(task.getTest()) || task.getTest() == null) ? task.getTable() : task.getTable() + "_";
        String model = task.getModel();

        Date date = new Date();
       // SimpleDateFormat yhdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        History entity;
        if (history == null) {
            entity = new History();
            entity.setStart(date);
            entity.setTime(0L);
            entity.setState(Constant.RUNNING);
            entity.setTask(task);
        } else {
            entity = history;
            entity.setError(null);
            entity.setTimes(entity.getTimes() + 1);
        }

        historyService.saveOrUpdate(entity);

        try {

            List<Map<String, Object>> list;

            int start = 0;
            boolean doWhile;

            do {

                String newSql;

                if (limit == null) {
                    newSql = originalSql + "  limit " + start + "," + Constant.LIMIT;
                } else {
                    newSql = originalSql;
                }


                list = bootService.executeSql(newSql);

                if (limit == null) {
                    start += Constant.LIMIT;
                    doWhile = list.size() == Constant.LIMIT;
                } else {
                    doWhile = false;
                }


                boolean createTable = false;

                List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

                for (Map<String, Object> map : list) {
                    //别名替换

                    Map<String, Object> newMap = new HashMap<String, Object>();

                    int i = 0;
                    for (String key : map.keySet()) {
                        Object value = map.get(key);
                        String newKey;
                        if (Constant.IP.equalsIgnoreCase(key)) {
                            value = ipSeeker.getAddress(String.valueOf(value));
//                            newMap.put(Constant.IP, value);
                        } else if (Constant.AGENT.equalsIgnoreCase(key)) {
                            value = agentSeeker.getAgentText(String.valueOf(value));
//                            newMap.put(Constant.AGENT, value);
                        } else if (Constant.TIME.equalsIgnoreCase(key)) {
//                            newMap.put(Constant.TIME, Long.valueOf(String.valueOf(value)));
                            value = new Date(Long.valueOf(String.valueOf(value)) * 1000);
                        } else if (Constant.USERID.equalsIgnoreCase(key)) {
//                            newMap.put(Constant.TIME, Long.valueOf(String.valueOf(value)));
                            value = barSeeker.getBarname(value);
                        }

                        try {
                            newKey = alias[i++];
                            if (StringUtils.isEmpty(newKey)) {
                                newKey = key;
                            }
                        } catch (Exception e) {
                            newKey = key;
                        }
                        newMap.put(newKey, value);

                    }

                    newList.add(newMap);

                    if (!createTable) {
                        DataBaseUtils.createTable(newMap, table);
                        createTable = true;

                        //覆盖保存清空表  测试提交清空表 (都是在表存在的情况下)
                        if ("1".equals(model) || "0".equals(task.getTest())) {
                            DataBaseUtils.deleteTable(table);
                        }

                    }

                }

                DataBaseUtils.insert(newList, table);

            } while (doWhile);

        } catch (Exception e) {
            e.printStackTrace();
            entity.setError(e.getMessage());
            entity.setTimes(entity.getTimes() == null ? 0 : entity.getTimes());
        }

        if (entity.getError() == null) {
            entity.setState(Constant.SUCCESS);
        } else {
            entity.setState(Constant.FAILURE);
        }
        entity.setTime((new Date().getTime() - date.getTime()) / 1000);
        historyService.saveOrUpdate(entity);

    }

}
