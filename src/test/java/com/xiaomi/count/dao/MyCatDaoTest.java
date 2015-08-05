package com.xiaomi.count.dao;

import com.xiaomi.count.service.BootService;
import com.xiaomi.count.util.DataBaseUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class MyCatDaoTest {

    @Autowired
    private BootService bootService;


    @Test
    public void testGetTotal() throws Exception {

        Long timeStart = System.currentTimeMillis();
        String sql = "SELECT * FROM pcboot  WHERE TIME >= 1437408000 - 86400 AND TIME < 1437408000";
//         sql = "select time,ver,ip,agentid from pcboot where time>=$timestamp(y,m,d)-86400 and time<$timestamp(y,m,d)";


        List<Map<String, Object>> list;
        int start = 0;
        boolean doWhile;

        do {

            String newSql = sql + "  limit " + start + ",100000";

            list = bootService.executeSql(newSql);

            System.out.println(list.size()+"**************");

            start += 100000;
            doWhile = list.size() == 100000;

            DataBaseUtils.insert(list, "test");

        } while (doWhile);

//        List<Map<String, Object>> list = bootService.executeSql(sql);
//        for(Map<String ,Object> map :list){
////            String value =String.valueOf( map.get("boottime"));
////            Long longValue = Long.valueOf(value);
//            int length= map.size();
//            if(length<10){
//                System.out.println(map.get("id"));
//            }
//        }

//
//        Map<String, Object> map = list.get(0);
//        for (String key : map.keySet()) {
//            System.out.println("key:  " + key + "   value:  " + map.get(key) + "   type:  " + map.get(key).getClass().getSimpleName());
//        }
//
//        DataBaseUtils.dropTable("test");

//        DataBaseUtils.createTable(map, "test");



        long timeEnd = System.currentTimeMillis();

        System.out.println((timeEnd - timeStart) / 1000);
    }

    @Test
    public void testDrop() throws Exception {

        System.out.println(1000%1000);
//        DataBaseUtils.dropTable("test");

    }

}