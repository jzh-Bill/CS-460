package com.drawing;

import com.jogamp.opengl.GL2;

public class GCloud implements GShape{
	
	float originX;          //The stored local original point coordinates of cloud
	float originY;
	float sizeRadius;       //The middle circle cloud radius.
	float movePosition;
	float speed;
	
	GCircle cloudLeft;
	GCircle cloudRight;
	GCircle cloudMiddle;
	
	public GCloud (float vertex2Cloud[]) {
		this.originX = vertex2Cloud[0];
		this.originY = vertex2Cloud[1];
		this.sizeRadius = vertex2Cloud[2];
		this.movePosition = vertex2Cloud[3];
		this.speed = vertex2Cloud[4];
		
		float colorCloud[] = new float[] {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
		
		cloudMiddle = new GCircle(vertex2Cloud[0], vertex2Cloud[1], sizeRadius);
		cloudMiddle.setColor(colorCloud);
		
		//The Other two circle cloud coordinates and radius depends on the middle one.
		cloudLeft = new GCircle(originX - 5.0f, originY - 1.6f, sizeRadius - 1.0f); 
		cloudLeft.setColor(colorCloud);
		
		cloudRight = new GCircle(originX + 4.0f, originY - 1.9f, sizeRadius - 1.0f);
		cloudRight.setColor(colorCloud);
	}
	
	//Render function, draw the whole clouds.
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		//Animating the render function.
		if(this.movePosition + this.originX >= 120.0f)
		{
			this.movePosition = -this.originX - 25;
		}
		
		gl.glTranslatef(this.movePosition = this.movePosition + this.speed, 0.0f,0.0f);
		
		cloudMiddle.render(gl);
		cloudLeft.render(gl);
		cloudRight.render(gl);
		gl.glPopMatrix();
	}
}
