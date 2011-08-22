package com.ipay.server.service;

public class ServiceException extends RuntimeException {
	
	private int httpStatusCode;

	/**
	 * service抛出的异常,如果不设置错误类型,默认是500内部服务器错误
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
		httpStatusCode = 500;
	}
	
	public ServiceException(String message,int httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}
	
	public int getHttpStatusCode(){
		return this.httpStatusCode;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
