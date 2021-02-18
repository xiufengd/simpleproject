package com.xiufengd.utils.exception;

import com.xiufengd.common.errorcode.ErrorCode;

/**
 * @author: liyang
 * @date：2018/11/21 0021
 * describe：
 */
public class WechatException extends RuntimeException{


    //异常信息
    private int code;

    private String message;

    public WechatException() {
    }

    public WechatException(Throwable ex) {
        super(ex);
    }

    public WechatException(ErrorCode errorCode){
        super(errorCode.name());
        //this.code = ErrorCode.valueOf(errorCode.name()).getCode();
        this.message = errorCode.name();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable ex) {
        super(message, ex);
    }

}
