package com.drawing;

import com.jogamp.opengl.GL2;

public class GWindmill implements GShape{
	
	//Those are the attributes of the Windmill.
	GTriangle bladeOne;
	GTriangle bladeTwo;
	GTriangle bladeThree;
	GTriangle bladeFour;
	
	GCircle center;
	
	GQuad mainBody;
	
	float originPointX = 0.0f;
	float originPointY = 0.0f;
	
	float centerX;
	float centerY;
	
	float theta = 0;
	final float thetaDisp = 360.0f / 100.0f;
	
	public GWindmill(float x, float y) {
		this.centerX = x;
		this.centerY = y;
		
		float vertex2Triangle[] = new float[] {
				originPointX, originPointY, //center point
				originPointX+1.0f, originPointY+8.0f, //bottom-right point
				originPointX-1.0f, originPointY+8.0f, //top point
				1.0f, 0.0f, 1.0f //drawing color
		};
		bladeOne = new GTriangle(vertex2Triangle);
		
		vertex2Triangle = new float[] {
				originPointX, originPointY, //center point
				originPointX+8.0f, originPointY-1.0f, //bottom-right point
				originPointX+8.0f, originPointY+1.0f, //top point
				1.0f, 0.0f, 1.0f //drawing color
		};
		bladeTwo = new GTriangle(vertex2Triangle);
		
		vertex2Triangle = new float[] {
				originPointX,originPointY, //center point
				originPointX-1.0f, originPointY-8.0f, //bottom-right point
				originPointX+1.0f,originPointY-8.0f, //top point
				1.0f, 0.0f, 1.0f //drawing color
		};
		bladeThree = new GTriangle(vertex2Triangle);
		
		vertex2Triangle = new float[] {
				originPointX, originPointY, //center point
				originPointX-8.0f, originPointY+1.0f, //bottom-right point
				originPointX-8.0f, originPointY-1.0f, //top point
				1.0f, 0.0f, 1.0f //drawing color
		};
		bladeFour = new GTriangle(vertex2Triangle);
		
		center = new GCircle(this.centerX, this.centerY, 1.0f);
		
		float[] vertexQuad = new float[] {
				centerX-0.5f, centerY, // left top point
				centerX+0.5f, centerY-20.0f, // bottom right point
				0.0f, 0.0f, 0.0f, //main color 
				0.0f, 0.0f, 0.0f //outline color
		};
		
		mainBody = new GQuad(vertexQuad);
	}
	
	public void render(GL2 gl) {
		//First render the blades which need to be rotated.
		mainBody.render(gl);
		
		gl.glPushMatrix();
		
		gl.glTranslatef(centerX, centerY, 0.0f);
		gl.glRotatef(theta, 0, 0, 1);
		theta -= this.thetaDisp;
		if (theta < -360.0f)
			theta = 0.f;
		
		bladeOne.render(gl);
		bladeTwo.render(gl);
		bladeThree.render(gl);
		bladeFour.render(gl);

		gl.glPopMatrix();
		//Then render the objects which do not have to rotate.
		center.render(gl);
	}
}
