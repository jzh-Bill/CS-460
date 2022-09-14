package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GTriangle implements GShape{
	
	float vertex2f[];

	GTriangle(float vertex2f[]) {
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
	}

	public void drawOutline(final GL2 gl) {
		gl.glColor3f(.20f, 0.20f, .80f); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		gl.glLineWidth(4f);

		gl.glBegin(GL_TRIANGLES);

		gl.glVertex2fv(vertex2f, 0); // v0
		gl.glVertex2fv(vertex2f, 2); // v1
		gl.glVertex2fv(vertex2f, 4); // v2

		gl.glEnd();
	}

	public void render(final GL2 gl) {
//
//		gl.glPushMatrix();
//		gl.glLoadIdentity(); // reset model-view matrix

		//gl.glTranslatef(15.0f, 15.0f, 0.0f);
		gl.glColor3fv(vertex2f, 6); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		gl.glBegin(GL_TRIANGLES);

		gl.glVertex2fv(vertex2f, 0); // v0
		gl.glVertex2fv(vertex2f, 2); // v1
		gl.glVertex2fv(vertex2f, 4); // v2

		gl.glEnd();

		//drawOutline(gl);

//		gl.glPopMatrix();

		// gl.glTranslatef(-15.0f, -15.0f, 0.0f);
	}

}
