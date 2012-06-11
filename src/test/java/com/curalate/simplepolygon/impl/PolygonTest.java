package com.curalate.simplepolygon.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PolygonTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPolygonConstructorThrowsOnNull() {
		exception.expect(IllegalArgumentException.class);
		new Polygon(null);
	}

	@Test
	public void testGetPointListReturnsNotNull() {
		assertNotNull(new Polygon().getPointList());
	}

	@Test
	public void testAddThrowsOnNull() {
		exception.expect(IllegalArgumentException.class);
		new Polygon().add(null);
	}

	@Test
	public void testAddAllThrowsOnNull() {
		exception.expect(IllegalArgumentException.class);
		new Polygon().addAll(null);
	}

	@Test
	public void testAddFromIteratorThrowsOnNull() {
		exception.expect(IllegalArgumentException.class);
		new Polygon().addFromIterator(null);
	}

	@Test
	public void testAddAddsElement() {
		final Polygon polygon = new Polygon();
		final Point point = new Point(0, 0);
		final Point point2 = new Point(1, 0);

		polygon.add(point);
		polygon.add(point2);

		assertTrue(polygon.getPointList().contains(point));
		assertTrue(polygon.getPointList().contains(point2));
	}

	@Test
	public void testAddPresersAddingOrder() {
		final Polygon polygon = new Polygon();
		final Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2) };

		for (final Point point : points) {
			polygon.add(point);
		}

		int i = 0;
		for (final Point point : polygon.getPointList()) {
			assertEquals(points[i++], point);
		}
	}

	@Test
	public void testAddAllAddsElements() {
		final Polygon polygon = new Polygon();
		final List<Point> points = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2) });
		
		polygon.addAll(points);
		
		int i = 0;
		for (final Point point : polygon.getPointList()) {
			assertEquals(points.get(i++), point);
		}
	}

	@Test
	public void testAddFromIteratorAddsElements() {
		final Polygon polygon = new Polygon();
		final List<Point> points = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2) });
		
		polygon.addFromIterator(points.iterator());
		
		int i = 0;
		for (final Point point : polygon.getPointList()) {
			assertEquals(points.get(i++), point);
		}
	}
}
