package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.awt.Point;
import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GCRect implements GShape {

	float vertex2f[];

	GCRect(float vertex2f[]) {
		// only color information is used
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
		// first 3 elements are color
		// 4th, 5th two elements provides the lower left corner point
		// 6th element is the width
		// 7th element is the height
	}

	public float getWidth() {
		return vertex2f[5];
	}

	public float getHeight() {
		return vertex2f[6];
	}

	public float getBaseHeight() {
		return vertex2f[4];
	}

	public Point getLowerLeftPoint()
	{
		Point p = new Point();
		p.x = (int) vertex2f[3];
		p.y = (int) vertex2f[4];
		return p;
	}

	public Point getUpperRightPoint() {
		Point p = new Point();
		p.x = (int) (vertex2f[3] + vertex2f[5]);
		p.y = (int) (vertex2f[4] + vertex2f[6]);
		return p;

	}


	public void render(final GL2 gl) {

		gl.glPushMatrix();


		gl.glTranslatef(vertex2f[3],vertex2f[4], 0f);

		gl.glScalef(vertex2f[5], vertex2f[6], 1.0f);

		gl.glColor3fv(vertex2f, 0); // drawing color
		//drawOutline(gl);
		//drawFilled(gl);

		gl.glPopMatrix();
	}

	public void drawOutline(final GL2 gl) {

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		gl.glLineWidth(4f);

		gl.glBegin(GL_LINE_LOOP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 1f); // v3 top left
		gl.glVertex2f(0f, 0f); // v2 bottom left

		gl.glEnd();

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	public void drawFilled(final GL2 gl) {
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


		gl.glBegin(GL_TRIANGLE_STRIP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 0f); // v2 bottom left
		gl.glVertex2f(0f, 1f); // v3 top left

		gl.glEnd();
	}

	// returns whether a given point is inside the rectangle
	boolean isInside(Point p) {
		Point pa = getLowerLeftPoint();
		Point pb = getUpperRightPoint();
		return GCollision.isInside((int) pa.getX(), (int) pa.getY(), (int) pb.getX(), (int) pb.getY(), (int) p.getX(),
				(int) p.getY());
	}

}
