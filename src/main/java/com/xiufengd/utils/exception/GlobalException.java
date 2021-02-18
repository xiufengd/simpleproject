package com.xiufengd.utils.exception;

import com.xiufengd.common.errorcode.ErrorCode;

//系统自定义异常处理类,针对预期的异常，需要在程序中抛出此类的异常
@SuppressWarnings("serial")
public class GlobalException extends RuntimeException{
	
    //异常信息
	private int code;

    private String message;

	public GlobalException() {
	}

	public GlobalException(Throwable ex) {
		super(ex);
	}

    public GlobalException(ErrorCode errorCode){
		super(errorCode.name());
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

	public GlobalException(String message) {
		super(message);
	}

	public GlobalException(String message, Throwable ex) {
		super(message, ex);
	}

}
