package com.xiaomi.count.service;

import com.xiaomi.count.model.History;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lijie on 2015-08-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BaseServiceTest {

    @Autowired
    private HistoryService historyService;


    @Test
    public void fuckTest(){
        String sql = "SELECT * FROM history WHERE state=? AND times<? GROUP BY task_id";
        List<History> historyList = historyService.getListBySQL(sql, "0", 3);
    }

}