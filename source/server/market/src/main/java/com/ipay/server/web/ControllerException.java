package com.ipay.server.web;

public class ControllerException extends RuntimeException {
	
	private int httpStatusCode;

	/**
	 * service抛出的异常,如果不设置错误类型,默认是500内部服务器错误
	 */
	private static final long serialVersionUID = 1L;

	public ControllerException() {
		super();
	}

	public ControllerException(String message) {
		super(message);
		httpStatusCode = 500;
	}
	
	public ControllerException(String message,int httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}
	
	public int getHttpStatusCode(){
		return this.httpStatusCode;
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

}
