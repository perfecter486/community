package com.yijia.community.dto;

import lombok.Data;

import java.util.List;


@Data
public class PageDto {
    private List<QuestionDto> questionDtoList;

    private  PageInfoDto pageInfoDto;


}
