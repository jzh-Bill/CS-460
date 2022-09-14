package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;

public class GDrawingPoints implements GShape {
	public static int XGL_CIRCLE = 100;

	private class DrawingPoint {
		private Point p;
		private float color[];

		DrawingPoint(Point p) {
			this.p = p;
			Random random = new Random();
			color = new float[] { random.nextFloat(), random.nextFloat(), random.nextFloat() };
		}

		void randomizeColor() {
			Random random = new Random();
			color = new float[] { random.nextFloat(), random.nextFloat(), random.nextFloat() };
		}

		float[] getColorArray() {
			return color;
		}

		float getX() {
			return (float) p.getX();
		}

		float getY()
		{
			return (float) p.getY();
		}
		
		Point getPoint()
		{	return p;
		}

	}

	ArrayList<DrawingPoint> mousePoints;
	int drawingType;


	GDrawingPoints(int dT) {
		mousePoints = new ArrayList<DrawingPoint>();
		this.drawingType = dT;
	}

	void addPoint(int x, int y) {
		Point p = new Point(x, y);

		mousePoints.add(new DrawingPoint(p));
	}

	boolean isInsidePoint(Point p) {

		boolean flag = false;
		
		if (this.drawingType == GL_TRIANGLES) {
			// take next three points

			for (int elem = 0; elem < mousePoints.size(); elem += 3) {

				// check if we have three more points
				if (elem + 2 < mousePoints.size()) {
					DrawingPoint point_a = mousePoints.get(elem);
					DrawingPoint point_b = mousePoints.get(elem + 1);
					DrawingPoint point_c = mousePoints.get(elem + 2);

					if (GCollision.isInside(point_a.getPoint(), point_b.getPoint(), point_c.getPoint(), p)) {

						point_a.randomizeColor();
						point_b.randomizeColor();
						point_c.randomizeColor();

						flag = true;
						break;
					}

				}

			}

		} else if (this.drawingType == GL_LINES) {
			// take next three points

		}
		else if (this.drawingType == XGL_CIRCLE) {
			// take next two points

			for (int elem = 0; elem < mousePoints.size(); elem += 2) {

				// check if we have three more points
				if (elem + 1 < mousePoints.size()) {
					DrawingPoint point_a = mousePoints.get(elem);
					DrawingPoint point_b = mousePoints.get(elem + 1);

					double radius = GCollision.distance(point_a.getPoint(), point_b.getPoint());
					if (GCollision.isInside(point_a.getPoint(), (int) radius, p)) {

						flag = true;
						break;
					}

				}

			}

		}

		return flag;

	}

	void addPoint(Point p) {

		if (isInsidePoint(p) == false)
			mousePoints.add(new DrawingPoint(p));
	}

	void setDrawingType(int dT) {
		this.drawingType = dT;
	}

	public void render(final GL2 gl) {

		gl.glPushMatrix();

		gl.glColor3f(0.4f, 0.2f, .6f); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		if (this.drawingType == GDrawingPoints.XGL_CIRCLE) {


		} else {
			gl.glBegin(this.drawingType);

			for (DrawingPoint p : mousePoints) {
				gl.glColor3fv(p.getColorArray(), 0);
				gl.glVertex2f((float) p.getX(), (float) p.getY());

			}

			gl.glEnd();
		}

		gl.glPopMatrix();
	}

}
