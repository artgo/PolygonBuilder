package com.curalate.simplepolygon.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import com.curalate.simplepolygon.exceptions.PolygonValidationException;

public class PolygonBuilder {

	public Polygon buildPolygon(final Collection<Point> pointsSet) throws PolygonValidationException {
		if (pointsSet == null) {
			throw new IllegalArgumentException("pointsSet is null");
		}
		
		// Check for extreme case where there are 1 or 2 points case
		if (pointsSet.size() < 3) {
			throw new PolygonValidationException("Not enough points for polygon");
		}
		
		final Iterator<Point> pointsIter = pointsSet.iterator();
		
		// As soon as there are at least 3 elements, get 1st
		Point minX = pointsIter.next();
		Point maxX = minX;
		
		// Find min and max starting from second element
		while(pointsIter.hasNext()) {
			final Point point = pointsIter.next();
			if (point.compareTo(minX) < 0) {
				minX = point;
			} else if (point.compareTo(maxX) > 0) {
				maxX = point;
			}
		}
		
		// Special case: all point lined up in a line
		if(maxX.getX() == minX.getX()) {
			throw new PolygonValidationException("All points are on one line");
		}
		
		final Line line = new Line(minX, maxX);
		
		final Collection<Point> above = new TreeSet<>();
		final TreeSet<Point> below = new TreeSet<>();
		
		// We will divide all points on those above and below the line
		for(final Point point : pointsSet) {
			// If line is strictly below the point
			if (line.isStrictlyBelow(point)) {
				above.add(point);
			} else {
				// If not min or max themselves or comparable
				if ((minX.compareTo(point) != 0) && (maxX.compareTo(point) != 0)) {
					below.add(point);
				}
			}
		}
		
		final Polygon polygon = new Polygon();
		polygon.add(minX);
		polygon.addAll(above);
		polygon.add(maxX);
		polygon.addFromIterator(below.descendingIterator());
		
		if (polygon.getPointList().size() < 3) {
			throw new PolygonValidationException("Not enough distinct points for polygon");
		}

		return polygon;
	}

}
