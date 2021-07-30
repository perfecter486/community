package com.yijia.community.domain;

import lombok.Data;

@Data
public class Question {

    private  Integer id;
    private String title;
    private  Long create_time;
    private  Long modify_time ;
    private  String description;
    private  int creator;
    private  int view_count;
    private  int comment_count;
    private  int like_count;
    private  String tags;
    private  String creator_account_id;


}
