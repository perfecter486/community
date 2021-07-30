package com.yijia.community.config;

import com.yijia.community.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {



    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        String [] excludePath ={"/callback","/**/*.css","/**/*.js","/**/fonts/**"};
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath);


    }
}
