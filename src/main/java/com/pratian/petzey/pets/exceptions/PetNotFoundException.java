package com.pratian.petzey.pets.exceptions;

public class PetNotFoundException extends RuntimeException {

	public PetNotFoundException() {
		super();
	}

	public PetNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PetNotFoundException(String message) {
		super(message);
	}

	public PetNotFoundException(Throwable cause) {
		super(cause);
	}

}