package com.yijia.community.dto;

import com.yijia.community.domain.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionPageInfoDto {

    private List<Question> questions;

    private  PageInfoDto pageInfoDto;
}
