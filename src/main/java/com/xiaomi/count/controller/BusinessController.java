package com.xiaomi.count.controller;

import com.xiaomi.count.bean.PageResults;
import com.xiaomi.count.model.Business;
import com.xiaomi.count.service.BusinessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 业务分类
 * Created by lijie on 2015-07-08.
 */
@Controller
@RequestMapping(value = "/bz")
public class BusinessController extends BaseController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {

        String hql = "from Business order by orderNo";
        PageResults<Business> businessPageResults = businessService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize());

        ModelMap modelMap = getModelMap();
        modelMap.put("pagenumber", getPagenumber());
        modelMap.put("pagecount", businessPageResults.getPagecount());

        modelMap.put("list", businessPageResults.getResults());

        return new ModelAndView("business", modelMap);
    }

    @RequestMapping(value = "/add")
    public String add(Business business) {
        businessService.saveOrUpdate(business);
        return "redirect:/bz/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(String key) {
        if (StringUtils.isNotEmpty(key)) {
            businessService.deleteById(Integer.valueOf(key));
        }
        return "redirect:/bz/list";
    }

}
