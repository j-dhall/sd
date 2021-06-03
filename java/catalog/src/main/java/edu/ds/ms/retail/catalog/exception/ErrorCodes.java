package edu.ds.ms.retail.catalog.exception;

public enum ErrorCodes {
	INVALID_ARGUMENT(1001);
	
	private ErrorCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	private int code;	
}