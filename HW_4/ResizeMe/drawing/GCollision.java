package com.drawing;

import java.awt.Point;

public class GCollision {

	/*
	 * A utility function to calculate area of triangle formed by (x1, y1) (x2, y2)
	 * and (x3, y3)
	 */
	private static double area(int x1, int y1, int x2, int y2, int x3, int y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	/*
	 * A function to check whether point P(x, y) lies inside the triangle formed by
	 * A(x1, y1), B(x2, y2) and C(x3, y3)
	 */
	static boolean isInside(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
		/* Calculate area of triangle ABC */
		double A = area(x1, y1, x2, y2, x3, y3);

		/* Calculate area of triangle PBC */
		double A1 = area(x, y, x2, y2, x3, y3);

		/* Calculate area of triangle PAC */
		double A2 = area(x1, y1, x, y, x3, y3);

		/* Calculate area of triangle PAB */
		double A3 = area(x1, y1, x2, y2, x, y);

		/* Check if sum of A1, A2 and A3 is same as A */
		return (A == A1 + A2 + A3);
	}

	public static boolean isInside(Point pa, Point pb, Point pc, Point p) {
		return isInside((int) pa.getX(), (int) pa.getY(), (int) pb.getX(), (int) pb.getY(),
				(int)pc.getX(), (int)pc.getY(), (int)p.getX(), (int)p.getY());

	}

	// Compare radius of circle with
	// distance of its center from
	// given point

	// we would like to avoid using square root calculation
	static boolean isInside(int circle_x, int circle_y, int rad, int x, int y) {
		if ((x - circle_x) * (x - circle_x) + (y - circle_y) * (y - circle_y) <= rad * rad)
			return true;
		else
			return false;
	}

	public static double distance(Point pa, Point pb) {

		int x1 = (int) pa.getX(), y1 = (int) pa.getY();
		int x2 = (int) pb.getX(), y2 = (int) pb.getY();

		double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

		return distance;

	}

	public static boolean isInside(Point circle, int rad, Point p) {
		return isInside((int) circle.getX(), (int) circle.getY(), rad, (int) p.getX(), (int) p.getY());
	}

	// determine whether a given point is inside a rectangle
	static boolean isInside(int x1, int y1, int x2, int y2, int x, int y) {
		if (x > x1 && x < x2 && y > y1 && y < y2)
			return true;

		return false;
	}

	public static boolean isInside(Point pa, Point pb, Point p) {
		return isInside((int) pa.getX(), (int) pa.getY(), (int) pb.getX(), (int) pb.getY(), (int) p.getX(),
				(int) p.getY());
	}
}
