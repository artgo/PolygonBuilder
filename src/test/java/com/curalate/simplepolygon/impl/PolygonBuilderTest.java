package com.curalate.simplepolygon.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.curalate.simplepolygon.exceptions.ParsingException;
import com.curalate.simplepolygon.exceptions.PolygonValidationException;
import com.curalate.simplepolygon.test.Interval;
import com.curalate.simplepolygon.test.TestHelper;

@RunWith(JUnit4.class)
public class PolygonBuilderTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testBuildPolygonThrowsOnEmptyinput() throws PolygonValidationException {
		final Collection<Point> points = new LinkedList<>();
		final PolygonBuilder builder = new PolygonBuilder();

		exception.expect(PolygonValidationException.class);
		builder.buildPolygon(points);
	}

	@Test
	public void testBuildPolygonThrowsOnOnePoint() throws PolygonValidationException {
		final Collection<Point> points = new LinkedList<>();
		points.add(new Point(0, 0));

		final PolygonBuilder builder = new PolygonBuilder();

		exception.expect(PolygonValidationException.class);
		builder.buildPolygon(points);
	}

	@Test
	public void testBuildPolygonPreserveTwoPointsPolygon() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1) });

		final PolygonBuilder builder = new PolygonBuilder();

		exception.expect(PolygonValidationException.class);
		builder.buildPolygon(points);
	}

	@Test
	public void testBuildPolygonWorksForThreePointsPolygon() throws PolygonValidationException {
		final Collection<Point> points = Arrays
				.asList(new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2) });

		final Collection<Point> expectedPoints = Arrays.asList(new Point[] { new Point(0, 0), new Point(2, 2),
				new Point(1, 1) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		assertEquals(3, polygon.getPointList().size());

		TestHelper.twoPointIteratablesAreEqual(expectedPoints, polygon.getPointList());
	}

	@Test
	public void testBuildPolygonThrowsOnNull() throws ParsingException, PolygonValidationException {
		exception.expect(IllegalArgumentException.class);
		new PolygonBuilder().buildPolygon(null);
	}

	@Test
	public void testBuildPolygonProcessesTestDataAsExpected() throws ParsingException, PolygonValidationException {
		final Collection<Point> points = TestHelper.getPointsForInput(TestHelper.TEST_INPUT);

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		assertEquals(TestHelper.TEST_INPUT.length, polygon.getPointList().size());

		final Iterator<Point> iter = polygon.getPointList().iterator();

		assertEquals(0, new Point(0, 0).compareTo(iter.next()));
		assertEquals(0, new Point(0, 1).compareTo(iter.next()));
		assertEquals(0, new Point(1, 1).compareTo(iter.next()));
		assertEquals(0, new Point(1, 0).compareTo(iter.next()));

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonProcessesTestData2WithNoIntersections() throws ParsingException, PolygonValidationException {
		final Collection<Point> points = TestHelper.getPointsForInput(TestHelper.TEST_INPUT2);

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		assertEquals(TestHelper.TEST_INPUT2.length, polygon.getPointList().size());

		final Collection<Point> expectedPoints = Arrays.asList(new Point[] { 
				new Point(1.00,1.00), new Point(1.00,2.00), new Point(1.00,3.00), new Point(1.00,4.00), 
				new Point(2.00,4.00), new Point(3.00,4.00), new Point(4.00,4.00), 
				new Point(4.00,3.00), new Point(4.00,2.00), new Point(4.00,1.00), new Point(3.00,1.00), new Point(2.00,1.00) });

		TestHelper.twoPointIteratablesAreEqual(expectedPoints, polygon.getPointList());
		
		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonThrowsIfOnOneLine() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(1, 3), new Point(1, 1), new Point(1, 5),
				new Point(1, 2), new Point(1, 4) });

		final PolygonBuilder builder = new PolygonBuilder();

		exception.expect(PolygonValidationException.class);
		builder.buildPolygon(points);
	}

	@Test
	public void testBuildPolygonThrowsIfLessThan3DistinctPoints() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 3), new Point(2, 5), new Point(2, 5),
				new Point(0, 3), new Point(2, 5) });

		final PolygonBuilder builder = new PolygonBuilder();

		exception.expect(PolygonValidationException.class);
		builder.buildPolygon(points);
	}

	@Test
	public void testBuildPolygonCollapsesSamePoints() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(4, 4), new Point(0, 0), new Point(1, 1),
				new Point(2, 5), new Point(5, 0), new Point(2, 5), new Point(3, 2), new Point(0, 0), new Point(3, 4),
				new Point(5, 0), new Point(2, 5) });

		final Collection<Point> expectedPoints = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1),
				new Point(2, 5), new Point(3, 2), new Point(3, 4), new Point(4, 4), new Point(5, 0) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		TestHelper.twoPointIteratablesAreEqual(expectedPoints, polygon.getPointList());
	}

	private Interval[] pointsToIntervals(final Collection<Point> points) {
		final Interval[] resultList = new Interval[points.size()];

		// We only deal with collections with at least 3 elements
		Point prev = (Point) points.toArray()[points.size() - 1];
		int pos = 0;

		for (final Point current : points) {
			resultList[pos++] = new Interval(prev, current);
			prev = current;
		}

		return resultList;
	}

	private void checkHasNoIntersections(final Interval[] intervals) {
		for (int i = 1; i < intervals.length; i++) {
			for (int j = 0; j < i; j++) {
				assertFalse(intervals[i].doesIntersect(intervals[j]));
			}
		}
	}

	private void checkHasNoIntersections(final Collection<Point> points) {
		final Interval[] intervals = pointsToIntervals(points);
		checkHasNoIntersections(intervals);
	}

	@Test(expected = AssertionError.class)
	public void testIntersectionsTestWorks() {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1), new Point(1, 0),
				new Point(0, 1) });

		checkHasNoIntersections(points);
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervals() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(4, 4), new Point(0, 0), new Point(1, 1),
				new Point(2, 5), new Point(5, 0), new Point(2, 5), new Point(3, 2), new Point(0, 0), new Point(3, 4),
				new Point(5, 0), new Point(2, 5) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForStandardTest() throws PolygonValidationException,
			ParsingException {
		final Collection<Point> points = TestHelper.getPointsForInput(TestHelper.TEST_INPUT);

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForSomePointsOnOneLine()
			throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 0), new Point(2, 0),
				new Point(1, -1) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForDiamondCase() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 2), new Point(2, 2), new Point(1, 1),
				new Point(1, 0) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForPointInTheMiddleCase() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 2), new Point(2, 0), new Point(0, 0),
				new Point(1, 1), new Point(2, 2) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForTwoPointsInTheMiddleCase2Sides() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 2), new Point(2, 0), new Point(0, 0),
				new Point(1, 1), new Point(2, 2), new Point(1, 1.1) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}

	@Test
	public void testBuildPolygonReturnsNonIntersectingIntervalsForTwoPointsInTheMiddleCase1Side() throws PolygonValidationException {
		final Collection<Point> points = Arrays.asList(new Point[] { new Point(0, 2), new Point(2, 0), new Point(0, 0),
				new Point(1, 1.2), new Point(2, 2), new Point(1, 1.1) });

		final PolygonBuilder builder = new PolygonBuilder();
		final Polygon polygon = builder.buildPolygon(points);

		checkHasNoIntersections(polygon.getPointList());
	}
}
