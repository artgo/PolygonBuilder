package com.curalate.simplepolygon.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.curalate.simplepolygon.test.TestHelper;

public class PointTest {

	@Test
	public void testGetXIsConsistent() {
		final Point point = new Point(-12.0, 0);
		assertEquals(-12.0, point.getX(), 0.1);
	}

	@Test
	public void testGetYIsConsistent() {
		final Point point = new Point(0, -12.0);
		assertEquals(-12.0, point.getY(), 0.1);
	}

	@Test
	public void testPointsAreComparedByX() {
		final Point point1 = new Point(1.0, -12.0);
		final Point point2 = new Point(0.0, 100.0);
		assertEquals(1, point1.compareTo(point2));
		assertEquals(-1, point2.compareTo(point1));
	}

	@Test
	public void testPointsAreComparedByYIfXAreEqual() {
		final Point point1 = new Point(1.0, 1.0);
		final Point point2 = new Point(1.0, 0.0);
		assertEquals(1, point1.compareTo(point2));
		assertEquals(-1, point2.compareTo(point1));
	}

	@Test
	public void testEqualPointsAreEqual() {
		final Point point1 = new Point(23.0, -12.0);
		final Point point2 = new Point(23.0, -12.0);
		assertEquals(0, point1.compareTo(point2));
	}

	@Test
	public void testPointIsEquelToItself() {
		for (int i = 0; i < 1000; i++) {
			final Point point = TestHelper.getRandomPoint();
			assertEquals(0, point.compareTo(point));
		}
	}
}
