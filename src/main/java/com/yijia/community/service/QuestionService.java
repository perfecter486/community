package com.yijia.community.service;

import com.yijia.community.domain.Question;
import com.yijia.community.domain.User;
import com.yijia.community.dto.PageDto;
import com.yijia.community.dto.PageInfoDto;
import com.yijia.community.dto.QuestionDto;
import com.yijia.community.dto.QuestionPageInfoDto;
import com.yijia.community.mapper.QuestionMapper;
import com.yijia.community.mapper.UserMapper;
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

        for( Question question1: question){//对每个问题查询其发起人

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

    private  int count(String accountId){ return questionMapper.countOfUserSelf(accountId);}


    public PageDto selectQuestionsAndPage(Integer page,Integer size){




       List<QuestionDto>  questionDtos= selectAllAndPage(page,size);

        int count =count();


        PageInfoDto  pageInfoDto= writePageInfo(page,size,count);

        PageDto pageDto =new PageDto();

        pageDto.setQuestionDtoList(questionDtos);
        pageDto.setPageInfoDto(pageInfoDto);

        return pageDto;


    }



    public QuestionPageInfoDto  selectQuestionsAndPage(Integer page, Integer size, String accountId){




        List<Question> questions = questionMapper.selectByUserAccountIdAndPage(page,size,accountId);

        int count= count(accountId);

        int offset =(page-1);

        PageInfoDto  pageInfoDto=writePageInfo(offset,size,count);



        QuestionPageInfoDto questionPageInfoDto=new QuestionPageInfoDto();

        questionPageInfoDto.setQuestions(questions);

        questionPageInfoDto.setPageInfoDto(pageInfoDto);

        return questionPageInfoDto;


    }



    //填写分页后,相关页码显示的信息
    private  PageInfoDto   writePageInfo(int page,int size,int count){



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


        PageInfoDto pageInfoDto =new PageInfoDto();

        //判断当前页码之前还有页码否
        pageInfoDto.setShowPre((page>1?true:false));

        //判断当前页码之后还有页码否
        pageInfoDto.setShowNext((page<pages)?true:false);

        //判断是否显示第一页链接

        pageInfoDto.setShowFirstPage((page-3>1)?true:false);

        //判断是否显示最后一页链接
        pageInfoDto.setShowEndPage((page+3<pages)?true:false);

        pageInfoDto.setPage(page);
        pageInfoDto.setPageList(pageList);
        pageInfoDto.setPages(pages);



        return  pageInfoDto;

    }
}
