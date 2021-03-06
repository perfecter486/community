package com.yijia.community.dto;

import lombok.Data;

import java.util.List;


@Data
public class PageInfoDto {

    private  boolean showNext;
    private  boolean showPre;
    private  boolean showEndPage;
    private  boolean showFirstPage;
    List<Integer> pageList ;
    private  Integer page;
    private  Integer pages;
}
