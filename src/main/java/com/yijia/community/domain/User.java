package com.yijia.community.domain;

public class User {

    private Integer id;
    private  String name;
    private  String account_id;
    private  String token;
    private Long create_time;

    private  Long modify_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long creat_time) {
        this.create_time = creat_time;
    }

    public Long getModify_time() {
        return modify_time;
    }

    public void setModify_time(Long modify_time) {
        this.modify_time = modify_time;
    }
}
