package com.drawing;

import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CW;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import java.awt.Point;
import java.lang.reflect.Array;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GEnemy implements GShape {
	// stores a set of texture coordinate along with a quad information
	// the textures are rendered at fixed interval (time) and repeated
	// translation based animation (horizontal, vertical) can also be
	// illustrated in the class


	//The moving quad used to detect the bullets.
	GCRect bulletDetectSqr;
	
	GPixels centerPosition;
	
	long startFrameTime = 0;
	long interval = 500; // how fast the animation frames are changing

	int aFrame = 0; // current frame for this animation
	int TOTAL_FRAME = 10; // we calculate this for each animation

	// so that we can use these variables from outside this class
	final static int IDLE = 0, WALK = 1, DEAD = 2, DYING = 3;
	final int TOTAL_ANIM = 4;

	// name of the animations
	final String fileName[] = { "idle", "walk", "dead", "dying" };
	// how many frames do we have for each animation
	final int frameNo[] = { 10, 10, 10, 10 };
	// what is the moving speed for each animation
	final int moveSpeed[] = { 0, 0, 0, 0 };

	private Texture spriteTex[][]; // holding all the textures for all the animations

	int movX, movY, originalY; // the speed of moving the model
	float width, height;

	// what is the current selected animation
	int currentAnimation = WALK;
	
	boolean spriteDirection = true;
	int speed = 1;

	int detectXPosition;
	
	//The status used to alter the rendering animations.
	boolean totallyDead = false;
	
	//Determine if the enemy is dead or not.
	boolean deadStatus = false;
	
	//Bullet array to store the position of bullets and will be used in bullet detections.
	float[] bulletsPosi = new float[10];
	
	// constructor to initialize the sprite
	GEnemy(final GL2 gl, int x, int y, long interval, float w, float h) {

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
				String name = "/world/jack/" + fileName[i] + "/" + fileName[i] + (j + 1) + ".png";

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
	}



	// by using time as a parameter, the next animation frame number is calculated
	private void deterMineNextFrame() {
		long endFrameTime = System.currentTimeMillis();

		// interval is the duration of one animation frame
		if ((endFrameTime - startFrameTime) > interval) {
			// go to the next frame
			aFrame = (aFrame + 1) % TOTAL_FRAME;
			startFrameTime = endFrameTime;
			
			//If he has been shot then change the rendering animations.
			if(this.deadStatus == true) {
				this.currentAnimation = DYING; 
			}
			
			//After the rendering animation is done,then change to static rendering: enemy lying on the ground.
			if(this.deadStatus == true && aFrame==9)
			{
				this.totallyDead = true;

			}
			if(this.totallyDead == true)
			this.currentAnimation = DEAD;
			
			// calculating the new position
			final int SPRITE_SIZE = 260;
			movX += this.moveSpeed[this.currentAnimation];
			if (movX > GLUTCanvas.DRAWING_WIDTH / 2)
				movX = -(int) GLUTCanvas.DRAWING_WIDTH / 2 - SPRITE_SIZE / 2;

		}
	}
	
	//The method ensure the quad will move along with enemy.
	public void updateBulletSqr()
	{
		if(this.spriteDirection == true)
		{
			float[] vertex2f1 = new float[] { 
					.3f, .3f, .3f, // The outline drawing color 
					this.movX, this.movY, //The left-bottom corner position(x,y)
					45, 65//The width and height
				};
			bulletDetectSqr = new GCRect(vertex2f1);
		}
		else
		{
			float[] vertex2f1 = new float[] { 
					.3f, .3f, .3f, // The outline drawing color 
					this.movX - 50f, this.movY, //The left-bottom corner position(x,y)
					45, 65//The width and height
				};
			bulletDetectSqr = new GCRect(vertex2f1);
		}
	}
	
	//This method set the boundaries to restrain enemy's moving 
	public void moving(){
		
		int directoinCorrection = 70;
		
		if(this.spriteDirection == true)
			detectXPosition = this.movX + directoinCorrection; 
		else
			detectXPosition = this.movX - directoinCorrection;
		
		int rightBoundary = 150;
		int leftBoundary = -50;
		
		this.movX = this.movX + speed;
		if(detectXPosition > rightBoundary|| detectXPosition < leftBoundary){
			this.speed = -this.speed;
			if(this.spriteDirection == true) {
				this.spriteDirection = false;
				this.movX = this.movX + 50;
			}else {
				this.spriteDirection = true;
				this.movX = this.movX - 50;
			}
		}
	}

	private void renderQuad(final GL2 gl, Texture texture) {
		if(this.deadStatus == false)
		moving();
		
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
		
		if (spriteDirection == true) {
			gl.glRotatef(0, 0, 1, 0);
			gl.glFrontFace(GL_CCW);
		} else {
			gl.glRotatef(180.0f, 0, 1, 0);
			gl.glFrontFace(GL_CW);
		}
		
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
	
	//This is the getter method to get the bullets positions.
	public void getBulletPositions(float[] bulletP)
	{
		this.bulletsPosi = bulletP;
	}

	
	//The most core algorithms which used to detect the bullet kills
	public void  bulletKill()
	{
		
		Point[] bullets = new Point[5];
		
		Point bullet1 = new Point((int)this.bulletsPosi[0], (int)this.bulletsPosi[1]);
			bullets[0] = bullet1;
		Point bullet2 = new Point((int)this.bulletsPosi[2], (int)this.bulletsPosi[3]);
			bullets[1] = bullet2;
		Point bullet3 = new Point((int)this.bulletsPosi[4], (int)this.bulletsPosi[5]);
			bullets[2] = bullet3;
		Point bullet4 = new Point((int)this.bulletsPosi[6], (int)this.bulletsPosi[7]);
			bullets[3] = bullet4;
		Point bullet5 = new Point((int)this.bulletsPosi[8], (int)this.bulletsPosi[9]);
			bullets[4] = bullet5;
	
		for(Point b: bullets)
		{
			if(bulletDetectSqr.isInside(b))
			{
				System.out.println("Enemy is killed!");
				this.deadStatus = true;
				this.width = 60;
				this.height = 60;
				this.movY = this.movY - 5;
				
			}
		}
	}
	
	//This method will return the enemy's positions.
	public float[] getEnemyPosition(){
		
		float[] position = new float[2];
		
		if(this.spriteDirection == true)
		{
			position[0] = this.movX+20;
			position[1] = this.movY+35;
		}
		else
		{
			position[0] = this.movX-20;
			position[1] = this.movY+35;
		}
		
		return position;
	}
	
	//Return the dead status to player.
	public boolean getDeadStatus() {
		return this.deadStatus;
	}
	
	// actual drawing method for the sprite, that renders a texture on a quad
	public void render(final GL2 gl) {
		
		centerPosition = new GPixels(movX-20, movY+35, 5);
		
		deterMineNextFrame();
		updateBulletSqr();
		
		if(this.deadStatus == false)
		bulletKill();

		renderQuad(gl, spriteTex[currentAnimation][aFrame]);

		bulletDetectSqr.render(gl);

	}

}
