package com.xiufengd.common;

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
