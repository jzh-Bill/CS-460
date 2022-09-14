package com.drawing;

import com.jogamp.opengl.GL2;

public class GTrianWheels implements GShape{
	//The origin point used to rotate at.
	float originPointX = 0.0f;
	float originPointY = 0.0f;
	
	float startingX = 0.0f;
	float startingY = 0.0f;
	float radius  = 0.0f;

	float theta = 0;
	final float thetaDisp = 360.0f / 100.0f;
	
	GCircle wheel;
	
	GPixels screwOne;
	GPixels screwTwo;
	GPixels screwThree;
	GPixels screwFour;
	
	//Class constructor.
	public GTrianWheels(float startingX, float startingY, float size) {
		
		//Initial wheel position assignment.
		this.startingX = startingX;
		this.startingY = startingY;
		this.radius = size;
		
		float black[] = new float[] {0.0f, 0.0f, 0.0f};
		wheel = new GCircle(originPointX, originPointY, this.radius);
		wheel.setColor(black);
		screwOne = new GPixels(originPointX, originPointY+(this.radius - 1.0f), 10.0f);
		screwTwo = new GPixels(originPointX+(this.radius - 1.0f), originPointY, 10.0f);
		screwThree = new GPixels(originPointX, originPointY-(this.radius - 1.0f), 10.0f);
		screwFour = new GPixels(originPointX-(this.radius - 1.0f), originPointY, 10.0f);
		
	}

	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		//This is initial position transformation, not animating translation.
		//The TrainHead class control the wheels animation.
		gl.glTranslatef(this.startingX, this.startingY, 0.0f);
		
		gl.glRotatef(theta, 0, 0, 1);
		
		//This is rotating loop function.
		theta -= this.thetaDisp;
		if (theta < -360.0f)
			theta = 0.f;
		
		wheel.render(gl);
		screwOne.render(gl);
		screwTwo.render(gl);
		screwThree.render(gl);
		screwFour.render(gl);
		gl.glPopMatrix();
	}
}
