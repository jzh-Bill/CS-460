package com.drawing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class SpriteAnimation {
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
	final static int IDLE = 0, WALK = 1, RUN = 2;
	final int TOTAL_ANIM = 3;

	// name of the animations
	final String fileName[] = { "idle", "walk", "run" };
	// how many frames do we have for each animation
	final int frameNo[] = { 10, 10, 8 };
	// what is the moving speed for each animation
	final int moveSpeed[] = { 0, 10, 20 };

	private Texture spriteTex[][]; // holding all the textures for all the animations

	int movX, movY; // the speed of moving the model

	// what is the current selected animation
	int currentAnimation = IDLE;

	// constructor to initialize the sprite
	SpriteAnimation(int x, int y, long interval) {

		movX = x; // initial position on the screen
		movY = y;

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
				String name = "/world/dino/" + fileName[i] + "/" + fileName[i] + " (" + (j + 1) + ").png";

				// storing the texture in the array
				spriteTex[i][j] = loadTextureProjectDir(name);
			}
		}

		// creating a quad where the texture will be drawn
		// mySpriteQuad = new CQuad(0, 0);

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
	}

	// this method loads a texture specified in the filename from the project
	// directory
	private Texture loadTextureProjectDir(String name) {

		// the folders should be placed in the directory of the project
		String texPath = System.getProperty("user.dir") + name;

		try {
			File initialFile = new File(texPath); // create a file
			// convert it into stream
			InputStream stream = new FileInputStream(initialFile);
			// load texture data from the input stream
			TextureData data = TextureIO.newTextureData(GLProfile.get(GLProfile.GL2), stream, false, "png");
			// return the texture object
			return TextureIO.newTexture(data);

		} catch (IOException exc) {
			System.out.println("Could not load " + texPath);
			// exc.printStackTrace();
			System.exit(1);
		}

		// to make the compiler happy
		return null;
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
			if (movX > 400)
				movX = -400 - SPRITE_SIZE / 2;
		}
	}

	// actual drawing method for the sprite, that renders a texture on a quad
	void renderSprite(final GL2 gl) {
		// determine the animation frame for this animation
		deterMineNextFrame();

		// move the model to specified position
		gl.glPushMatrix();
		gl.glTranslatef(movX, movY, 0);
		// mySpriteQuad.renderTextureAlpha(gl, spriteTex[currentAnimation][aFrame], 195,
		// 150);
		gl.glPopMatrix();
	}

}
