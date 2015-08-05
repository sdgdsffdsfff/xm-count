package com.xiaomi.count;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session超时验证
 * Created by lijie on 2015-06-25.
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        if (url.contains("/resources/")) return true;
        Object object = httpServletRequest.getSession().getAttribute(Constant.CURRENT_USER);
        if (object == null) {
            httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest, httpServletResponse);
        }
        return object != null;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
