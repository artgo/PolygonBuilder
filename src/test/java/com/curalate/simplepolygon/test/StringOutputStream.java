package com.curalate.simplepolygon.test;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream {
	private final StringBuilder stringBuilder = new StringBuilder();

	@Override
	public void write(final int b) throws IOException {
		stringBuilder.append((char) b);
	}

	public String getString() {
		return stringBuilder.toString();
	}
}
