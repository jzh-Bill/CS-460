package com.drawing;

import com.jogamp.opengl.GL2;

public class GSun implements GShape{
	
	//Use the GCricle to create project named sun.
	GCircle sun;
	
	float centerX = 0.0f;
	float centerY = 0.0f;
	
	float realCenterX;
	float realCenterY;
	float scaleDegree = 0.0f;
	
	GLine beamOne;
	GLine beamTwo;
	GLine beamThree;
	GLine beamFour;
	
	float theta = 0;
	final float thetaDisp = 360.0f / 360.0f;
	
	
	public GSun (float vertex2fSun[]) {
		sun = new GCircle(vertex2fSun[0], vertex2fSun[1], vertex2fSun[2]);
		
		this.realCenterX = vertex2fSun[0];
		this.realCenterY = vertex2fSun[1];
		
		//Set the default color.
		float colorYellow[] = new float[]{1.0f, 1.0f, 0.0f, 0,0f, 0.0f, 0.0f};
		sun.setColor(colorYellow);
		
		//Use the vertex2f to input the attributes of different sun beams
		float[] vertex2f = new float[] {
				this.centerX - 10.0f, this.centerY, 
				this.centerX + 10.0f, this.centerY,
				0.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
		};
		beamOne = new GLine(vertex2f);
		
		vertex2f = new float[] {
				this.centerX, this.centerY + 10.0f, 
				this.centerX, this.centerY-10.0f,
				0.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
		};
		beamTwo = new GLine(vertex2f);
		
		vertex2f = new float[] {
				this.centerX+7.07f, this.centerY + 7.07f, 
				this.centerX-7.07f, this.centerY-7.07f,
				0.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
		};
		beamThree = new GLine(vertex2f);
		
		vertex2f = new float[] {
				this.centerX-7.07f, this.centerY + 7.07f, 
				this.centerX+7.07f, this.centerY - 7.07f,
				0.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
		};
		beamFour = new GLine(vertex2f);
		
	}
	
	public void render(final GL2 gl) {
		
		gl.glPushMatrix();
		
		gl.glTranslatef(this.realCenterX, this.realCenterY, 0.0f);
		scaleDegree = scaleDegree + 0.05f;
		gl.glScaled((float) Math.cos(scaleDegree),(float) Math.cos(scaleDegree) , 1.0f);
		gl.glRotatef(theta, 0, 0, 1);
		
		//This determines the speed of those beam rotating around the sun.
		theta -= this.thetaDisp;
		if (theta < -360.0f)
			theta = 0.f;
		
		beamOne.render(gl);
		beamTwo.render(gl);
		beamThree.render(gl);
		beamFour.render(gl);
		gl.glPopMatrix();
		
		sun.render(gl);
	}
}
