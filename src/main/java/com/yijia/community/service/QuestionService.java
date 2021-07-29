package com.yijia.community.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.yijia.community.domain.Question;
import com.yijia.community.domain.User;
import com.yijia.community.dto.PageDto;
import com.yijia.community.dto.QuestionDto;
import com.yijia.community.mapper.QuestionMapper;
import com.yijia.community.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {


    @Resource
    UserMapper userMapper;

    @Resource
    QuestionMapper questionMapper;


    public  int insert(Question question){

        return questionMapper.insert(question);
    }


    public  List<QuestionDto> selectAllDto(){

       return  selectAllAndPage(0,-1);

    }

    public  List<QuestionDto> selectAllAndPage( Integer page ,Integer size){


        page=page<1?1:page;//检验

        List<QuestionDto> questionDtos=new ArrayList<>();
        int offset=(page-1)*size;
        List<Question> question=questionMapper.selectAllAndPage(offset,size);

        for( Question question1: question){

            int creator =question1.getCreator();

            User user=userMapper.selectById(creator);

            QuestionDto questionDto=new QuestionDto();

            BeanUtils.copyProperties(question1,questionDto);

            questionDto.setUser(user);

            questionDtos.add(questionDto);

        }

        return  questionDtos;

    }

    private int count(){
        return questionMapper.count();
    }


    public PageDto selectQuestionsAndPage(Integer page,Integer size){




       List<QuestionDto>  questionDtos= selectAllAndPage(page,size);

        int count =count();


        return writePageInfo(page,size,count,questionDtos);

    }


    //填写分页后,相关页码显示的信息
    private   PageDto writePageInfo(int page,int size,int count,List<QuestionDto> questionDtos){



        int pages=count/size;

        pages=(count%size==0)?pages:pages+1;//计算总页数

        PageDto pageDto=new PageDto();


        int begin=page-3;
        begin=(begin>1)?begin:1;//得到页码的最小值

        int end=page+3;
        end=(end<pages)?end:pages;//得到页码的最大值

        List<Integer> pageList=new ArrayList<>();
        for(;begin<=end;++begin){//添加可在页面上展示的页码

            pageList.add(begin);
        }


        //判断当前页码之前还有页码否
        pageDto.setShowPre((page>1?true:false));

        //判断当前页码之后还有页码否
        pageDto.setShowNext((page<pages)?true:false);

        //判断是否显示第一页链接

        pageDto.setShowFirstPage((page-3>1)?true:false);

        //判断是否显示最后一页链接
        pageDto.setShowEndPage((page+3<pages)?true:false);

        pageDto.setPage(page);
        pageDto.setPageList(pageList);
        pageDto.setQuestionDtoList(questionDtos);
        pageDto.setPages(pages);

        return  pageDto;

    }
}
