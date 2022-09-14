package com.drawing;

import com.jogamp.opengl.GL2;

public class GMissile implements GShape{
	
	//Those are the attributes of Missile
	GCircle missileHead;
	GQuad missileBody;
	GEllipse flame;

	float initialX;
	float initialY;
	float move;
	float maneuver; 
	
	public GMissile(float initalPositionX, float initalPositionY) {
		initialX = initalPositionX;
		initialY = initalPositionY;
		
		missileHead = new GCircle(initialX, initialY, 2);
		float[] colorPurple = new float[] {0.7f, 0.09f, 0.48f};
		missileHead.setColor(colorPurple);
		
		
		float[] vertex2f = new float[] {
				this.initialX+0.0f, this.initialY+2.0f, 	//top left point 
				this.initialX+15.0f, this.initialY-2.0f, 	//bottom right point
				0.0f, 0.0f, 1.0f, 							// main color 	
				0.0f, 0.0f, 0.0f							//dummy outline color
		};
		missileBody = new GQuad(vertex2f);
		
		flame = new GEllipse(initialX+15.0f, initialY, 4.0f, 1.0f);
		
		//Color red for flame.
		vertex2f = new float[] {0.95f, 0.25f, 0.03f};
		flame.setColor(vertex2f);
		
	}
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		//The moving function of the object.
		if(this.move + this.initialX <= -20.0f)
		{
			this.move = 50.0f;
		}
		maneuver = (float) Math.sin(this.move + this.initialX);
		gl.glTranslatef(this.move = this.move - 0.5f, maneuver,0.0f);
		
		//Render of the objects.
		missileHead.render(gl);
		this.flame.render(gl);
		this.missileBody.render(gl);
		
		gl.glPopMatrix();
	}
}
