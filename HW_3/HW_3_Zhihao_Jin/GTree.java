package com.drawing;

import com.jogamp.opengl.GL2;

public class GTree implements GShape{
	GEllipse treeTop;     //The local stored information about tree coordinates and radius. 
	float centerXofTree;
	float centerYofTree;
	GQuad stump;
	GCircle apple1;
	GCircle apple2;
	GCircle apple3;
	GCircle apple4;
	GCircle apple5;
	GCircle apple6;
	GCircle apple7;
	float appleColor[] = new float[] {0.86f, 0.20f, 0.18f}; 
	
	//The constructor will only need the center origin point of tree, and the rest object will depend on
	//the origin point.
	public GTree (float vertex2fTree[], float vertex2fstump[]) {
		this.centerXofTree = vertex2fTree[0];
		this.centerYofTree = vertex2fTree[1];
		this.treeTop = new GEllipse(vertex2fTree[0], vertex2fTree[1], vertex2fTree[2], vertex2fTree[3]);
		this.stump = new GQuad(vertex2fstump);
		
		//The apple coordinates are default and depend on the input center origin point coordinates.
		this.apple1 = new GCircle(this.centerXofTree - 4.5f, this.centerYofTree - 5.5f, 1.20f);
		apple1.setColor(this.appleColor);
		
		this.apple2 = new GCircle(this.centerXofTree - 5.5f, this.centerYofTree - 0.5f, 1.6f); 
		apple2.setColor(this.appleColor);
		
		this.apple3 = new GCircle(this.centerXofTree - 5.20f, this.centerYofTree + 4.5f, 1.65f); 
		apple3.setColor(this.appleColor);
		
		this.apple4 = new GCircle(this.centerXofTree, this.centerYofTree + 7.5f, 1.20f); 
		apple4.setColor(this.appleColor);
		
		this.apple5 = new GCircle(this.centerXofTree + 4.0f, this.centerYofTree + 4.5f, 1.6f); 
		apple5.setColor(this.appleColor);
		
		this.apple6 = new GCircle(this.centerXofTree + 5.5f, this.centerYofTree + 0.5f, 1.0f); 
		apple6.setColor(this.appleColor);
		
		this.apple7 = new GCircle(this.centerXofTree + 4.0f, this.centerYofTree - 3.0f, 1.20f); 
		apple7.setColor(this.appleColor);
		
	}
	
	//All render function.
	public void render(final GL2 gl) {
		treeTop.render(gl);
		stump.render(gl);
		apple1.render(gl);
		apple2.render(gl);
		apple3.render(gl);
		apple4.render(gl);
		apple5.render(gl);
		apple6.render(gl);
		apple7.render(gl);
	}
}
