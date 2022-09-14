package com.drawing;

import static com.jogamp.opengl.GL.GL_POINTS;

import java.awt.Point;

import com.jogamp.opengl.GL2;

public class GPixels implements GShape {

	private float size;
	private float x, y;

	public GPixels(float x, float y, float size) {

		this.size = size;
		this.x = x;
		this.y = y;
	}

	void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	void setXY(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public void render(final GL2 gl) {

		gl.glColor3f(.80f, .20f, .10f); // drawing color
		gl.glPointSize(this.size);

		gl.glBegin(GL_POINTS);
		gl.glVertex3f(this.x, this.y, 0.0f);
		gl.glEnd();

	}

	void updatePoint(float disp, float max) {
		if (this.x < max)
			this.x += disp;
		else
			this.x = 0;
	}
}
