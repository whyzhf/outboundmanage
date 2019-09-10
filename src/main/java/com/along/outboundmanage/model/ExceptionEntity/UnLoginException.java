package com.along.outboundmanage.model.ExceptionEntity;

public class UnLoginException extends RuntimeException{
	public UnLoginException() {
	}

	public UnLoginException(String message) {
		super(message);
	}

	public UnLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnLoginException(Throwable cause) {
		super(cause);
	}

	public UnLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
