package com.drawing;

import com.jogamp.opengl.GL2;

public class GUFO implements GShape{
	
	//Those are the attributes of UFO
	float initialX = 0.0f;
	float initialY = 0.0f;
	GEllipse mainBody;
	GEllipse subBodyOne;
	GEllipse bottom;
	GLine leftFeet;
	GLine rightFeet;
	
	float move;
	float dodgeMissilePlace = 0.0f;
	
	//The constructor of this class
	public GUFO(float initialXPosition, float initialYPosition) {
		this.initialX = initialXPosition;
		this.initialY = initialYPosition;
		
		mainBody = new 	GEllipse(initialX, initialY, 8.0f, 3.0f);
		subBodyOne = new 	GEllipse(initialX, initialY+2.0f, 3.0f, 3.0f);
		
		//The vertex of the color.
		float[] silverColor = new float[] {0.5f, 0.51f, 0.54f};
		mainBody.setColor(silverColor);
		
		float[] purpleColor = new float[] {0.54f, 0.08f, 0.81f};
		subBodyOne.setColor(purpleColor);
		
		float[] yellowColor = new float[] {0.95f, 0.95f, 0.08f};
		bottom = new GEllipse(initialX, initialY, 5.0f, 1.0f);
		bottom.setColor(yellowColor);
		
		float[] vertex2f = new float[] {
				initialX-2.0f, initialY,
				initialX-7.0f, initialY-5.0f,	
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f
		};
		leftFeet = new GLine(vertex2f);
		
		vertex2f = new float[] {
				initialX+2.0f, initialY,
				initialX+7.0f, initialY-5.0f,	
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f
		};
		rightFeet = new GLine(vertex2f);
	}
	
	
	public void render(GL2 gl) {
		gl.glPushMatrix();
		
		if(this.move + this.initialX <= -35.0f)
		{
			this.move = 50.0f;
		}
		
		//This function makes UFO move curly, instead of just straight line.
		dodgeMissilePlace = (float) Math.sin(this.move + this.initialX);
		
		//The speed of the UFO is 0.5.
		gl.glTranslatef(this.move = this.move - 0.5f, dodgeMissilePlace,0.0f);
		
		//Render of the objects.
		subBodyOne.render(gl);
		leftFeet.render(gl);
		rightFeet.render(gl);
		mainBody.render(gl);
		bottom.render(gl);
		gl.glPopMatrix();
	}

}
