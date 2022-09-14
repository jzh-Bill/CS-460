package com.drawing;

import com.jogamp.opengl.GL2;

public class GSun implements GShape{
	
	GCircle sun;
	
	
	public GSun (float vertex2fSun[]) {
		sun = new GCircle(vertex2fSun[0], vertex2fSun[1], vertex2fSun[2]);
		float colorYellow[] = new float[]{1.0f, 1.0f, 0.0f, 0,0f, 0.0f, 0.0f};
		sun.setColor(colorYellow);
	}
	
	public void render(final GL2 gl) {
		sun.render(gl);
	}
}
