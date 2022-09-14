package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GCRQuad implements GShape {

	float vertex2f[];


	GCRQuad(float vertex2f[]) {
		// only color information is used
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
	}


	public void render(final GL2 gl) {

		gl.glPushMatrix();
		gl.glColor3fv(vertex2f, 0); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		gl.glScalef(vertex2f[3], vertex2f[4], vertex2f[5]);
		gl.glBegin(GL_TRIANGLE_STRIP);

		gl.glVertex2f(1f, -1f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, -1f); // v2 bottom left
		gl.glVertex2f(0f, 1f); // v3 top left

		gl.glEnd();

		gl.glPopMatrix();
	}
}
