package com.drawing;

import com.jogamp.opengl.GL2;

public class GTrainHead implements GShape{
	
	float originPointX;
	float originPointY;
	float move = 0.0f;
	
	GQuad mainBody;
	GQuad controlRoom;
	GQuad roof;
	GQuad trainChimney;
	GQuad light;
	GQuad tailHook;
	
	GTrianWheels frontWheel;
	GCircle frontWheelHub;
	
	GTrianWheels backWheel;
	GCircle backWheelHub;
	
	public GTrainHead(float vertex2fOrigin[]) {
		this.originPointX = vertex2fOrigin[0];
		this.originPointY = vertex2fOrigin[1];
		
		float vertex2f[] = new float[] {
				this.originPointX, this.originPointY + 14.0f, //top left point 
				this.originPointX + 26.0f, this.originPointY, //bottom right point
				0.0f, 0.0f, 1.0f, 							  // main color 	
				0.0f, 0.0f, 0.0f							  //dummy outline color
		};
		mainBody = new GQuad(vertex2f);
		
		vertex2f = new float[] {
				this.originPointX + 3.0f, this.originPointY + 26.0f,  //top left point 
				this.originPointX + 16.0f, this.originPointY + 14.0f, //bottom right point
				1.0f, 0.0f, 0.0f, 							          // main color 	
				0.0f, 0.0f, 0.0f							          //dummy outline color
		};
		controlRoom = new GQuad(vertex2f);
		
		vertex2f = new float[] {
				this.originPointX + 3.0f, this.originPointY + 28.0f,   //top left point 
				this.originPointX + 16.0f, this.originPointY + 25.0f,  //bottom right point
				0.0f, 0.0f, 1.0f, 						               // main color 	
				0.0f, 0.0f, 0.0f							           //dummy outline color
		};
		roof = new GQuad(vertex2f);
		
		vertex2f = new float[] {
				this.originPointX + 20.0f, this.originPointY + 19.5f, //top left point 
				this.originPointX + 23.0f, this.originPointY + 14.0f, //bottom right point
				0.0f, 0.0f, 1.0f, 					         		  // main color 	
				0.0f, 0.0f, 0.0f							          //dummy outline color
		};
		trainChimney = new GQuad(vertex2f);
		
		vertex2f = new float[] {
				this.originPointX + 26.0f, this.originPointY + 10.0f, //top left point 
				this.originPointX + 28.0f, this.originPointY + 3.5f,  //bottom right point
				0.0f, 0.0f, 0.0f, 							          // main color 	
				0.0f, 0.0f, 0.0f							          //dummy outline color
		};
		light = new GQuad(vertex2f);
		
	    vertex2f = new float[] {
				this.originPointX - 8.0f, this.originPointY + 4.5f, //top left point 
				this.originPointX, this.originPointY + 2.0f,        //bottom right point
				0.0f, 0.0f, 1.0f, 							        // main color 	
				0.0f, 0.0f, 0.0f							        //dummy outline color
		};
	    this.tailHook = new GQuad(vertex2f);
	    
		//backWheel
		float vertex2fCircle[] = new float[] {
				this.originPointX + 8.0f,
				this.originPointY + 3.0f,
				7.0f
		};
		backWheel = new GTrianWheels(vertex2fCircle[0],vertex2fCircle[1], vertex2fCircle[2]);
		
		float black[] = new float[] {
			0.0f, 0.0f, 0.0f
		};
		
		//backWheelHub
		vertex2fCircle = new float[] {
				this.originPointX + 8.0f,
				this.originPointY + 3.0f,
				4.5f
		};
		backWheelHub = new GCircle(vertex2fCircle[0],vertex2fCircle[1], vertex2fCircle[2]);
		
		float pink[] = new float[] {
			0.97f, 0.263f, 0.314f
		};
		backWheelHub.setColor(pink);
		
		//frontWheel
		vertex2fCircle = new float[] {
				this.originPointX + 21.0f,
				this.originPointY + 0.0f,
				4.0f
		};
		frontWheel = new GTrianWheels(vertex2fCircle[0], vertex2fCircle[1], vertex2fCircle[2]);
	
		
		//frontWheelHub
		vertex2fCircle = new float[] {
				this.originPointX + 21.0f,
				this.originPointY + 0.0f,
				2.5f
		};
		frontWheelHub = new GCircle(vertex2fCircle[0],vertex2fCircle[1], vertex2fCircle[2]);
		frontWheelHub.setColor(pink);
	}
	
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		//Animating the render function.
		if(this.move + this.originPointX >= 120.0f)
		{
			this.move = -this.originPointX - 25;
		}
		
		gl.glTranslatef(this.move = this.move + 0.5f, 0.0f,0.0f);
		
		this.mainBody.render(gl);
		this.controlRoom.render(gl);
		this.roof.render(gl);
		this.trainChimney.render(gl);
		this.light.render(gl);
		this.tailHook.render(gl);
		
		this.frontWheel.render(gl);
		this.backWheel.render(gl);
		this.backWheelHub.render(gl);
		this.frontWheelHub.render(gl);
		
		gl.glPopMatrix();
	}
}
