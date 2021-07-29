package com.yijia.community.service;

import com.yijia.community.domain.User;
import com.yijia.community.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public  int insert(User user){

      return   userMapper.insert(user);
    }

    public  User selectByToken(String token){

        return userMapper.selectByToken(token);
    }
}

