package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GCircle implements GShape {

	float radius;
	float x, y;
	int numSegments;
	float color[];

	GCircle(float x, float y, float r) {
		this.radius = r;
		this.x = x;
		this.y = y;

		numSegments = 32;

		color = new float[] { 0f, 1f, 1f, 1f, 0f, 0f };
	}

	void setColor(float color[]) {
		this.color = Arrays.copyOf(color, color.length);
	}

	public void drawOutline(final GL2 gl) {
		// gl.glColor3f(1.0f, 0.0f, 0.0f); // Blue
		gl.glColor3fv(this.color, 3);

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		gl.glLineWidth(4f);

		gl.glBegin(GL2.GL_LINE_LOOP);


		float anglePerSegment = 2.0f * (float) Math.PI / numSegments;
		float angle = 0f, nx = 0, ny = 0;
		for (int i = 0; i < numSegments; i++) { // Last vertex same as first vertex
			angle = i * anglePerSegment;
			nx = (float) Math.cos(angle) * this.radius;
			ny = (float) Math.sin(angle) * this.radius;
			gl.glVertex2f(this.x + nx, this.y + ny);
		}
		gl.glEnd();
	}


	public void render(final GL2 gl) {
		// gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glColor3fv(this.color, 0);

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		gl.glVertex2f(this.x, this.y); // Center of circle


		float anglePerSegment = 2.0f * (float) Math.PI / numSegments;
		float angle = 0f, nx = 0, ny = 0;
		for (int i = 0; i <= numSegments; i++) { // Last vertex same as first vertex
			angle = i * anglePerSegment;
			nx = (float) Math.cos(angle) * this.radius;
			ny = (float) Math.sin(angle) * this.radius;
			gl.glVertex2f(this.x + nx, this.y + ny);
		}
		gl.glEnd();
		drawOutline(gl);

	}
}
