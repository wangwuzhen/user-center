package com.ice.usercenter.execption;

import com.ice.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 * @author ICE
 */
public class BusinessExecption extends RuntimeException {
    private final int code;

    private final String description;

    public BusinessExecption(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessExecption(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessExecption(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;

    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
