package com.curalate.simplepolygon.impl;

public class Point implements Comparable<Point> {
	private final double x;
	private final double y;

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int compareTo(final Point o) {
		if (x > o.x) {
			return 1;
		}

		if (x < o.x) {
			return -1;
		}

		if (y > o.y) {
			return 1;
		} else {
			if (y == o.y) {
				return 0;
			}
			return -1;
		}
	}
}
