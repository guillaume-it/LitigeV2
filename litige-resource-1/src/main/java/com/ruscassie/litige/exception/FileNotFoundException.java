package com.ruscassie.litige.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends StorageException {

	/**
	 *
	 */
	private static final long serialVersionUID = 4997756685515095497L;

	public FileNotFoundException(final String message) {
		super(message);
	}

	public FileNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}