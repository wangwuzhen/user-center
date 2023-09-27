package com.ice.usercenter.execption;

import com.ice.usercenter.common.BaseResponse;
import com.ice.usercenter.common.ErrorCode;
import com.ice.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author ICE
 */
@RestControllerAdvice
@Slf4j
public class GlobalExecptionHandler {


    @ExceptionHandler(BusinessExecption.class)
    public BaseResponse businessExecptionHandler(BusinessExecption e){
        log.error("businessExecption"+e.getMessage(),e);

        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());



    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExecptionHandler(BusinessExecption e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
