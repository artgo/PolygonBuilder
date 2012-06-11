package com.curalate.simplepolygon.impl;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LineTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testIsStrictlyBelowIsTrueForPointsOver() {
		final Line line = new Line(new Point(0,0), new Point(10,0));
		assertTrue(line.isStrictlyBelow(new Point(5,5)));
	}

	@Test
	public void testIsStrictlyBelowIsFalseForPointsBelow() {
		final Line line = new Line(new Point(0,0), new Point(10,0));
		assertFalse(line.isStrictlyBelow(new Point(5,-5)));
	}

	@Test
	public void testIsStrictlyBelowIsFalseForPointsOnTheLine() {
		final Line line = new Line(new Point(0,0), new Point(10,0));
		assertFalse(line.isStrictlyBelow(new Point(5,0)));
	}

	@Test
	public void testIsStrictlyBelowReturnsTrueForPointsOnLeftOfVerticalLine() {
		final Line line = new Line(new Point(0,0), new Point(0,10));
		assertTrue(line.isStrictlyBelow(new Point(-1, 0)));
	}

	@Test
	public void testIsStrictlyBelowReturnsFalseOnPointsOnRightVerticalLine() {
		final Line line = new Line(new Point(0,0), new Point(0,10));
		assertFalse(line.isStrictlyBelow(new Point(1, 0)));
	}

	@Test
	public void testLineThrowsOnUndefinedLines() {
		exception.expect(IllegalArgumentException.class);
		new Line(new Point(0,0), new Point(0,0));
	}

	@Test
	public void testFirstArgumentMustNotBeNull() {
		exception.expect(IllegalArgumentException.class);
		new Line(null, new Point(0,10));
	}

	@Test
	public void testSecondArgumentMustNotBeNull() {
		exception.expect(IllegalArgumentException.class);
		new Line(new Point(0,0), null);
	}

	@Test
	public void testIsStrictlyBelowThrowsOnNull() {
		final Line line = new Line(new Point(0,0), new Point(1,10));
		exception.expect(IllegalArgumentException.class);
		line.isStrictlyBelow(null);
	}
}
