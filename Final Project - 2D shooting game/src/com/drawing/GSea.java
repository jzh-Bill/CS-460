package com.drawing;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
 
//The class will create the water down in the screen
public class GSea implements GShape {
	
	ArrayList<GQuad> waves1;
	
	//We use the GQuad to create the wave, and the size is defined by users.
	GQuad wave;
	
	//The moving speed is default.
	float movSpeed = 0.5f;
	float xStartPos;
	float xEndPos;
	
	float currentxPosi;
	
	//The constructor is asking number of waves(size of sea), starting point and ending position.
	public GSea(final GL2 gl, int numberOfWaves, float initialxPos, float endxPos){
		waves1 = new ArrayList<>();
		
		this.xStartPos = initialxPos;
		this.xEndPos = endxPos;
		
		this.currentxPosi = initialxPos;
		
		for(int i = 0; i < numberOfWaves; i++)
		{
			//The width of single wave is default in order to fit the screen
			float vertex2f[] = new float[] { (i*50f), 0f, 50f, 50f, 1f, 1f, 1f, 0f, 0f, 0f};
			wave = new GQuad(vertex2f);
			
			//The path is fixed since it has only one texture.
			wave.loadTexture(gl, "/World/Forest/Tiles/17.png");
			wave.setAlpha(true);
		
			waves1.add(wave);
		}
	}
	
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		//This statement controls the portal of the one of the two waves in order to make them look contiguous.
		if(currentxPosi == xEndPos)
		{
			this.currentxPosi = 300;
		}
		gl.glTranslatef(currentxPosi = currentxPosi - movSpeed, -175.0f, 0.0f);
		for(GQuad wav: waves1)
			wav.render(gl);
		
		gl.glPopMatrix();
		

	}
}
