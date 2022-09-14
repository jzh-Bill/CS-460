package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.util.Arrays;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GQuad implements GShape {

	float vertex2f[];

	private Texture texID;
	float theta;
	final float thetaDisp = 360.0f / 100.0f;
	boolean alpha;

	GQuad(float vertex2f[]) {
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
		alpha = false;
		texID = null;
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	public void loadTexture(final GL2 gl, String path) {
		texID = GTextureUtil.loadTextureProjectDir(gl, path, "PNG");
		texID.setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		texID.setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		texID.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT);
		texID.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT);

		// float borderColor[] = { 1.0f, 1.0f, 0.0f, 1.0f };
		// texID.setTexParameterfv(gl, GL2.GL_TEXTURE_BORDER_COLOR, borderColor, 0);

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

		if(this.alpha == false)
		{
			renderTexture(gl);
		}
		else
			renderTextureAlpha(gl);
	}

	public void renderTexture(final GL2 gl) {
		gl.glPushMatrix();

		gl.glTranslatef(vertex2f[0], vertex2f[1], 0.0f);
		gl.glScalef(vertex2f[2], vertex2f[3], 1.0f);


		gl.glColor3fv(vertex2f, 4); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		if (texID != null) {

			gl.glEnable(GL2.GL_TEXTURE_2D);
			texID.enable(gl);
			texID.bind(gl);
		}

		gl.glBegin(GL_TRIANGLE_STRIP);


		gl.glTexCoord2f(1, 0);
		gl.glVertex2f(1f, 0f); // v0 bottom right

		gl.glTexCoord2f(1, 1);
		gl.glVertex2f(1f, 1f); // v1 top right

		gl.glTexCoord2f(0, 0);
		gl.glVertex2f(0f, 0f); // v2 bottom left

		gl.glTexCoord2f(0, 1);
		gl.glVertex2f(0f, 1f); // v3 top left


		gl.glEnd();

		// drawOutline(gl);

		if (texID != null) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
			texID.disable(gl);
		}

		gl.glPopMatrix();

	}

	void renderTextureAlpha(final GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		// gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		texID.enable(gl);
		texID.bind(gl);

		gl.glPushMatrix();
		gl.glTranslatef(vertex2f[0], vertex2f[1], 0.0f);
		gl.glScalef(vertex2f[2], vertex2f[3], 1.0f);

		gl.glColor3fv(vertex2f, 4); // drawing color
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		if (texID != null) {

			gl.glEnable(GL2.GL_TEXTURE_2D);
			texID.enable(gl);
			texID.bind(gl);
		}

		gl.glBegin(GL_TRIANGLE_STRIP);

		gl.glTexCoord2f(1, 0);
		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glTexCoord2f(1, 1);
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glTexCoord2f(0, 0);
		gl.glVertex2f(0f, 0f); // v2 bottom left
		gl.glTexCoord2f(0, 1);
		gl.glVertex2f(0f, 1f); // v3 top left

		gl.glEnd();

		// drawOutline(gl);

		if (texID != null) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
			texID.disable(gl);
		}

		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glPopMatrix();
	}

	public void updateTheta() {
		theta += this.thetaDisp;
		if (theta > 360.0f)
			theta = 0.f;
	}

}
