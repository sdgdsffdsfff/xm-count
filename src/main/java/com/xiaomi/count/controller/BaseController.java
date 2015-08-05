package com.xiaomi.count.controller;

import com.xiaomi.count.Constant;
import com.xiaomi.count.model.Business;
import com.xiaomi.count.model.Rs;
import com.xiaomi.count.model.User;
import com.xiaomi.count.model.UserBz;
import com.xiaomi.count.service.BusinessService;
import com.xiaomi.count.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * 通用controller
 * Created by lijie on 2015-06-11.
 */
public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RsService rsService;
    @Autowired
    private BusinessService businessService;

    public String getStrValue(String key) {
        return request.getParameter(key);
    }

    public String getStrValue(String key, HttpServletRequest request) {
        return request.getParameter(key);
    }

    public String getStrValues(String key) {

        String[] values = request.getParameterValues(key);

        if (values != null) {
            String result = "";
            for (String val : values) {
                result += val + ",";
            }
            if (result.contains(",")) {
                result = result.substring(0, result.lastIndexOf(","));
            }

            return result;

        }

        return null;
    }

    public String getStrValues(String key, HttpServletRequest request) {

        String[] values = request.getParameterValues(key);

        if (values != null) {
            String result = "";
            for (String val : values) {
                result += val + ",";
            }
            if (result.contains(",")) {
                result = result.substring(0, result.lastIndexOf(","));
            }

            return result;

        }

        return null;
    }


    public Integer getIntValue(String key) {
        String strValue = request.getParameter(key);
        if (strValue == null || "".equals(strValue.trim())) {
            return null;
        }
        return Integer.valueOf(strValue);
    }

    public Integer getIntValue(String key, HttpServletRequest request) {
        String strValue = request.getParameter(key);
        if (strValue == null || "".equals(strValue.trim())) {
            return null;
        }
        return Integer.valueOf(strValue);
    }

    public Double getDoubleValue(String key) {
        String strValue = request.getParameter(key);
        if (strValue == null || "".equals(strValue.trim())) {
            return null;
        }
        return Double.valueOf(strValue);
    }

    public Double getDoubleValue(String key, HttpServletRequest request) {
        String strValue = request.getParameter(key);
        if (strValue == null || "".equals(strValue.trim())) {
            return null;
        }
        return Double.valueOf(strValue);
    }

    public int getPagenumber() {
        Object object = request.getParameter("pagenumber");
        if (object == null) {
            return 1;
        }
        return Integer.valueOf(object.toString());
    }


    public int getPagesize() {
        Object object = request.getParameter("pagesize");
        if (object == null) {
            return 15;
        }
        return Integer.valueOf(object.toString());
    }

    public String getSearchFiledValue() {
        return request.getParameter("namefield");
    }


    /**
     * 页面初始化 栏目显示 和按钮显示
     * 根据用户权限显示
     *
     * @return
     */
    public ModelMap getModelMap() {

        HttpSession httpSession = request.getSession();

        User user = (User) httpSession.getAttribute(Constant.CURRENT_USER);

        boolean isSuper = httpSession.getAttribute(Constant.IS_SUPER).toString().equals("true");

        ModelMap modelMap = new ModelMap();

        if (isSuper) {
            List<Rs> rsList = rsService.getListByHQL("from Rs order by orderNo");

            modelMap.put("rsList", rsList);

            List<Business> businessList = businessService.getListByHQL("from Business order by orderNo");

            modelMap.put("businessList", businessList);

            if (businessList.size() > 0) {
                modelMap.put("bid", businessList.get(0).getId());
                modelMap.put("code", ",C,U,D,R,");
            }

        } else {

            modelMap.put("rsList", user.getRsSet());

            /*
            * UserBz 是对Business 的包装 含有这个类型的可以操作代码
            * */
            Set<UserBz> userBzSet = user.getUserBzSet();

            modelMap.put("businessList", userBzSet);

            if (userBzSet.size() > 0) {
                UserBz userBz = userBzSet.iterator().next();
                modelMap.put("bid", userBz.getId());
                modelMap.put("code", userBz.getCode());
            }

        }

        return modelMap;

    }

    public HttpServletRequest getRequest() {
        return request;
    }

}
