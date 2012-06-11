package com.curalate.simplepolygon.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Polygon {
	private final Collection<Point> pointsList;
	
	public Polygon() {
		pointsList = new LinkedList<>();
	}

	public Polygon(final Collection<Point> pointsList) {
		if (pointsList == null) {
			throw new IllegalArgumentException("pointsList can't be null");
		}
		
		this.pointsList = pointsList;
	}
	
	public Collection<Point> getPointList() {
		return pointsList;
	}
	
	public void add(final Point point) {
		if (point == null) {
			throw new IllegalArgumentException("point can't be null");
		}
		
		pointsList.add(point);
	}

	public void addAll(final Collection<Point> points) {
		if (points == null) {
			throw new IllegalArgumentException("points can't be null");
		}
		
		pointsList.addAll(points);
	}

	public void addFromIterator(final Iterator<Point> pointsIter) {
		if (pointsIter == null) {
			throw new IllegalArgumentException("pointsIter can't be null");
		}
		
		while(pointsIter.hasNext()) {
			pointsList.add(pointsIter.next());
		}
	}
}
