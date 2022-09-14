package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GSprite implements GShape {
	// stores a set of texture coordinate along with a quad information
	// the textures are rendered at fixed interval (time) and repeated
	// translation based animation (horizontal, vertical) can also be
	// illustrated in the class

	// CQuad mySpriteQuad;

	long startFrameTime = 0;
	long interval = 500; // how fast the animation frames are changing

	int aFrame = 0; // current frame for this animation
	int TOTAL_FRAME = 10; // we calculate this for each animation

	// so that we can use these variables from outside this class
	final static int IDLE = 0, WALK = 1, RUN = 2, JUMP = 3;
	final int TOTAL_ANIM = 4;

	// name of the animations
	final String fileName[] = { "idle", "walk", "run", "jump" };
	// how many frames do we have for each animation
	final int frameNo[] = { 10, 10, 8, 12 };
	// what is the moving speed for each animation
	final int moveSpeed[] = { 0, 5, 10, 10 };

	private Texture spriteTex[][]; // holding all the textures for all the animations

	int movX, movY, originalY; // the speed of moving the model
	float width, height;

	// what is the current selected animation
	int currentAnimation = WALK;

	// constructor to initialize the sprite
	GSprite(final GL2 gl, int x, int y, long interval, float w, float h) {

		movX = x; // initial position on the screen
		movY = y;
		originalY = y;
		this.width = w; // dimension
		this.height = h;

		this.interval = interval;
		startFrameTime = System.currentTimeMillis();

		// loading the texture objects
		spriteTex = new Texture[TOTAL_ANIM][];

		// load all the texture in the array
		for (int i = 0; i < TOTAL_ANIM; i++) {
			// allocation memory for all the textures for this row
			spriteTex[i] = new Texture[frameNo[i]];

			for (int j = 0; j < frameNo[i]; j++) {
				// calculating the name of the texture
				String name = "/world/dino/" + fileName[i] + "/" + fileName[i] + (j + 1) + ".png";

				// storing the texture in the array
				spriteTex[i][j] = GTextureUtil.loadTextureProjectDir(gl, name, "PNG");
				spriteTex[i][j].setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
				spriteTex[i][j].setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			}
		}
	}

	// by using this method, the outside class can choose which animation to render
	void selectAnimation(int animationNumber) {

		// in order to ensure that we do not use an invalid animation number
		if (animationNumber < 0 || animationNumber >= this.TOTAL_ANIM)
			return;

		// otherwise, change the animation number as well as the frame-number
		currentAnimation = animationNumber;
		TOTAL_FRAME = frameNo[animationNumber];
		// we have to do it immediately, in order to ensure that
		// the frame number in the array is correct for this animation
		aFrame = (aFrame + 1) % TOTAL_FRAME;

		if (animationNumber != JUMP)
			movY = originalY;

	}



	// by using time as a parameter, the next animation frame number is calculated
	private void deterMineNextFrame() {
		long endFrameTime = System.currentTimeMillis();

		// interval is the duration of one animation frame
		if ((endFrameTime - startFrameTime) > interval) {
			// go to the next frame
			aFrame = (aFrame + 1) % TOTAL_FRAME;
			startFrameTime = endFrameTime;

			// calculating the new position of the dino
			final int SPRITE_SIZE = 260;
			movX += this.moveSpeed[this.currentAnimation];
			if (movX > GLUTCanvas.DRAWING_WIDTH / 2)
				movX = -(int) GLUTCanvas.DRAWING_WIDTH / 2 - SPRITE_SIZE / 2;

			// for each step what is the height of the animated sprite
			float angleStep = (float) Math.PI / this.frameNo[this.currentAnimation];
			final int JUMP_HEIGHT = 50;
			if (currentAnimation == JUMP) {
				float angle = angleStep * (aFrame);
				movY = (int) (Math.sin(angle) * JUMP_HEIGHT);
				movY = originalY + movY;
			}
		}
	}

	private void renderQuad(final GL2 gl, Texture texture) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		// gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		// gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		// gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_BLEND);
		texture.enable(gl);
		texture.bind(gl);

		gl.glPushMatrix();
		gl.glTranslatef(movX, movY, 0.0f);
		gl.glScalef(width, height, 1.0f);
		gl.glColor3f(0.5f, 0.5f, .9f); // drawing color

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		if (texture != null) {

			gl.glEnable(GL2.GL_TEXTURE_2D);
			texture.enable(gl);
			texture.bind(gl);
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

		if (texture != null) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
			texture.disable(gl);
		}

		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glPopMatrix();
	}

	// actual drawing method for the sprite, that renders a texture on a quad
	public void render(final GL2 gl) {
		// determine the animation frame for this animation
		deterMineNextFrame();

		// render a quad with the current texture
		renderQuad(gl, spriteTex[currentAnimation][aFrame]);
	}

}
