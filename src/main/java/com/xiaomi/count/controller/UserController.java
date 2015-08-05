package com.xiaomi.count.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.count.Constant;
import com.xiaomi.count.bean.PageResults;
import com.xiaomi.count.model.Rs;
import com.xiaomi.count.model.User;
import com.xiaomi.count.service.UserService;
import com.xiaomi.count.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用户管理
 * Created by lijie on 2015-07-08.
 */
@Controller
@RequestMapping(value = "/usr")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/resources/login")
    public String login(HttpServletRequest request, User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return "redirect:/";
        }
        User entity = userService.getByHQL("from User where username=?", user.getUsername());
        if (entity == null) {
            return "redirect:/";
        }
        String userPassword = MD5Util.getStrMd5String(user.getPassword());
        if (!userPassword.equals(entity.getPassword())) {
            return "redirect:/";
        }
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CURRENT_USER, entity);

        if (user.getUsername().equals(Constant.SUPER)) {
            session.setAttribute(Constant.IS_SUPER, true);
            return "redirect:/bz/list";
        } else {
            session.setAttribute(Constant.IS_SUPER, false);

            Set<Rs> rsSet = entity.getRsSet();
            if (rsSet.size() > 0) {
                Rs rs = rsSet.iterator().next();
                return "redirect:" + rs.getUrl();
            }

        }

        return "redirect:/";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.CURRENT_USER);
        return "redirect:/";
    }

    @RequestMapping(value = "/list")
    public ModelAndView list() {

        ModelMap modelMap = getModelMap();

        String hql = "from User";
        PageResults<User> userPageResults = userService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize());

        modelMap.put("userList", userPageResults.getResults());

        modelMap.put("pagenumber", getPagenumber());
        modelMap.put("pagecount", userPageResults.getPagecount());

        return new ModelAndView("usr", modelMap);
    }

    @RequestMapping(value = "/add")
    public String add(User user) {
        User entity;
        if (user.getId() != null) {
            entity = userService.get(user.getId());
            entity.setUsername(user.getUsername());
            entity.setNickname(user.getNickname());
        } else {
            entity = user;
            entity.setPassword(MD5Util.getStrMd5String(entity.getPassword()));
        }
        userService.saveOrUpdate(entity);
        return "redirect:/usr/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            userService.deleteById(Integer.valueOf(ids));
        }
        return "redirect:/usr/list";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(String oldpassword, String newpassword, String confirmpassword, String id) {
        if (StringUtils.isNotEmpty(id)) {
            User user = userService.get(Integer.valueOf(id));
            if (!(MD5Util.getStrMd5String(oldpassword)).equals(user.getPassword())) {
                return "error";
            }
            user.setPassword(MD5Util.getStrMd5String(newpassword));
            userService.saveOrUpdate(user);
        }
        return "success";
    }

    @RequestMapping(value = "/auths")
    public String authority(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String[] rs = request.getParameterValues("rs");
            Map<String, String> bz = new HashMap<String, String>();
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (!key.contains("bz")) continue;
                String[] values = request.getParameterValues(key);
                if (values != null && values.length > 0) {
                    String keyValue = ",";
                    for (String value : values) {
                        keyValue += value + ",";
                    }
                    bz.put(key.replace("bz", ""), keyValue);
                }
            }

            User user = userService.get(Integer.valueOf(id));
            userService.saveAllAuths(user, rs, bz);
        } catch (Exception e) {

        }
        return "redirect:/usr/list";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get(String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = userService.get(Integer.valueOf(id));
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
        }
        return null;
    }

}
