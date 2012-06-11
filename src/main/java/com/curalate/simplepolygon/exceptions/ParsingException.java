package com.curalate.simplepolygon.exceptions;

public class ParsingException extends Exception {
	private static final long serialVersionUID = 2039788423701159145L;

	public ParsingException() {
		super();
	}

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}
}
