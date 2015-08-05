package com.xiaomi.count.scheduling;

import com.xiaomi.count.Constant;
import com.xiaomi.count.bean.AgentSeeker;
import com.xiaomi.count.bean.IPSeeker;
import com.xiaomi.count.model.History;
import com.xiaomi.count.model.Task;
import com.xiaomi.count.model.Timer;
import com.xiaomi.count.service.BootService;
import com.xiaomi.count.service.HistoryService;
import com.xiaomi.count.service.TaskService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 更新总量表,找出每日新增的localId写入总量表  更新每日登陆量到登陆记录表
 * Created by lijie on 2015-07-07.
 */
public class QuartzJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(QuartzJob.class);

    private TaskService taskService;

    private BootService bootService;

    private IPSeeker ipSeeker;

    private AgentSeeker agentSeeker;

    private HistoryService historyService;

    private ThreadPoolTaskExecutor threadPool;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SimpleDateFormat m = new SimpleDateFormat("mm");
        SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String strDate = hm.format(date);

        //执行定时任务
        String hql = "from Task where state=? and execute=? order by orderNo";
        List<Task> taskList = taskService.getListByHQL(hql, "1", "2");

        for (final Task task : taskList) {
            List<Timer> timerList = task.getTimerList();
            for (Timer timer : timerList) {
                if (strDate.equals(timer.getTime())) {
                    //sql任务
                    if (Constant.SQL.equals(task.getType())) {
                        SqlDataTask dataTask = new SqlDataTask(task, bootService, historyService, ipSeeker, agentSeeker, null);
                        threadPool.execute(dataTask);
                    }
                    //jython任务
                    if (Constant.PYTHON.equals(task.getType())) {
                        JythonDataTask jythonDataTask = new JythonDataTask(task, historyService, null);
                        threadPool.execute(jythonDataTask);
                    }

                }
            }
        }

        //执行失败过后重试 没15分钟重试一次 15分钟为jdbc查询的超时时间 保证每个任务不会第一次重试没执行完 执行第二次重试 重试次数为3次
        String minute = m.format(date);
        if ("00".equals(minute) || "30".equals(minute)) {
            String sql = "SELECT * FROM history WHERE state=? AND times<? GROUP BY task_id";
            List<History> historyList = historyService.getListBySQL(sql, "0", 3);

            for (History history : historyList) {
                Task task = history.getTask();

                if (Constant.SQL.equals(task.getType())) {
                    SqlDataTask dataTask = new SqlDataTask(task, bootService, historyService, ipSeeker, agentSeeker, history);
                    threadPool.execute(dataTask);
                }

                if (Constant.PYTHON.equals(task.getType())) {
                    JythonDataTask jythonDataTask = new JythonDataTask(task, historyService, null);
                    threadPool.execute(jythonDataTask);
                }

            }
        }

        //跟踪线程池中在执行的任务
        LOG.error("线程池中正在执行的任务数:" + threadPool.getActiveCount());

    }


    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public BootService getBootService() {
        return bootService;
    }

    public void setBootService(BootService bootService) {
        this.bootService = bootService;
    }

    public IPSeeker getIpSeeker() {
        return ipSeeker;
    }

    public void setIpSeeker(IPSeeker ipSeeker) {
        this.ipSeeker = ipSeeker;
    }

    public AgentSeeker getAgentSeeker() {
        return agentSeeker;
    }

    public void setAgentSeeker(AgentSeeker agentSeeker) {
        this.agentSeeker = agentSeeker;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;
    }
}
