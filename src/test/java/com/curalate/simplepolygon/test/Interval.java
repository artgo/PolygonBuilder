package com.curalate.simplepolygon.test;

import com.curalate.simplepolygon.impl.Line;
import com.curalate.simplepolygon.impl.Point;

public class Interval extends Line {
	private final Point point1;
	private final Point point2;

	public Interval(final Point point1, final Point point2) {
		super(point1, point2);
		
		// Save points in sorted order
		if (point1.compareTo(point2) > 0) {
			this.point1 = point2;
			this.point2 = point1;
		} else {
			this.point1 = point1;
			this.point2 = point2;
		}
	}

	private double getYforX(final double x0) {
		return k * x0 + b;
	}

	private boolean hasBetweenX(final double x0) {
		return (point1.getX() < x0) && (x0 < point2.getX());
	}
	
	private boolean hasBetweenY(final double y0) {
		return (point1.getY() < y0) && (y0 < point2.getY());
	}
	
	public boolean doesIntersect(final Interval interval) {
		Interval a = this;
		Interval b = interval;

		// If there is at least one vertical, then a will be vertical
		if (b.isVertical) {
			a = interval;
			b = this;
		}

		if (a.isVertical) {
			if (b.isVertical) {
				if (a.x != b.x) {
					return false;
				}

				return a.hasBetweenY(b.point1.getY()) || a.hasBetweenY(b.point2.getY());
			} else {
				if (!b.hasBetweenX(a.x)) {
					return false;
				}
				
				final double yOnB = b.getYforX(a.x);
				
				return a.hasBetweenY(yOnB);
			}
		} else {
			if (a.k == b.k) {
				if (a.b != b.b) {
					return false;
				}
				
				return a.hasBetweenX(b.point1.getX()) || a.hasBetweenX(b.point2.getX());
			}
			
			final double xIntersection = (b.b - a.b)/(a.k - b.k);
			
			return a.hasBetweenX(xIntersection) && b.hasBetweenX(xIntersection);
		}
	}
}
