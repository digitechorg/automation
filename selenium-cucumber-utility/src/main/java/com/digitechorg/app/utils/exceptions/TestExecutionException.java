package com.digitechorg.app.utils.exceptions;

public class TestExecutionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public TestExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestExecutionException(String message) {
		super(message);
	}
	
	
}
