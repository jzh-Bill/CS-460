package com.drawing;

import com.jogamp.opengl.GL2;

public class GTrail implements GShape{
	
	GQuad trail;
	
	public GTrail (float vertex2f[]) {
		trail = new GQuad(vertex2f);
	}

	public void render(final GL2 gl) {
		trail.render(gl);
	}
}
