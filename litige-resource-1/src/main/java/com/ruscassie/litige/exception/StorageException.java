package com.ruscassie.litige.exception;

public class StorageException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -6032029479338935336L;

	public StorageException(final String message) {
		super(message);
	}

	public StorageException(final String message, final Throwable cause) {
		super(message, cause);
	}
}