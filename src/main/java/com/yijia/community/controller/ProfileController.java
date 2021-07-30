package com.yijia.community.controller;

import com.yijia.community.domain.User;
import com.yijia.community.dto.QuestionPageInfoDto;
import com.yijia.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {



    @Resource
    QuestionService questionService;


    @GetMapping("profile/{section}")
   public String  profile(@PathVariable(value = "section", required = false) String section,
                          @RequestParam(value = "page",required = false) Integer page,
                          HttpSession session,
                          Model model){




        if (session.getAttribute("user")==null ){
            return "redirect:/";
        }
        if(section ==null ||("").equals(section)){

            section="questions";
        }

        if(page==null ||page<=0){
            page=1;
        }


        model.addAttribute("sectionName",section);
        QuestionPageInfoDto questionPageInfoDto =null;
        switch (section){

            case "questions":{

                String id=((User)session.getAttribute("user")).getAccount_id();

                questionPageInfoDto = questionService.selectQuestionsAndPage(page,1,id);
                model.addAttribute("questionPageInfoDto",questionPageInfoDto);

            }
            case "follows" :{}


        }



        return "profile";
    }
}
