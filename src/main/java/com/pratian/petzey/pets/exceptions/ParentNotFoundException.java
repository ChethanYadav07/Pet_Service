package com.pratian.petzey.pets.exceptions;

public class ParentNotFoundException extends RuntimeException{
	String message;

	public ParentNotFoundException() {
		super();
	}

	public ParentNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParentNotFoundException(String message) {
		super(message);
	}

	public ParentNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
