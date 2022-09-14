package com.drawing;

import com.jogamp.opengl.GL2;

public class GTrail implements GShape{
	//Set the project as instance.
	GQuad trail;
	
	//The constructor.
	public GTrail (float vertex2f[]) {
		trail = new GQuad(vertex2f);
	}
	
	public void render(final GL2 gl) {
		trail.render(gl);
	}
}
