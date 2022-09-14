package com.drawing;

import com.jogamp.opengl.GL2;


//This is class to create the animated object: hot air balloon
public class GTrap implements GShape {
	
	//This vector will store the information of the balloon
	float vertex2f1[];
	int textureNum;
	float xpos;
	float ypos;
	
	String Path;
	GQuad trap;
	
	
	//The constructor will ask the texture name, and object information stored in an array.
	public GTrap(final GL2 gl, int num, float[] vertex2f) {
		this.textureNum = num;
		
		this.xpos = vertex2f[0];
		this.ypos = vertex2f[1];
		
		float vertex2f1[] = vertex2f;
		
		//Create the path.
		this.Path = "/World/Forest/ObjectByNum/" + textureNum + ".png";
		trap = new GQuad(vertex2f1);
		trap.loadTexture(gl, Path);
		trap.setAlpha(true);
	}

	public void render(final GL2 gl) {
		
		gl.glPushMatrix();
		//The animation is controlled by this is statement.

		gl.glTranslatef(xpos, ypos, 0.0f);
		trap.render(gl);
		gl.glPopMatrix();
	}
}
