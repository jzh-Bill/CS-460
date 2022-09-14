package com.drawing;

import java.util.Arrays;

import com.jogamp.opengl.GL2;

public class GHouse implements GShape{
	
	float vertexMajorQuad[];    //The vertex2f information about servel Quads and Triangles
	float vertexMinorQuad[];
	float vertexTriangle[];
	GQuad majorQuad; 
	GQuad minorQuad;
	GTriangle roof;
	
	//The GHouse Constructor.
	public GHouse(float vertexMaQ[], float vertexMiQ[], float vertexTri[]){
		this.vertexMajorQuad = Arrays.copyOf(vertexMaQ, vertexMaQ.length);
		this.vertexMinorQuad = Arrays.copyOf(vertexMiQ, vertexMiQ.length);
		this.vertexTriangle = Arrays.copyOf(vertexTri, vertexTri.length);
		
		majorQuad = new GQuad(vertexMajorQuad);
		minorQuad = new GQuad(vertexMinorQuad);
		roof = new GTriangle(vertexTriangle); 
		
	}
	
	//Render function.
	public void render(final GL2 gl){
		majorQuad.render(gl);      //call the GQuad and GTriangle render function inside. 
		minorQuad.render(gl);
		roof.render(gl);
	}
	
}
