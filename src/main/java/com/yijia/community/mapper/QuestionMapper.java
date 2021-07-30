package com.yijia.community.mapper;

import com.yijia.community.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {


    int insert(Question question);

    List<Question> selectAllAndPage(@Param("offset") Integer offset,@Param("size") Integer size);

    int count();

    List<Question> selectByUserAccountIdAndPage(@Param("offset") Integer offset,@Param("size") Integer size,@Param("account_id") String accountId);

    int countOfUserSelf(@Param("account_id") String accountId);
}
