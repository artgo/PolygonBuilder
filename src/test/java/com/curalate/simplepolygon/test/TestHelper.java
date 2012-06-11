package com.curalate.simplepolygon.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import com.curalate.simplepolygon.exceptions.ParsingException;
import com.curalate.simplepolygon.impl.InputParser;
import com.curalate.simplepolygon.impl.Point;

public class TestHelper {
	public static final String[] TEST_INPUT = { "0,0", "1,1", "1,0", "0,1" };
	public static final String[] TEST_INPUT2 = { "1.0,2.0", "1.0,3.0", "4.0,4.0", "1.0,4.0", "3.0,4.0", "4.0,1.0",
			"3.0,1.0", "2.0,1.0", "1.0,1.0", "4.0,3.0", "4.0,2.0", "2.0,4.0" };

	public static Collection<Point> getPointsForInput(final String[] input) throws ParsingException {
		final InputParser parser = new InputParser();

		return parser.parse(input);
	}

	public static void twoPointIteratablesAreEqual(final Iterable<Point> expected, final Iterable<Point> actual) {
		final Iterator<Point> actualIterator = actual.iterator();

		for (final Point pointExpected : expected) {
			assertTrue(actualIterator.hasNext());
			final Point pointActual = actualIterator.next();
			assertEquals(0, pointExpected.compareTo(pointActual));
		}

		assertFalse(actualIterator.hasNext());
	}

	public static Point getRandomPoint() {
		return new Point(Math.random() * Double.MAX_VALUE - (Double.MAX_VALUE / 2), Math.random() * Double.MAX_VALUE
				- (Double.MAX_VALUE / 2));
	}
}
