package com.ice.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 定义通用返回对象
 * @author ICE
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description=description;
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }


    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * 自定义错误返回信息  枚举类code，data返回null 枚举错误信息 描述
     * @param errorCode
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }


}
