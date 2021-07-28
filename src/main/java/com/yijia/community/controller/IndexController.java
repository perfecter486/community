package com.yijia.community.controller;


import com.yijia.community.domain.User;
import com.yijia.community.mapper.UserMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.CollationKey;

@Controller
public class IndexController {


    @Resource
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){


        Cookie[] cookies=request.getCookies();
        User user=null;


        for (Cookie cookie :cookies){//利用cookie中的token查找用户信息

            if(cookie.getName().equals("token")){
               user= userMapper.selectByToken(cookie.getValue());
               break;
            }
        }

        //将查找的用户信息放入session中

        request.getSession().setAttribute("user",user);

        return "index";
    }
}
