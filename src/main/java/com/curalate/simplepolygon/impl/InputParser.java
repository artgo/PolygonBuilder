package com.curalate.simplepolygon.impl;

import java.util.Collection;
import java.util.LinkedList;

import com.curalate.simplepolygon.exceptions.ParsingException;

public class InputParser {
	private static final String COORDINATES_SEPARATOR = ",";

	public Collection<Point> parse(final String[] args) throws ParsingException {
		if ((args == null) || (args.length == 0)) {
			throw new ParsingException("No arguments provided");
		}

		// Sort and remove duplicates
		final Collection<Point> points = new LinkedList<>();

		for (final String pointStr : args) {
			if (pointStr == null) {
				throw new ParsingException("One of arguments is null");
			}

			final String[] strParts = pointStr.split(COORDINATES_SEPARATOR);
			if (strParts.length != 2) {
				throw new ParsingException("Wrong format for point: " + pointStr);
			}

			double x;
			double y;
			try {
				x = Double.parseDouble(strParts[0]);
				y = Double.parseDouble(strParts[1]);
			} catch (NullPointerException | NumberFormatException e) {
				throw new ParsingException("Wrong format for point: " + pointStr);
			}
			
			final Point point = new Point(x, y);
			points.add(point);
		}

		return points;
	}
}
