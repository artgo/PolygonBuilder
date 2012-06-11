package com.curalate.simplepolygon.exceptions;

public class PolygonValidationException extends Exception {
	private static final long serialVersionUID = -6775044476385496716L;

	public PolygonValidationException() {
	}

	public PolygonValidationException(String message) {
		super(message);
	}

	public PolygonValidationException(Throwable cause) {
		super(cause);
	}

	public PolygonValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolygonValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
