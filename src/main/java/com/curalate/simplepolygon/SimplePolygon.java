package com.curalate.simplepolygon;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;

import com.curalate.simplepolygon.exceptions.ParsingException;
import com.curalate.simplepolygon.exceptions.PolygonValidationException;
import com.curalate.simplepolygon.impl.InputParser;
import com.curalate.simplepolygon.impl.Point;
import com.curalate.simplepolygon.impl.Polygon;
import com.curalate.simplepolygon.impl.PolygonBuilder;

public class SimplePolygon {
	private InputParser parser;
	private PolygonBuilder builder;
	private OutputStream outputStream;

	SimplePolygon(final InputParser parser, final PolygonBuilder builder, final OutputStream outputStream) {
		this.parser = parser;
		this.builder = builder;
		this.outputStream = outputStream;
	}

	public static void main(String[] args) {
		final SimplePolygon simplePolygon = new SimplePolygon(new InputParser(), new PolygonBuilder(), System.out);
		simplePolygon.process(args);
	}

	public void process(final String[] args) {
		final PrintStream printStream = getPrintStream();
		
		Collection<Point> pointsSet;
		try {
			pointsSet = parser.parse(args);
		} catch (ParsingException e) {
			printStream.print("Error during parsing: " + e.getMessage());
			return;
		}
		
		Polygon polygon;
		try {
			polygon = builder.buildPolygon(pointsSet);
		} catch (PolygonValidationException e) {
			printStream.print("Error during building polygon: " + e.getMessage());
			return;
		}

		printStream.print("Simple Polygon:");
		for (final Point point : polygon.getPointList()) {
			printStream.printf(" (%.2f,%.2f)", point.getX(), point.getY());
		}
	}

	private PrintStream getPrintStream() {
		PrintStream output;

		if (outputStream instanceof PrintStream) {
			output = (PrintStream) outputStream;
		} else {
			output = new PrintStream(outputStream, /* autoflush */true);
		}

		return output;
	}
}
