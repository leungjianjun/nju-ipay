package com.ipay.server.security;

public class TransactionException extends RuntimeException {
	
	private int httpStatusCode;

	/**
	 * service抛出的异常,如果不设置错误类型,默认是500内部服务器错误
	 */
	private static final long serialVersionUID = 1L;

	public TransactionException() {
		super();
	}

	public TransactionException(String message) {
		super(message);
		httpStatusCode = 500;
	}
	
	public TransactionException(String message,int httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}
	
	public int getHttpStatusCode(){
		return this.httpStatusCode;
	}

	public TransactionException(Throwable cause) {
		super(cause);
	}

	public TransactionException(String message, Throwable cause) {
		super(message, cause);
	}

}
