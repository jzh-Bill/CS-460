package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GQuad implements GShape {

	float vertex2f[];

	float theta;
	final float thetaDisp = 360.0f / 100.0f;

	GQuad(float vertex2f[]) {
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
	}


	public void drawOutline(final GL2 gl) {
		gl.glColor3fv(vertex2f, 7); // outline color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		gl.glLineWidth(4f);

		gl.glBegin(GL_LINE_LOOP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 1f); // v3 top left
		gl.glVertex2f(0f, 0f); // v2 bottom left


		gl.glEnd();
	}

	public void render(final GL2 gl) {

		gl.glPushMatrix();

		gl.glTranslatef(vertex2f[0], vertex2f[1], 0.0f);
		gl.glScalef(vertex2f[2], vertex2f[3], 1.0f);
		gl.glRotatef(theta, 0.0f, 0.0f, 1.0f);

		gl.glColor3fv(vertex2f, 4); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		gl.glBegin(GL_TRIANGLE_STRIP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 0f); // v2 bottom left
		gl.glVertex2f(0f, 1f); // v3 top left


		gl.glEnd();

		drawOutline(gl);

		gl.glPopMatrix();

	}

	public void updateTheta() {
		theta += this.thetaDisp;
		if (theta > 360.0f)
			theta = 0.f;
	}

}
