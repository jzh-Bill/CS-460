package com.drawing;

import com.jogamp.opengl.GL2;


//This is class to create the animated object: hot air balloon
public class GKey implements GShape {
	
	//This vector will store the information of the balloon
	float vertex2f1[];
	int textureNum;
	String Path;
	GQuad key;
	
	float move = 1;
	float yPosition;
	//The constructor will ask the texture name, and object information stored in an array.
	public GKey(final GL2 gl, int num, float[] vertex2f) {
		this.textureNum = num;

		
		float vertex2f1[] = vertex2f;
		
		//Create the path.
		this.Path = "/World/Forest/ObjectByNum/" + 16 + ".png";
		key = new GQuad(vertex2f1);
		key.loadTexture(gl, Path);
		key.setAlpha(true);
	}
	
	
	public void render(final GL2 gl) {
		
		gl.glPushMatrix();
		//The animation is controlled by this is statement.
		move -= 0.3;
		yPosition = (float) Math.sin(this.move + 0);
		gl.glTranslatef(0.0f,yPosition, 0.0f);
		key.render(gl);
		gl.glPopMatrix();
	}
}
