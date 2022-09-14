package com.drawing;

import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_CW;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GSpriteKey implements GShape {
	// stores a set of texture coordinate along with a quad information
	// the textures are rendered at fixed interval (time) and repeated
	// translation based animation (horizontal, vertical) can also be
	// illustrated in the class

	boolean spriteDirection;
	boolean keyPressStatus = false;
	boolean keyMap[];
	
	//The key
	GKey key;
	
	//The two traps used to kill the shooter.
	GTrap trapOne;
	GTrap trapTwo;
	//The end check point
	GDoor door;
	
	//Firing muzzle is not part of character image, it is separated
	GMuzzle firingMuzzle;
	//The moving quad of character
	GCRect detectSquare;
	
	GObject congrats;
	
	//The dead status of shooter
	boolean deadStatus = false;
	
	GBullet myBullet1;
	GBullet myBullet2;
	GBullet myBullet3;
	GBullet myBullet4;
	GBullet myBullet5;
	
	int numberOfBullet = 5;
	int bulletCount = 4;
	int remaningBullets = 5;
	
	boolean fireStatus = false;
	
	//The magazine system used to store the bullets.
	GBullet[] magazine;
	
	//Enemy status information is continuously updating.
	float[] enemy1Position = new float[2];
	boolean enemy1DeadStatus = false;
	
	float[] enemy2Position = new float[2];
	boolean enemy2DeadStatus = false;
	
	//Check if player get the key
	boolean getTheKey = false;
	//Check if the game is over or not.
	boolean gamaeOver = false;
	
	

	enum KEYBOARD {
		UP, DOWN, RIGHT, LEFT, RUN, WALK, JUMP, FIRE
	}

	int animationType;
	long startFrameTime = 0;
	long interval = 50; // how fast the animation frames are changing

	int nextFrame = 0; // current frame for this animation
	int maxAnimationFrameCount = 10; // we calculate this for each animation

	final int SPRITE_SIZE = 90;
	// so that we can use these variables from outside this class
	final static int IDLE = 0, WALK = 1, RUN = 2, JUMP = 3, FIRE = 4, DEAD = 5;
	final int TOTAL_ANIM_COUNT = 6;

	// name of the animations
	final String fileName[] = { "idle", "walk", "run", "jump", "Fire", "dead"};
	// how many frames do we have for each animation
	final int frameNo[] = { 10, 10, 8, 12, 10, 10};
	// what is the moving speed for each animation
	final int moveSpeed[] = { 0, 4, 10, 10, 0, 0};

	private Texture spriteTex[][]; // holding all the textures for all the animations

	int spritePosX, spritePosY, spriteBaseHeight; // the speed of moving the model
	float width, height;

	// what is the current selected animation
	int currentAnimationType = IDLE;
	int nextActiveAnimationType = IDLE;

	// collision detection rectangles
	ArrayList<GCRect> collisionRects;
	boolean isInsideBoundary = true;
	boolean outsideBoundary = false;
	
	float[] vertex2f1;

	GPixels spritePixel, leftBottomPixel, rightTopPixel;
	// constructor to initialize the sprite
	GSpriteKey(final GL2 gl, int x, int y, long interval, float w, float h) {
		
		
		this.spritePosX = x; // initial position on the world
		this.spritePosY = y;
		spriteBaseHeight = y;
		this.width = w; // dimension of the sprite
		this.height = h;
		this.spriteDirection = true; // facing right
		this.currentAnimationType = IDLE;
		this.nextActiveAnimationType = WALK;
		this.keyMap = new boolean[KEYBOARD.values().length];

		// frame change and collision detection interval
		this.interval = interval;
		this.startFrameTime = System.currentTimeMillis();
		
		float[] vertex2f = new float[] {
				-50.0f, 20.0f,      //position
				20.0f, 10.0f,     //scaling
				1.0f, 1.0f, 1.0f,   //drawing color
				0.0f, 0.0f, 1.0f    //texture color
		};
		key = new GKey(gl, 15, vertex2f);
		
		
		
		vertex2f1 = new float[] { 
				  -75f, -35f, //The positional information, x and y.
				  150f, 70f,  //The scale rate for this object
				  1.0f, 1.0f,1.0f, //Drawing color
				  0.0f, 0.0f, 1.0f //The outline color.
				  }; 
		congrats = new GObject(gl, 23, vertex2f1);
		
		firingMuzzle = new GMuzzle(gl, 0, 0, 25, 25, 25);
		firingMuzzle.selectAnimation(0);
		
		
		//The objects of different objects
		myBullet1 =new GBullet(gl, 0, 0, 25, 25, 15);
		myBullet2 =new GBullet(gl, 0, 0, 25, 25, 15);
		myBullet3 =new GBullet(gl, 0, 0, 25, 25, 15);
		myBullet4 =new GBullet(gl, 0, 0, 25, 25, 15);
		myBullet5 =new GBullet(gl, 0, 0, 25, 25, 15);
		//Add them into array
		magazine = new GBullet[numberOfBullet];
		magazine[0] = myBullet1;
		magazine[1] = myBullet2;
		magazine[2] = myBullet3;
		magazine[3] = myBullet4;
		magazine[4] = myBullet5;
		
		door = new GDoor(gl, 250, 70, //The positional information, x and y.
				50,  //The frame interval 
				50, 55 //The scale rate for this object(90,85)
			);
		
		
		
		// loading the texture objects
		this.spriteTex = new Texture[TOTAL_ANIM_COUNT][];

		// load all the texture in the array
		for (int i = 0; i < TOTAL_ANIM_COUNT; i++) {
			// allocation memory for all the textures for this row
			this.spriteTex[i] = new Texture[frameNo[i]];

			for (int j = 0; j < frameNo[i]; j++) {
				// calculating the name of the texture
				String name = "/world/bill/" + fileName[i] + "/" + fileName[i] + (j + 1) + ".png";
				

				// storing the texture in the array
				this.spriteTex[i][j] = GTextureUtil.loadTextureProjectDir(gl, name, "PNG");
				this.spriteTex[i][j].setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
				this.spriteTex[i][j].setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			}
		}

		this.spritePixel = new GPixels(spritePosX, spritePosY, 50f);
		this.leftBottomPixel = new GPixels(spritePosX, spritePosY, 50f);
		this.rightTopPixel = new GPixels(spritePosX, spritePosY, 50f);

		this.outsideBoundary = false;
		this.isInsideBoundary = true;
	}

	// the model should be within these two rectangles
	void setCollisionRectangles(ArrayList<GCRect> cRectangles) {
		this.collisionRects = cRectangles;
	}

	// by using this method, the outside class can choose which animation to render
	void selectAnimation(int animationType) {

		// in order to ensure that we do not use an invalid animation number
		if (animationType < 0 || animationType >= this.TOTAL_ANIM_COUNT)
			return;

		this.nextFrame = 0;
		// otherwise, change the animation number as well as the frame-number
		this.currentAnimationType = animationType;
		this.maxAnimationFrameCount = frameNo[animationType];
		// we have to do it immediately, in order to ensure that
		// the frame number in the array is correct for this animation
		this.nextFrame = (this.nextFrame + 1) % this.maxAnimationFrameCount;

		if (animationType != JUMP)
			this.spritePosY = this.spriteBaseHeight;
	}


	// this function checks collision of the sprite with the
	// quad boundaries specified in the collisionRects ArrayList
	public void calculateSpriteCollision() {

		// if there are no collission rectangle boundaries
		// exit the function
		if (this.collisionRects == null)
			return;

		// assume that the sprite is not within boundary
		isInsideBoundary = false;
		GCRect inRect = null;
		
		// get the base point of the model
		int sPx = (int) spritePosX;
		int sPy = (int) spritePosY;

		// because of the texture rotation, the sprite point
		// needs to be adjusted
		final int H_OFFSETCORRECTION = 22, V_OFFSETCORRECTION = 10;
		if (this.spriteDirection == true)
			sPx = sPx + H_OFFSETCORRECTION; // correcting the offset of the quad
		else
			sPx = sPx - H_OFFSETCORRECTION;

		sPy = sPy + V_OFFSETCORRECTION; // correct the offset of the quad

		// this is the point we are going to use for collision calculation
		Point sPoint = new Point(sPx, sPy);
		

		// determine whether it is inside any of the collision rectangles
		for (GCRect rect : this.collisionRects) {

			// is the point inside the current rectangle
			boolean inFlag = rect.isInside(sPoint);
			// if within the current rectangle boundary
			if (inFlag) {

				//if (DrawWindow.DEBUG_OUTPUT) //Comments this line making output correct
//				System.out.println("Collision found ..");
//				System.out.println(sPoint);

				
				isInsideBoundary = true;

				// record the rectangle
				inRect = rect;
				
				// update the displayed points on the screen
				// bottomleft and topright point is displayed as red dot
				leftBottomPixel.setXY(inRect.getLowerLeftPoint());
				rightTopPixel.setXY(inRect.getUpperRightPoint());
				break;
			}
		}
		
		// if inside the boundary of a rectangle
		if (isInsideBoundary == true && inRect != null) {
			// adjust the height of the sprite with respect
			// to this rectangle boundary
			this.spriteBaseHeight = (int) inRect.getBaseHeight();
			this.spritePosY = this.spriteBaseHeight;
		}
		else {
			// the sprite is outside the boundary
			// calculate this only when we are not jumping
			if (nextFrame == maxAnimationFrameCount - 1)
				outsideBoundary = true;
			// }
			//if (DrawWindow.DEBUG_OUTPUT) {
//				System.out.println("No Collision found ..");
//				System.out.println(sPoint);
		//}
		}

		// update the sprite pixel position
		spritePixel.setXY(sPoint);

	}
	
	void endPoint() {
		if(spritePosX >= 250.0f && spritePosY >= 60 && getTheKey == true && this.gamaeOver == false)
		{	
			System.out.println("Congratulations, you made it!!!");
			
			gamaeOver = true;
			door.selectAnimation(1);
			this.calculateSpriteCollision();
		}
		if(spritePosX >= 250.0f && spritePosY >= 60 && getTheKey == false && this.gamaeOver == false)
		{
			System.out.println("You have to get the key before open the door!!");
		}
		
	}

	// removes the sprite from screen
	void removeSpriteFromWorld() {
		if (outsideBoundary == true) {
			spritePosY -= 5;
			this.spriteBaseHeight -= 5;
			
			// reset the game
			if (this.spritePosY < -(GLUTCanvas.DRAWING_HEIGHT / 2 + SPRITE_SIZE / 2)) {
				spritePosX = -280;
				spritePosY = 20;
				this.spriteBaseHeight = 20;
				outsideBoundary = false;
				this.isInsideBoundary = true;
				this.spriteDirection = true; // facing right
				this.currentAnimationType = IDLE;
				this.nextActiveAnimationType = WALK;

				this.selectAnimation(IDLE);
				this.calculateSpriteCollision();
				
				this.remaningBullets = 5;
				this.bulletCount = 4;
			}
		}
	}

	// by using time as a parameter, the next animation frame number is calculated
	private void calculateNextFrame() {
		
//		System.out.println(this.nextFrame);
		
		long endFrameTime = System.currentTimeMillis();

		// interval is the duration of one animation frame
		if ((endFrameTime - startFrameTime) > interval) {
			// go to the next frame
			nextFrame = (nextFrame + 1) % maxAnimationFrameCount;
			// at the last frame, change the animation type

			startFrameTime = endFrameTime;
			
			if(this.deadStatus == true && nextFrame == 9) {
				this.spritePosX = -280;
				this.spritePosY = 20;
				deadStatus = false;
			}
			
			
			if(nextFrame > 8) {
				firingMuzzle.setFiringStatus(false);
			}

			// when we reach to the end of the animation sequence
			// then check for collision
			if (nextFrame == maxAnimationFrameCount - 1) {
				// when we are at the idle state, check collision
				this.calculateSpriteCollision();
				this.endPoint();
			}

			// calculating the new position of the dino

			// for each step what is the height of the animated sprite
			float angleStep = (float) Math.PI / this.frameNo[this.currentAnimationType];
			final int JUMP_HEIGHT = 70;
			if (currentAnimationType == JUMP) {
				float angle = angleStep * (nextFrame);
				spritePosY = (int) (Math.sin(angle) * JUMP_HEIGHT);
				spritePosY = spriteBaseHeight + spritePosY;

				// during the jump, when half of the animation has been
				// rendered then check for collision (at landing)
				if ((nextFrame > maxAnimationFrameCount / 2 + 1) )
				this.calculateSpriteCollision();
				this.endPoint();

			}

			// if there is no keypress event then
			// after the complete animation cycle
			// revert back to idle state
			if (keyPressStatus == false && nextFrame == maxAnimationFrameCount - 1) {
				this.selectAnimation(IDLE);
			}

			// if the sprite is outside the boundary
			// remove it from the world
			if (outsideBoundary == true) {
				this.removeSpriteFromWorld();
				return;
			}


			if (keyMap[KEYBOARD.UP.ordinal()]) {

				// at jump animation the character does not move vertically
				if (currentAnimationType != JUMP) {
					spritePosY += this.moveSpeed[this.currentAnimationType];
					spriteBaseHeight = spritePosY;
				}

				// if it reaches the top, animate from bottom
				if (spritePosY > GLUTCanvas.DRAWING_HEIGHT / 2)
					spritePosY = -(int) GLUTCanvas.DRAWING_HEIGHT / 2 - SPRITE_SIZE / 2;

			}

			if (keyMap[KEYBOARD.DOWN.ordinal()]) {
				// at jump animation the character does not move vertically
				if (currentAnimationType != JUMP) {
					spritePosY -= this.moveSpeed[this.currentAnimationType];
					spriteBaseHeight = spritePosY;
				}

				// if it reaches the bottom, animate from top
				//Honestly, this piece of code makes nonsense in current assignment
				if (spritePosY < -(GLUTCanvas.DRAWING_HEIGHT / 2 + SPRITE_SIZE / 2))
					spritePosY = (int) GLUTCanvas.DRAWING_HEIGHT / 2 + SPRITE_SIZE / 2;

			}

			// transformation correction: when the quad is rotated
			// the texture is placed ahead of the original position
			final int TEX_POSITION_CORRECTION = 50;

			if (keyMap[KEYBOARD.LEFT.ordinal()]) {
				spritePosX -= this.moveSpeed[this.currentAnimationType];
				if (spritePosX < -GLUTCanvas.DRAWING_WIDTH / 2)
					spritePosX = +(int) GLUTCanvas.DRAWING_WIDTH / 2 + SPRITE_SIZE / 2;

				if (this.spriteDirection == true)
					spritePosX += TEX_POSITION_CORRECTION;

				// the character is facing left
				this.spriteDirection = false;

			}

			if (keyMap[KEYBOARD.RIGHT.ordinal()]) {
				
				spritePosX += this.moveSpeed[this.currentAnimationType];
				if (spritePosX > GLUTCanvas.DRAWING_WIDTH / 2)
					spritePosX = -(int) GLUTCanvas.DRAWING_WIDTH / 2 - SPRITE_SIZE / 2;

				if (this.spriteDirection == false)
					spritePosX -= TEX_POSITION_CORRECTION;

				// the character is facing right
				this.spriteDirection = true;

			}
		}
	}

	// event driven function call
	// this function is called from the GLUTCanvas class
	void resetKeyBoardEvent() {
		// there is no keyboard event
		keyPressStatus = false;
	}

	// event driven function call
	// this function is called from the GLUTCanvas class
	void processKeyBoardEvent(int key) {
		// the key press event has been recorded
		keyPressStatus = true;

		// determining which buttons have been pressed
		keyMap[KEYBOARD.UP.ordinal()] = (key == KeyEvent.VK_UP) ? true : false;
		keyMap[KEYBOARD.DOWN.ordinal()] = (key == KeyEvent.VK_DOWN) ? true : false;
		keyMap[KEYBOARD.RIGHT.ordinal()] = (key == KeyEvent.VK_RIGHT) ? true : false;
		keyMap[KEYBOARD.LEFT.ordinal()] = (key == KeyEvent.VK_LEFT) ? true : false;
		keyMap[KEYBOARD.RUN.ordinal()] = (key == KeyEvent.VK_R) ? true : false;
		keyMap[KEYBOARD.WALK.ordinal()] = (key == KeyEvent.VK_W) ? true : false;
		keyMap[KEYBOARD.FIRE.ordinal()] = (key == KeyEvent.VK_F) ? true : false;
		keyMap[KEYBOARD.JUMP.ordinal()] = (key == KeyEvent.VK_J || key == KeyEvent.VK_SPACE) ? true : false;
		//I have modified the code, which makes Sprite jump by press space or J.
	    //keyMap[KEYBOARD.JUMP.ordinal()] = (key == KeyEvent.VK_SPACE) ? true : false;

		// toggle animation selection
		// e.g., pressing walk button again will disable walk animation
		if (keyMap[KEYBOARD.RUN.ordinal()]) {
			nextActiveAnimationType = RUN;
			// flip the animation type
			if (currentAnimationType != IDLE) {
				selectAnimation(IDLE);
			}

		}

		if (keyMap[KEYBOARD.WALK.ordinal()]) {
			nextActiveAnimationType = WALK;
			// flip the animation type
			if (currentAnimationType != IDLE) {
				selectAnimation(IDLE);
			}
		}

		if (keyMap[KEYBOARD.JUMP.ordinal()]) {
			nextActiveAnimationType = JUMP;
			// flip the animation type
			if (currentAnimationType != IDLE) {
				selectAnimation(IDLE);
			}
		}
		
		if (keyMap[KEYBOARD.FIRE.ordinal()]) {
			currentAnimationType = FIRE;
			this.fireStatus = true;
			
			if(remaningBullets != 0)
			{
				firingMuzzle.setFiringStatus(true);
				this.magazine[bulletCount].setFiringStatus(true);
				if(this.spriteDirection == true){
					firingMuzzle.setPosition(this.spritePosX+58, this.spritePosY+15, this.spriteDirection);
					this.magazine[bulletCount].setPosition(this.spritePosX+55, this.spritePosY+15, this.spriteDirection);
				}
				else{
					
					firingMuzzle.setPosition(-this.spritePosX+58, this.spritePosY+15, this.spriteDirection);
					this.magazine[bulletCount].setPosition(-this.spritePosX+55, this.spritePosY+15, this.spriteDirection);
				}
				
				System.out.println("Shot Fired");
				bulletCount--;
				remaningBullets--;
				System.out.println("The remain bullets: " + remaningBullets);
			}
			else
			{
				System.out.println("No ammno!!!!");
			}
		}
		
		// whether the navigation buttons have been pressed
		// if yes, then start the recorded animation type
		if (keyMap[KEYBOARD.UP.ordinal()] || keyMap[KEYBOARD.DOWN.ordinal()] || keyMap[KEYBOARD.LEFT.ordinal()]
				|| keyMap[KEYBOARD.RIGHT.ordinal()])
		{
			// if the character is IDLE then start the next active animation type
			if (currentAnimationType == IDLE) {
				selectAnimation(nextActiveAnimationType);
			}

		}
		
	}

	// this function renders a quad with the nextFrame texture
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
		gl.glTranslatef(spritePosX, spritePosY - 4f, 0.0f);
		gl.glScalef(width, height, 1.0f);

		gl.glEnable(GL_CULL_FACE);
		// depending on the direction, the robot's heading will change
		if (spriteDirection == true) {
			gl.glRotatef(0, 0, 1, 0);
			gl.glFrontFace(GL_CCW);
		} else {
			gl.glRotatef(180.0f, 0, 1, 0);
			gl.glFrontFace(GL_CW);
		}

		gl.glColor3f(1.0f, 1.0f, 1.0f); // drawing color

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

		if (texture != null) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
			texture.disable(gl);
		}

		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glDisable(GL_CULL_FACE);

		gl.glPopMatrix();

	}
	
	//Get the bullet and store them in this array.
	public float[] getBulletPositions(){
		
		float[] bulletsPosi = new float[10];
		
		int index = 0;
		
		for(GBullet b: magazine)
		{
			bulletsPosi[index] = b.getXPostion();
				index++;
			bulletsPosi[index] = b.getYPostion();
				index++;
		}
		
		return bulletsPosi;
	}
	
	//get the enemy1's position.
	public void getEnemy1Positions(float[] p, boolean status, float[] p2, boolean status2){
		
		this.enemy1DeadStatus = status;
		this.enemy1Position = p;

		this.enemy2DeadStatus = status2;
		this.enemy2Position = p2;
		
	}
	
	//Killing method checking if shooter would be killed by traps or melee (touching enemy)
	public void meleeAndTrapKilled() {
		
		//Since all traps location is fixed, not need to pass the information from their objects.
		Point[] trapKillPoint = new Point[2];
		Point trap1 = new Point(0, -75);
		Point trap2 = new Point(270, -25);
		
		trapKillPoint[0] = trap1;
		trapKillPoint[1] = trap2;
		
		//The enemy's position.
		Point enemyPoint1 = new Point((int)this.enemy1Position[0], (int)this.enemy1Position[1]);
		Point enemyPoint2 = new Point((int)this.enemy2Position[0], (int)this.enemy2Position[1]);
		
		
		//check if shooter would be killed by enemy one or not.
		if(this.enemy1DeadStatus == false)
		{
				if(detectSquare.isInside(enemyPoint1))
				{
					deadStatus = true;
					this.currentAnimationType = DEAD;
					this.remaningBullets = 5;
					this.bulletCount = 4;
					
				}
			
		}
		
		//check if shooter would be killed by enemy two or not.
		if(this.enemy2DeadStatus == false)
		{
				if(detectSquare.isInside(enemyPoint2))
				{
					deadStatus = true;
					this.currentAnimationType = DEAD;
					this.remaningBullets = 5;
					this.bulletCount = 4;
					
				}
		}
		
		//check if shooter would be killed by traps.
		for(Point t: trapKillPoint) {
			if(detectSquare.isInside(t)) {
				deadStatus = true;
				this.currentAnimationType = DEAD;
				this.remaningBullets = 5;
				this.bulletCount = 4;
			}
		}
		
	}
	
	//updating the moving quad.
	public void updateDetectSquare()
	{
		if(this.spriteDirection == true)
		{
			float[] vertex2f1 = new float[] { 
					.3f, .3f, .3f, // The outline drawing color 
					this.spritePosX, this.spritePosY, //The left-bottom corner position(x,y)
					35, 65//The width and height
				};
			detectSquare = new GCRect(vertex2f1);
		}
		else
		{
			float[] vertex2f1 = new float[] { 
					.3f, .3f, .3f, // The outline drawing color 
					this.spritePosX - 35f, this.spritePosY, //The left-bottom corner position(x,y)
					35, 65//The width and height
				};
			detectSquare = new GCRect(vertex2f1);
		}
	//	System.out.println("x:"+this.movX + "y:"+this.movY);
	}
	
	//Check if they got the key.
	public void whetherHaveKey() {
		Point keyPos = new Point(-45, 30);
		if(this.detectSquare.isInside(keyPos) && this.getTheKey == false)
		{
			this.getTheKey = true;
			System.out.println("Great, you have got the key now, go the door!!");
		}
	}
	

	
	// actual drawing method for the sprite, that renders a texture on a quad
	public void render(final GL2 gl) {
		// determine the animation frame for this animation
		calculateNextFrame();
		updateDetectSquare();
		if(this.deadStatus == false)
		meleeAndTrapKilled();
		
		door.render(gl);
		
		if(this.gamaeOver == false) {
		// render a quad with the nextFrame texture
		renderQuad(gl, spriteTex[currentAnimationType][nextFrame]);
		}
		
		firingMuzzle.render(gl);
		detectSquare.render(gl);
		
		//System.out.println(myBullet.getXPostion());
		if(this.fireStatus == true){
			for(int i = 4; i >= 0; i--)
			this.magazine[i].render(gl);
		}
		
		
		whetherHaveKey();
		
		if(this.getTheKey == false)
		key.render(gl);
		
		//Congratulation sign pops up.
		if(this.gamaeOver == true)
		congrats.render(gl);
	}

}


