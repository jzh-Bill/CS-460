package com.drawing;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GPatch implements GShape {

	private Texture earthTexture;
	int patchNo;
	int locx, locy;
	int width, height;
	String imagePath;

	GPatch(int x, int y, int width, int height, int patchNo, String imagePath) {
		// number of division
		this.patchNo = patchNo;
		// size of the patch
		this.width = width;
		this.height = height;
		// texture path
		this.imagePath = imagePath;

		// location
		this.locx = x;
		this.locy = y;
	}

	public void render(final GL2 gl) {

		if (this.earthTexture == null) {
			earthTexture = GTextureUtil.loadTextureProjectDir(gl, imagePath, "PNG");
		}

		int texture = earthTexture.getTextureObject(gl);



		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		gl.glPushMatrix();
		gl.glTranslatef(locx, locy, 0);

		float x = 0;
		float y = 0;
		float patch_width = width / this.patchNo, path_height = this.height / this.patchNo;
		float texLeft, texBottom, texRight, texTop;

		texLeft = 0f;
		texBottom = 0f;
		texRight = patch_width / width;
		texTop = path_height / height;

		for (int i = 0; i < this.patchNo; i++) {
			x = 0f;
			texLeft = 0f;
			texRight = patch_width / width;

			for (int j = 0; j < this.patchNo; j++) {

				gl.glBegin(GL2.GL_QUADS); // Each set of 4 vertices form a quad
				gl.glColor3f(1.0f, 1.0f, 1.0f); // white

				gl.glTexCoord2d(texLeft, texBottom);
				gl.glVertex2d(x, y);

				gl.glTexCoord2d(texRight, texBottom);
				gl.glVertex2d(x + patch_width, y);

				gl.glTexCoord2d(texRight, texTop);
				gl.glVertex2d(x + patch_width, y + path_height);

				gl.glTexCoord2d(texLeft, texTop);
				gl.glVertex2d(x, y + path_height);
				gl.glEnd();

				x += patch_width;

				texLeft += patch_width / width;
				texRight += patch_width / width;
			}

			y += path_height;
			texBottom += path_height / height;
			texTop += path_height / height;

		}

		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}


}
