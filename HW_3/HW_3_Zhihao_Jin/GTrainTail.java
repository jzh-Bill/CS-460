package com.drawing;

import com.jogamp.opengl.GL2;

public class GTrainTail implements GShape{
	
	float originPointX;
	float originPointY;
	float move = 0.0f;
	
	GQuad mainBody;
	
	GTrianWheels frontWheel;
	GCircle frontWheelHub;
	
	GTrianWheels backWheel;
	GCircle backWheelHub;
	
	
	public GTrainTail(float vertex2f[]) {
		this.originPointX = vertex2f[0];
		this.originPointY = vertex2f[1];
		
		float vertex2fMainBody[] = new float[] {
				this.originPointX, this.originPointY + 14.0f,
				this.originPointX + 23.0f, this.originPointY,
				0.667f, 0.47f, 0.258f, 							// main color 	
				0.0f, 0.0f, 0.0f							//dummy outline color
		};
		mainBody = new GQuad(vertex2fMainBody);
		
		//The vertex2f of drawing color.
		float black[] = new float[] {0.0f, 0.0f, 0.0f};
		float pink[] = new float[] {0.97f, 0.263f, 0.314f};
		
		//Set the relevant color.
		frontWheel = new GTrianWheels(originPointX + 6.0f, originPointY, 4.0f);
		
		frontWheelHub = new GCircle(originPointX + 6.0f, originPointY, 2.5f);
		frontWheelHub.setColor(pink);
		
		backWheel = new GTrianWheels(originPointX + 17.0f, originPointY, 4.0f);
		
		backWheelHub = new GCircle(originPointX + 17.0f, originPointY, 2.5f);
		backWheelHub.setColor(pink);
	}
	
	
	
	public void render(final GL2 gl) {
		//Animating the object
		gl.glPushMatrix();
		if(this.move + this.originPointX >= 120.0f)
		{
			this.move = -this.originPointX - 25;
		}
		
		gl.glTranslatef(this.move = this.move + 0.5f, 0.0f,0.0f);

		mainBody.render(gl);
		frontWheel.render(gl);
		frontWheelHub.render(gl);
		
		backWheel.render(gl);
		backWheelHub.render(gl);
		
		gl.glPopMatrix();
	}
}
