package com.ice.usercenter.common;

/**
 * 返回工具类
 * 帮助我们去new返回对象
 * @author ICE
 */
public class ResultUtils  {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public  static <T>  BaseResponse<T> success (T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * 失败
     * @param errorCode
     * @param
     * @return
     */
    public   static BaseResponse error(ErrorCode errorCode){
        return  new BaseResponse(errorCode);
    }
    /**
     * 失败
     * @param errorCode
     * @param
     * @return
     */
    public   static BaseResponse error(ErrorCode errorCode,String message,String description){
        return  new BaseResponse(errorCode.getCode(),null,message,description);
    }

    /**
     * 失败
     * @param errorCode
     * @param
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description){
        return  new BaseResponse(errorCode.getCode(),null,description);
    }
    /**
     * 失败
     * @param code
     * @param
     * @return
     */
    public   static BaseResponse error(int code,String message,String description){
        return  new BaseResponse(code, null,message,description);
    }





}
