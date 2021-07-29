package com.yijia.community.controller;


import com.yijia.community.domain.Question;
import com.yijia.community.domain.User;
import com.yijia.community.mapper.QuestionMapper;
import com.yijia.community.mapper.UserMapper;
import com.yijia.community.service.QuestionService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {




    @Resource
    QuestionService questionService;
    @GetMapping("/publish")
    public String publish(HttpServletRequest request){

        return "/publish";
    }

    @PostMapping("/publish")
    public  String publish(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("tags") String tags,
                           HttpServletRequest request,
                           Model model
                           ){




        //在后面的页面跳转中,保存之前的内容.
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tags",tags);




        //验证内容
        if(title==null || title.equals("")){

            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if(description==null || title.equals("")){

            model.addAttribute("error","问题描述不能为空");
            return "/publish";
        }
        if(description ==null || title.equals("")){

            model.addAttribute("error","标签不能为空");
            return "/publish";
        }



        User user=(User) request.getSession().getAttribute("user");

        //将查找的用户信息放入session中
        if(user==null){

            model.addAttribute("error","你还没有登录");
        }else{

            model.addAttribute("success","发布成功");


            //存入发布的问题
            Question question =new Question();
            long time=System.currentTimeMillis();
            question.setCreate_time(time);
            question.setModify_time(time);
            question.setComment_count(0);
            question.setCreator(user.getId());
            question.setLike_count(0);
            question.setTags(tags);
            question.setDescription(description);
            question.setTitle(title);

            questionService.insert(question);


        }



        return "/publish";

    }
}
