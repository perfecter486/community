package com.yijia.community.config;


import com.yijia.community.dto.AccessTokenParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GitHubConfig {


    @Bean
    public RestTemplate restTemplate(){

        return  new RestTemplate();
    }

    @Bean
    @Value("github.access-token-param")
    public AccessTokenParam accessTokenParam(){

        AccessTokenParam accessTokenParam =new AccessTokenParam();

        accessTokenParam.setClient_secret("f67208ebb3fa4671a4545f252cc3f58ad88e1f51");
        accessTokenParam.setClient_id("47b1457e4ad4dbd281d2");
        accessTokenParam.setCode("241223");
        return accessTokenParam;
    }
}
