package com.xiaomi.count.scheduling;

import com.xiaomi.count.Constant;
import com.xiaomi.count.model.History;
import com.xiaomi.count.model.Task;
import com.xiaomi.count.service.HistoryService;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.Date;

/**
 * 数据库查询任务写入任务 执行python文件
 * Created by lijie on 2015-07-17.
 */
public class JythonDataTask implements Runnable {

    private Task task;
    private HistoryService historyService;

    private History history;

    public JythonDataTask(Task task, HistoryService historyService, History history) {
        super();
        this.task = task;
        this.historyService = historyService;
        this.history = history;
    }

    public void run() {

        Date date = new Date();

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

        //判断创建测试表 还是正式表
        String table = (Constant.test.equals(task.getTest()) || task.getTest() == null)? task.getTable() : task.getTable() + "_";
        String model = task.getModel();

        //开始执行脚本文件
        //脚本文件的位置
        String url = task.getUrl();
        if (url.contains("/")) {

            try {

                //todo..... 部署到linux后改为对应的地址
                PySystemState sys = Py.getSystemState();
                // 将 Jython 库加入系统的 classpath 中或直接通过这种方式动态引入
                sys.path.add("E:\\jython2.7.0\\Lib");
                sys.path.add("E:\\jython2.7.0\\Lib\\site-packages");

                PythonInterpreter interpreter = new PythonInterpreter();

                // 执行算法所在的 python 文件
                interpreter.execfile(url);

                //得到文件中函数
                PyFunction pyFunction = interpreter.get(Constant.FUNCTION, PyFunction.class);

                //函数参数
                PyString pyStringTable = new PyString(table);
                PyString pyStringModel = new PyString(model);

                //执行函数
                PyObject pyObject = pyFunction.__call__(pyStringTable, pyStringModel);

               // interpreter.close();

                if (!"1".equals(pyObject.toString())) {
                    entity.setError(pyObject.toString());
                    entity.setTimes(entity.getTimes() == null ? 0 : entity.getTimes());
                }

            } catch (Exception e) {
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

}
