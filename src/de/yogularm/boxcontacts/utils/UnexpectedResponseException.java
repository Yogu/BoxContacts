package de.yogularm.boxcontacts.utils;

public class UnexpectedResponseException extends Exception {
	private static final long serialVersionUID = -3699777287978252737L;

	public UnexpectedResponseException(String message) {
		super(message);
	}

	public UnexpectedResponseException(Throwable throwable) {
		super(throwable);
	}
	
	public UnexpectedResponseException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
