package com.xiufengd.common;
/**
 *
 * @Description:操作环境变量
 * @Author:xiufengd
 * @Date:  2021/2/19 22:50
 * @Param: null
 * @return: null
 * @Throws
 */
public enum OperationEnv {
	
	H5("H5"),
	MOBILE("移动端"),
	PC("PC端");
	
	String operation;
	
	OperationEnv(String operation) {
		this.operation = operation;
    }
	
	public String getOperation() {
        return operation;
    }
	
	@Override
	public String toString() {
		return operation;
	}
}
