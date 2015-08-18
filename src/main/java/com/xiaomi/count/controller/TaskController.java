package com.xiaomi.count.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.count.Constant;
import com.xiaomi.count.bean.AgentSeeker;
import com.xiaomi.count.bean.IPSeeker;
import com.xiaomi.count.bean.PageResults;
import com.xiaomi.count.model.Agent;
import com.xiaomi.count.model.History;
import com.xiaomi.count.model.Region;
import com.xiaomi.count.model.Task;
import com.xiaomi.count.scheduling.JythonDataTask;
import com.xiaomi.count.scheduling.SqlDataTask;
import com.xiaomi.count.service.*;
import com.xiaomi.count.util.DataBaseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * 任务
 * Created by lijie on 2015-07-08.
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TempTableService tempTableService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private BootService bootService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IPSeeker ipSeeker;
    @Autowired
    private AgentSeeker agentSeeker;

    @RequestMapping(value = "/list")
    public ModelAndView list(String bid) {

        ModelMap modelMap = getModelMap();

        if (StringUtils.isEmpty(bid)) {
            bid = String.valueOf(modelMap.get("bid"));
        }

        modelMap.put("bid", bid);

        if (StringUtils.isNotEmpty(bid)) {

            String hql = "from Task where businessId=?";
            PageResults<Task> taskPageResults = taskService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize(), Integer.valueOf(bid));//统计分类下面的统计项 页面初始化时候 初始化第一个分类下面的 如果有bid 初始化bid下面的


            modelMap.put("taskList", taskPageResults.getResults());//任务

            modelMap.put("pagenumber", getPagenumber());
            modelMap.put("pagecount", taskPageResults.getPagecount());
        }

        return new ModelAndView("task", modelMap);
    }


    @RequestMapping(value = "/view")
    public ModelAndView view(HttpServletRequest request) {

        String bid = request.getParameter("bid"), tid = request.getParameter("tid");

        ModelMap modelMap = getModelMap();

        if (StringUtils.isEmpty(bid)) {
            bid = String.valueOf(modelMap.get("bid"));
        }

        modelMap.put("bid", bid);//分类

        String hql = "from Task where businessId=? order by orderNo";
        List<Task> taskList = taskService.getListByHQL(hql, Integer.valueOf(bid));
        modelMap.put("taskList", taskList);

        if (StringUtils.isEmpty(tid)) {
            if (taskList.size() > 0) {
                tid = taskList.get(0).getId().toString();
            }
        }

        if (StringUtils.isNotEmpty(tid)) {

            modelMap.put("tid", tid);

            Task task = taskService.get(Integer.valueOf(tid));
            String table = task.getTable();
            Double plus = task.getPlus();


            //这里判断当前任务的临时表是否存在
            if (!tempTableService.existTable(table)) {
                modelMap.put("pagenumber", 0);
                modelMap.put("pagecount", 0);
                return new ModelAndView("view", modelMap);
            }


            String ip = request.getParameter("ip");
            String agentid = request.getParameter("agentid");
            String ver = request.getParameter("ver");
            String start = request.getParameter("start");
            String end = request.getParameter("end");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from ").append(table).append(" where 1=1");

//            //地区查找
            if (StringUtils.isNotEmpty(ip)) {
                String text = regionService.get(Integer.valueOf(ip)).getText();
                if (!Constant.ALL.equals(text)) {
                    stringBuilder.append(" and ").append(Constant.CN_IP).append(" like'%").append(text).append("%'");
                }
            }

            //代理商条件查找
            if (StringUtils.isNotEmpty(agentid)) {
                String text = agentService.get(Integer.valueOf(agentid)).getText();
                if (!Constant.ALL.equals(text)) {
                    stringBuilder.append(" and ").append(Constant.CN_AGENT).append("='").append(text).append("'");
                }
            }

            //版本号
            if (StringUtils.isNotEmpty(ver)) {
                stringBuilder.append(" and ").append(Constant.CN_VER).append("='").append(ver.trim()).append("'");
            }


            //时间
            if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
                stringBuilder.append(" and ").append(Constant.CN_TIME).append(">='").append(start).append(" 00:00:00'");
                stringBuilder.append(" and ").append(Constant.CN_TIME).append("<='").append(end).append(" 23:59:59'");

            }



            modelMap.put("ip", ip);
            modelMap.put("agentid", agentid);
            modelMap.put("ver", ver);
            modelMap.put("start", start);
            modelMap.put("end", end);


            List<String> columnList = tempTableService.queryForMetaData(table);

            modelMap.put("hlist", columnList);

            boolean hasIp = columnList.contains(Constant.CN_IP);
            boolean hasAgent = columnList.contains(Constant.CN_AGENT);
            boolean hasVer = columnList.contains(Constant.CN_VER);
            boolean hasTime = columnList.contains(Constant.CN_TIME);


            if(hasTime){
                stringBuilder.append(" order by "+Constant.CN_TIME+" desc ");
            }

            stringBuilder.append(" limit ").append((getPagenumber() - 1) * getPagesize()).append(",").append(getPagesize());


            List<Map<String, Object>> list = tempTableService.queryForList(stringBuilder.toString());

            int totalCount = tempTableService.queryForInt(stringBuilder.toString());




            if (hasIp || hasAgent || hasVer || hasTime) {
                modelMap.put("toolbar", "1");//有搜索工具条
            }
            if (hasIp) {
                modelMap.put("ipbar", "1");
            }
            if (hasAgent) {
                modelMap.put("agentbar", "1");
            }
            if (hasVer) {
                modelMap.put("verbar", "1");
            }
            if (hasTime) {
                modelMap.put("timebar", "1");
            }

            if (plus != null) {
                modelMap.put("plus", totalCount * plus);
            }

            modelMap.put("pagenumber", getPagenumber());
            modelMap.put("pagecount", totalCount % getPagesize() == 0 ? totalCount / getPagesize() : totalCount / getPagesize() + 1);

            modelMap.put("list", list);

        }
        return new ModelAndView("view", modelMap);
    }


    @RequestMapping(value = "/testView")
    public ModelAndView testView(HttpServletRequest request) {

        Task task;

        String tid = request.getParameter("tid");
        String name = request.getParameter("name");

        if (StringUtils.isEmpty(tid)) {
            try {
                name = URLDecoder.decode(name, "utf8");
            } catch (UnsupportedEncodingException e) {
                name = "";
            }

            String hql = "from Task where name=?";

            task = taskService.getByHQL(hql, name);
        } else {
            task = taskService.get(Integer.valueOf(tid));
        }

        ModelMap modelMap = new ModelMap();

        if (task != null) {

            modelMap.put("tid", task.getId());

            //测试数据保存在测试表
            String table = task.getTable() + "_";
            Double plus = task.getPlus();

            String ip = request.getParameter("ip");
            String agentid = request.getParameter("agentid");
            String ver = request.getParameter("ver");
            String start = request.getParameter("start");
            String end = request.getParameter("end");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from ").append(table).append(" where 1=1");

            if (StringUtils.isNotEmpty(ip)) {
                String text = regionService.get(Integer.valueOf(ip)).getText();
                if (!Constant.ALL.equals(text)) {
                    stringBuilder.append(" and ").append(Constant.CN_IP).append(" like'%").append(text).append("%'");
                }
            }

            //代理商条件查找
            if (StringUtils.isNotEmpty(agentid)) {
                String text = agentService.get(Integer.valueOf(agentid)).getText();
                if (!Constant.ALL.equals(text)) {
                    stringBuilder.append(" and ").append(Constant.CN_AGENT).append("='").append(text).append("'");
                }
            }

            //版本号
            if (StringUtils.isNotEmpty(ver)) {
                stringBuilder.append(" and ").append(Constant.CN_VER).append("='").append(ver.trim()).append("'");
            }

            //时间
            if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
                stringBuilder.append(" and ").append(Constant.CN_TIME).append(">='").append(start).append(" 00:00:00'");
                stringBuilder.append(" and ").append(Constant.CN_TIME).append("<='").append(end).append(" 23:59:59'");

            }

            modelMap.put("ip", ip);
            modelMap.put("agentid", agentid);
            modelMap.put("ver", ver);
            modelMap.put("start", start);
            modelMap.put("end", end);

            stringBuilder.append(" limit ").append((getPagenumber() - 1) * getPagesize()).append(",").append(getPagesize());

            List<Map<String, Object>> list = tempTableService.queryForList(stringBuilder.toString());

            int totalCount = tempTableService.queryForInt(stringBuilder.toString());

            List<String> columnList = tempTableService.queryForMetaData(table);

            modelMap.put("hlist", columnList);

            boolean hasIp = columnList.contains(Constant.IP);
            boolean hasAgent = columnList.contains(Constant.AGENT);
            boolean hasVer = columnList.contains(Constant.VER);
            boolean hasTime = columnList.contains(Constant.TIME);


            if (hasIp || hasAgent || hasVer || hasTime) {
                modelMap.put("toolbar", "1");//有搜索工具条
            }
            if (hasIp) {
                modelMap.put("ipbar", "1");
            }
            if (hasAgent) {
                modelMap.put("agentbar", "1");
            }
            if (hasVer) {
                modelMap.put("verbar", "1");
            }
            if (hasTime) {
                modelMap.put("timebar", "1");
            }

            if (plus != null) {
                modelMap.put("plus", totalCount * plus);
            }

            modelMap.put("pagenumber", getPagenumber());
            modelMap.put("pagecount", totalCount % getPagesize() == 0 ? totalCount / getPagesize() : totalCount / getPagesize() + 1);

            modelMap.put("list", list);

        }
        return new ModelAndView("test", modelMap);
    }

    @RequestMapping(value = "/check")
    @ResponseBody
    public String checkTable(String name) {

        Task task;
        String table;

        if (StringUtils.isNotEmpty(name)) {
            String hql = "from Task where name=?";

            task = taskService.getByHQL(hql, name);

            table = task != null ? task.getTable() + "_" : "";

            if (tempTableService.existTable(table)) {
                return "success";
            }

        }

        return "failure";
    }


    @RequestMapping(value = "/wait")
    public ModelAndView wait(HttpServletRequest request) {

        ModelMap modelMap = new ModelMap();

        try {
            String name = URLDecoder.decode(request.getParameter("name"), "utf8");
            modelMap.put("name", name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ModelAndView("wait", modelMap);
    }

    @RequestMapping(value = "/add")
    public String add(MultipartFile file, HttpServletRequest request) {

        //得到表单参数
        Task task = new Task();
        task.setId(getIntValue("id", request));
        task.setBusinessId(getIntValue("businessId", request));
        task.setTest(getStrValue("test", request));
        task.setName(getStrValue("name", request));
        task.setType(getStrValue("type", request));
        task.setSql(getStrValue("sql", request));
        task.setPlus(getDoubleValue("plus", request));
        task.setOrderNo(getIntValue("orderNo", request));
        task.setExecute(getStrValue("execute", request));
        task.setModel(getStrValue("model", request));
        task.setState(getStrValue("state", request));
        task.setUrl(getStrValue("url", request));

        taskService.saveOrUpdate(task);

        //得到定时时间
        String timer = getStrValues("timer", request);

        //保存或者更新
        String path = request.getSession().getServletContext().getRealPath(Constant.PATH);
        taskService.updateTask(task, timer, file, path);

        //即时任务 任务在启动状态 立刻执行一次
        //提交的测试请求
        boolean conditon1 = Constant.Immediately.equals(task.getExecute()) && Constant.state.equals(task.getState());
        boolean conditon2 = "0".equals(task.getTest());
        if (conditon1 || conditon2) {

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

        return "redirect:/task/list?bid=" + task.getBusinessId();
    }

    @RequestMapping(value = "/delete")
    public String delete(String key, String bid) {
        System.out.println(getStrValue("key"));
        if (StringUtils.isNotEmpty(key)) {
            Task task = taskService.get(Integer.valueOf(key));
            DataBaseUtils.dropTable(task.getTable());
            taskService.delete(task);
        }
        return "redirect:/task/list?bid=" + bid;
    }

    @RequestMapping(value = "/ajax")
    @ResponseBody
    public String getById(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Task task = taskService.get(Integer.valueOf(id));
            return objectMapper.writeValueAsString(task);
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value = "/getWaitState")
    @ResponseBody
    public String getWaitState(String taskId) {

        String result_data = "0";

        //先判断表存在不
        if (StringUtils.isNotEmpty(taskId)) {
            Task task = taskService.get(Integer.valueOf(taskId));

            String table = task.getTable();
            String execute = task.getExecute();

            String sql = "select * from history where task_id=? order by start desc limit 0,1";
            History history = historyService.getBySQL(sql, Integer.valueOf(taskId));

            boolean hasTable = tempTableService.existTable(table);

            if (Constant.Immediately.equals(execute)) {
                if (hasTable) {
                    if (history == null) {
                        //数据正在计算
                        result_data = "1";
                    } else {
                        if ("2".equals(history.getState())) {
                            //数据正在计算
                            result_data = "1";
                        }
                    }
                } else {
                    //数据正在计算
                    result_data = "1";
                }

            } else {
                if (!hasTable) {
                    //定时任务还没开始
                    result_data = "2";
                }

            }

        }

//        try {
//            if (StringUtils.isNotEmpty(taskId)) {
//
//                return objectMapper.writeValueAsString(history);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return result_data;
    }

    @RequestMapping(value = "/getAgent")
    @ResponseBody
    public String getAgent() {
        String hql = "from Agent where pid=?";
        Agent agent = agentService.getByHQL(hql, -1);

        int id = agent.getId();
        String text = agent.getText();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[{");
        stringBuilder.append("\"id\":" + id + ",");
        stringBuilder.append("\"text\":\"" + text + "\",");

        List<Agent> children = agent.getChildren();
        stringBuilder.append("\"children\":[");

        for (int i = 0; i < children.size(); i++) {
            agent = children.get(i);
            id = agent.getId();
            text = agent.getText();

            stringBuilder.append("{\"id\":" + id + ",");
            stringBuilder.append("\"text\":\"" + text + "\"");

            if (i == children.size() - 1) {
                stringBuilder.append("}");
            } else {
                stringBuilder.append("},");
            }

        }

        stringBuilder.append("]");
        stringBuilder.append("}]");

        return stringBuilder.toString();

    }

    @RequestMapping(value = "/getRegion")
    @ResponseBody
    public String getRegion() {
        String hql = "from Region where pid=?";
        Region region = regionService.getByHQL(hql, -1);

        int id = region.getId();
        String text = region.getText();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[{");
        stringBuilder.append("\"id\":" + id + ",");
        stringBuilder.append("\"text\":\"" + text + "\",");

        List<Region> children = region.getChildren();
        stringBuilder.append(this.parseTreeJson(children));

        stringBuilder.append("}]");

        return stringBuilder.toString();
    }

    private String parseTreeJson(List<Region> regionList) {

        StringBuilder stringBuilder = new StringBuilder();

        if (regionList.size() > 0) {
            stringBuilder.append("\"children\":[");

            for (int i = 0; i < regionList.size(); i++) {
                Region region = regionList.get(i);
                int id = region.getId();
                String text = region.getText();
                String state = region.getState();

                stringBuilder.append("{\"id\":" + id + ",");
                stringBuilder.append("\"text\":\"" + text + "\"");
                if (StringUtils.isNotEmpty(state)) {
                    stringBuilder.append(",");
                    stringBuilder.append("\"state\":\"closed\"");
                }

                String children = parseTreeJson(region.getChildren());
                if (StringUtils.isNotEmpty(children)) {
                    stringBuilder.append(",");
                    stringBuilder.append(children);
                }
                if (i == regionList.size() - 1) {
                    stringBuilder.append("}");
                } else {
                    stringBuilder.append("},");
                }
            }

            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }

}
