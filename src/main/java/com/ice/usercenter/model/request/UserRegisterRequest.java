package com.ice.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = 5849305912759195008L;

    private String userAccount;
    private String userPassWord;
    private String checkPassWord;
    private String planetCode;
}
