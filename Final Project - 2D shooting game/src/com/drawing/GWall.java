package com.drawing;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class GWall implements GShape{
	
	//I used the 2d array as the wall.
	GQuad[][] lands;
	
	float startPosix;
	float startPosiy;
	
	int wallNumber;
	int numberOfWidth;
	int numberOfHeight;
	
	GQuad walls;
	// Constructor, used to create the custom wall.
	public GWall(final GL2 gl, float vertex2f[]) {
		
		
		this.startPosix = vertex2f[0];
		this.startPosiy = vertex2f[1];
		
		this.wallNumber = (int)vertex2f[2];
		this.numberOfWidth = (int)vertex2f[3];
		this.numberOfHeight = (int)vertex2f[4];
		
		lands = new GQuad[numberOfHeight][numberOfWidth];
		//The loop is used to create desired size of wall.
		for(int h = 0; h < numberOfHeight; h++) {
			for(int r = 0; r < numberOfWidth; r++)
			{
				float vertex2f1[] = new float[] { (startPosix+(r)*50f), startPosiy, 50f, 50f, 1f, 1f, 1f, 0f, 0f, 0f};
				walls = new GQuad(vertex2f1);
				walls.loadTexture(gl,"/World/Forest/Tiles/"+ wallNumber+ ".png");
				walls.setAlpha(true);
			
				lands[h][r] = walls;
			}
			startPosiy += 50f;
		}
		
	}
	
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		for(int h = 0; h < numberOfHeight; h++) {
			for(int r = 0; r < numberOfWidth; r++) {	
				lands[h][r].render(gl);
			}
		}
		gl.glPopMatrix();
	}
	
}
