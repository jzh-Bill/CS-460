package com.drawing;

import static com.jogamp.opengl.GL.GL_POINTS;

import com.jogamp.opengl.GL2;

public class GPixels implements GShape {

	private float size;
	private float x, y;

	public GPixels(float x, float y, float size) {

		this.size = size;
		this.x = x;
		this.y = y;
	}
	public void render(final GL2 gl) {

		gl.glColor3f(.80f, .80f, .30f); // drawing color
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
