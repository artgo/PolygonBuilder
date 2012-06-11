package com.curalate.simplepolygon.impl;

public class Line {
	protected final double k;
	protected final double b;
	protected final boolean isVertical;
	protected final double x;

	public Line(final Point point1, final Point point2) {
		if ((point1 == null) || (point2 == null)) {
			throw new IllegalArgumentException("One of points specified is null");
		}

		if (point1.compareTo(point2) == 0) {
			throw new IllegalArgumentException("Can't form line out of one point");
		}

		x = point1.getX();
		k = (point2.getY() - point1.getY()) / (point2.getX() - x);
		isVertical = Double.isInfinite(k) || Double.isNaN(k);
		if (isVertical) {
			b = 0.0;
		} else {
			b = point1.getY() - k * point1.getX();
		}
	}

	public boolean isStrictlyBelow(final Point point) {
		if (point == null) {
			throw new IllegalArgumentException("point is null");
		}

		return isVertical? point.getX() < x :  point.getY() > (k * point.getX() + b);
	}
}
