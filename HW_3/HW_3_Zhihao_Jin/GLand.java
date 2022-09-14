package com.drawing;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GLand implements GShape{
	
	float x_c;     //The local stored lands coordinates and radius information.
	float y_c;
	float radius;
	float color[];
	GCircle myLand;
	
	//The constructor for land, containing coordinates and radius information.
	public GLand(float x, float y, float r){
		this.x_c = x;
		this.y_c = y;
		this.radius = r;
		
		this.color = new float[] { 0.33f, 0.611f, 0.172f, 0f, 0f,0f };
		myLand = new GCircle(this.x_c, this.y_c, this.radius);
	}
	
	//The mutator that change the default color.
	public void set_color(float color[]){ 
		this.color = Arrays.copyOf(color, color.length); 
	}
	
	//Render function.
	public void render(final GL2 gl){
		myLand.setColor(this.color);
		myLand.render(gl);
	}
}
