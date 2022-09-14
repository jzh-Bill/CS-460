package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GLine implements GShape {

	private float vertex2f[];
	private float lineWidth;

	public GLine(float vertex2f[]) {
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
		lineWidth = 4f;
	}

	public void setLineWidth(float lw) {
		this.lineWidth = lw;
	}

	public void drawOutline(final GL2 gl) {
		gl.glColor3fv(vertex2f, 7); // outline color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		gl.glLineWidth(lineWidth);

		gl.glBegin(GL_LINES);

		gl.glVertex2fv(vertex2f, 0);
		gl.glVertex2fv(vertex2f, 2);

		gl.glEnd();
	}

	public void render(final GL2 gl) {

		// gl.glPushMatrix();
		// gl.glTranslatef(5.0f, 5.0f, 0.0f);
		gl.glColor3fv(vertex2f, 4); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


		drawOutline(gl);

		// gl.glPopMatrix();
	}

}
