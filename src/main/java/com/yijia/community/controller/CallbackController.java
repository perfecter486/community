package com.yijia.community.controller;

import com.yijia.community.domain.AccessTokenParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CallbackController {


    @Resource
    RestTemplate restTemplate;
    @Resource
    AccessTokenParam accessTokenParam;//获取token时带上的参数
    @Value("${github.token-url}")
    String tokenUrl;//获取token的url
    @Value("${github.access-token-url}")
    String accessTokenUrl;//认证的url
    @GetMapping("/callback")
    public String callback(String error,String error_uri,String error_description,String code ){


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


       return  result;

    }


}
