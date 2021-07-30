package com.yijia.community.domain;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private  String name;
    private  String account_id;
    private  String token;
    private Long create_time;

    private  Long modify_time;
    private  String avatar_url;

}
