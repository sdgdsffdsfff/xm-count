package com.xiaomi.count.service;

import com.xiaomi.count.Constant;
import com.xiaomi.count.dao.TaskDao;
import com.xiaomi.count.model.Task;
import com.xiaomi.count.model.Timer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务
 * Created by lijie on 2015-07-08.
 */
@Service
public class TaskService extends BaseService<Task> {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TimerService timerService;

    public void updateTask(Task task, String timer, MultipartFile file, String path) {

        //当前任务的主键值 用于下面设置表名使用
        Integer key = task.getId();

        //如果是更新操作 先删除设置的定时时间
        if (task.getId() != null) {
            timerService.queryHql("delete from Timer t where t.task.id= ?", task.getId());
        }

        //设置当前任务结果存储的临时表的表名
        task.setTable(task.getBusinessId() + "_" + key);

        //设置当前任务的定时时间 如果是定时任务
        if (Constant.delay.equals(task.getExecute())) {
            List<Timer> timerList = new ArrayList<Timer>();
            if (StringUtils.isNotEmpty(timer)) {
                for (String time : timer.split(",")) {
                    timerList.add(new Timer(time, task));
                }
            }
            task.setTimerList(timerList);
        }

        //判断当前任务是sql任务还是python任务
        if (Constant.PYTHON.equals(task.getType())) {
            task.setSql(null);
            //上传python脚本文件
            String originalName = file.getOriginalFilename();

            if (StringUtils.isNotEmpty(originalName)) {

                path = path + "/" + key + "/" + originalName;
                File pythonFile = new File(path);

                boolean exists = pythonFile.exists();

                if (!exists) {
                    exists = pythonFile.mkdirs();
                }

                if (exists) {
                    try {
                        file.transferTo(pythonFile);
                    } catch (IOException e) {
                        task.setUrl(null);
                    }
                }

                task.setUrl(path);
            }

        } else {
            task.setUrl(null);
        }

        taskDao.saveOrUpdate(task);
    }

}
