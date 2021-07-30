package com.yijia.community.controller;

import com.alibaba.fastjson.JSON;
import com.yijia.community.domain.User;
import com.yijia.community.dto.AccessTokenParam;
import com.yijia.community.dto.GithubUser;
import com.yijia.community.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
public class CallbackController {


    @Resource
    RestTemplate restTemplate;
    @Resource
    AccessTokenParam accessTokenParam;//获取token时带上的参数
    @Value("${github.token-url}")
    String tokenUrl;//获取token的url
    @Value("${github.access-token-url}")
    String accessTokenUrl;//认证的url


    @Resource
    UserService userService;
    @GetMapping("/callback")
    public String callback(String error, String error_uri, String error_description, String code , HttpSession session, HttpServletResponse httpResponse){


        accessTokenParam.setCode(code);
        System.out.println(accessTokenParam);
        //带着code等一些参数去获取token
         HttpEntity<String> response= restTemplate.postForEntity(tokenUrl,accessTokenParam,String.class);
         String result =response.getBody();

         String token=result.split("&")[0].split("=")[1];
        System.out.println(token);

        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","token "+token);
         HttpEntity<String> entity =new HttpEntity<String>(httpHeaders);
        //通过token得到认证
         response =restTemplate.exchange(accessTokenUrl, HttpMethod.GET,entity,String.class);
         result= response.getBody();
        GithubUser githubUser=null;

        System.out.println(result);


        try {
            githubUser=JSON.parseObject(result,GithubUser.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        User user=new User();
        if(githubUser!=null){

            //持久化用户信息
            Long time=System.currentTimeMillis();
            user.setCreate_time(time);
            user.setModify_time(time);
            user.setToken(UUID.randomUUID().toString().substring(0,36));
            user.setAccount_id(githubUser.getId()+"");
            user.setName(githubUser.getLogin());
            user.setAvatar_url(githubUser.getAvatar_url());
            userService.insert(user);

        }

        httpResponse.addCookie(new Cookie("token",user.getToken()));


        return "redirect:/";

    }


}
