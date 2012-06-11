package com.curalate.simplepolygon.impl;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.curalate.simplepolygon.exceptions.ParsingException;
import com.curalate.simplepolygon.test.TestHelper;

@RunWith(JUnit4.class)
public class InputParserTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testParseThrowsOnNull() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(null);
	}

	@Test
	public void testParseAlwaysReturnsNotNull() throws ParsingException {
		assertNotNull(new InputParser().parse(new String[] { "0,0" }));
	}

	@Test
	public void testParseThrowsOnNullInArray() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { null });
	}

	@Test
	public void testParseThrowsOnOnlyOneNumberInPair() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "0" });
	}

	@Test
	public void testParseThrowsOnWrongFormatOfSecondArgument() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "0,w" });
	}

	@Test
	public void testParseThrowsOnWrongFormatOfFirstArgument() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "w,0" });
	}

	@Test
	public void testParseThrowsOnWrongFormatOfSecondArgumentOfSecondPoint() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "0,0", "0,e" });
	}

	@Test
	public void testParseThrowsOnEmptyFormatOfSecondArgument() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "0," });
	}

	@Test
	public void testParseThrowsOnEmptyFormatOfFirstArgument() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { ",0" });
	}

	@Test
	public void testParseThrowsOnEmptyFormatOfSecondArgumentOfSecondPoint() throws ParsingException {
		exception.expect(ParsingException.class);
		new InputParser().parse(new String[] { "0,0", "0," });
	}

	@Test
	public void testParseParsesDoubles() throws ParsingException {
		final Iterable<Point> points = new InputParser().parse(new String[] { "23.3876,3.234E+2" });
		final Iterator<Point> iter = points.iterator();
		
		assertTrue(iter.hasNext());
		
		final Point value = iter.next();
		
		assertEquals(23.3876, value.getX(), 0.0001);
		assertEquals(3.234E+2, value.getY(), 1.0);
		
		assertFalse(iter.hasNext());
	}

	@Test
	public void testParseParsesTestDataCorrectly() throws ParsingException {
		// "0,0", "1,1", "1,0", "0,1"
		final Point[] expectedResults = new Point[] { new Point(0, 0), new Point(1, 1), new Point(1, 0),
				new Point(0, 1) };
		
		final Iterable<Point> points = new InputParser().parse(TestHelper.TEST_INPUT);
		
		int pos = 0;
		for (final Point point : points) {
			assertEquals(0, point.compareTo(expectedResults[pos++]));
		}

		assertEquals(expectedResults.length, pos);
	}

	@Test
	public void testParseParsesListsCorrectly() throws ParsingException {
		final Point[] expectedResults = new Point[] { new Point(12.32, 3.2), new Point(43.1, 1.22e3),
				new Point(1234.8, 0.0002), new Point(0.2342, 1111.3), new Point(1.2342, 1.4), new Point(4.2, 76.9) };
		final String[] resultsStr = new String[expectedResults.length];
		
		for (int i = 0; i < expectedResults.length; i++) {
			resultsStr[i] = Double.toString(expectedResults[i].getX()) + ","
					+ Double.toString(expectedResults[i].getY());
		}
		
		final Iterable<Point> points = new InputParser().parse(resultsStr);
		
		int pos = 0;
		for (final Point point : points) {
			assertEquals(0, point.compareTo(expectedResults[pos++]));
		}

		assertEquals(expectedResults.length, pos);
	}
}
