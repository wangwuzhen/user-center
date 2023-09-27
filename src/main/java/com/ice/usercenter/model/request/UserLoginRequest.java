package com.ice.usercenter.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5849305912759195008L;

    private String userAccount;
    private String userPassWord;
    //private HttpServletRequest httpServletRequest;
}
