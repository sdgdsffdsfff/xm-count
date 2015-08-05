package com.xiaomi.count.controller;

import com.xiaomi.count.bean.PageResults;
import com.xiaomi.count.model.History;
import com.xiaomi.count.service.HistoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 统计历史记录
 * Created by lijie on 2015-07-16.
 */
@Controller
@RequestMapping(value = "/his")
public class HistoryController extends BaseController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/list")
    public ModelAndView list(HttpServletRequest request) throws ParseException {

        String name = request.getParameter("name");
        String start = request.getParameter("start");
        String end = request.getParameter("end");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ModelMap modelMap = getModelMap();

        PageResults<History> historyPageResults;

        if (StringUtils.isNotEmpty(name) && (StringUtils.isEmpty(start) || StringUtils.isEmpty(end))) {
            String hql = "from History h where h.task.name=? order by h.task.id,h.start desc";
            historyPageResults = historyService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize(), name);
        } else if (StringUtils.isEmpty(name) && StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            String hql = "from History h where h.start>=? and h.start<=? order by h.task.id,h.start desc";
            Date startDate = simpleDateFormat.parse(start + " 00:00:00");
            Date endDate = simpleDateFormat.parse(end + " 23:59:59");
            historyPageResults = historyService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize(), startDate, endDate);
        } else if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            String hql = "from History h where h.start>=? and h.start<=? and h.task.name=? order by h.task.id,h.start desc";
            Date startDate = simpleDateFormat.parse(start + " 00:00:00");
            Date endDate = simpleDateFormat.parse(end + " 23:59:59");
            historyPageResults = historyService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize(), startDate, endDate, name);
        } else {
            String hql = "from History h order by h.task.id,h.start desc";
            historyPageResults = historyService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize());
        }


        modelMap.put("historyList", historyPageResults.getResults());

        modelMap.put("pagenumber", getPagenumber());
        modelMap.put("pagecount", historyPageResults.getPagecount());

        modelMap.put("name", name);
        modelMap.put("start", start);
        modelMap.put("end", end);

        return new ModelAndView("history", modelMap);
    }

}
