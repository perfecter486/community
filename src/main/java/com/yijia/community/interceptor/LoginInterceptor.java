package com.yijia.community.interceptor;

import com.yijia.community.domain.User;
import com.yijia.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor  implements HandlerInterceptor {


    @Resource
   UserMapper userMapper ;




    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Cookie[] cookies=request.getCookies();
        User user=null;
        System.out.println("aaaa");
        if(cookies!=null) {
            for (Cookie cookie : cookies) {//利用cookie中的token查找用户信息

                if (cookie.getName().equals("token")) {
                    user = userMapper.selectByToken(cookie.getValue());
                    break;
                }
            }
        }

        //将查找的用户信息放入session中

        request.getSession().setAttribute("user",user);


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
