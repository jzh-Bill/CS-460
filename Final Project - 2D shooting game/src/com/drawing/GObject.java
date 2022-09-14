package com.drawing;

import com.jogamp.opengl.GL2;

public class GObject implements GShape{
	
	float vertex2f1[];
	int textureNumber;
	String Path;
	GQuad myObject;
	
	public GObject(final GL2 gl, int textureNum, float vertex2f[]) {
		this.textureNumber = textureNum;

		
		float vertex2f1[] = vertex2f;
		
		this.Path = "/World/Forest/ObjectByNum/" + textureNumber + ".png";
		myObject = new GQuad(vertex2f1);
		myObject.loadTexture(gl, Path);
		myObject.setAlpha(true);
	}
	
	public GObject(final GL2 gl, float vertex2f[]) {
		this.textureNumber = (int)vertex2f[10]; //The last element is the texture number.


		vertex2f1 = new float[11];
		
		for(int i = 0; i < 10; i++) {
			this.vertex2f1[i] = vertex2f[i];
		}

		this.Path = "/World/Forest/ObjectByNum/" + textureNumber + ".png";
		myObject = new GQuad(vertex2f1);
		myObject.loadTexture(gl, Path);
		myObject.setAlpha(true);
	}
	
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		myObject.render(gl);
		gl.glPopMatrix();
	}
}
