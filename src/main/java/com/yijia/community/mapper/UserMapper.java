package com.yijia.community.mapper;

import com.yijia.community.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    int insert(User user);

    User selectByToken(String value);
}
