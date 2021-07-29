package com.yijia.community.controller;


import com.yijia.community.dto.PageDto;

import com.yijia.community.service.QuestionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;


@Controller
public class IndexController {


    @Resource
    QuestionService questionService;
    @GetMapping("/")
    public String index( @RequestParam(value = "page" ,required = false) Integer  page, Model model){


        if (page==null){//检验
            page=1;
        }

        //查询所有的问题
        PageDto pageDto=questionService.selectQuestionsAndPage(page,1);
        //将问题填入model
        model.addAttribute("pageDto", pageDto);

        return "index";
    }
}
