package com.drawing;

import java.util.ArrayList;
import java.util.Random;
import com.jogamp.opengl.GL2;

//This class is used to create the lands and floaters.
public class GBaseLand implements GShape {
	
	//All lands and floaters are combined with three parts: Head, middle, and tail.
	//Head and tail are required when creating floaters
	GQuad landHead;
	GQuad landTail;
	GQuad landSoil;
	
	//This array is used to store data informations.
	ArrayList<GQuad> lands;
	float startPosix;
	float startPosiy;
	
	float numberOfSoil;
	int landHeadNum;
	int landMiddleSoilNum;
	int landTailNum;
	

	//The constructor
	public GBaseLand(final GL2 gl, float vertex2f[]) {
		
		lands = new ArrayList<>();
		this.startPosix = vertex2f[0];
		this.startPosiy = vertex2f[1];
		
		this.numberOfSoil = vertex2f[10];
		this.landHeadNum = (int)vertex2f[11];
		this.landMiddleSoilNum = (int)vertex2f[12];
		this.landTailNum = (int)vertex2f[13];
		
		
		//This is to create land head.
		float vertex2f2[] = new float [] {startPosix, startPosiy, 50f, 50f, 0f, 0f, 0f, 0f, 0f, 0f};
		landHead = new GQuad(vertex2f2);
		landHead.loadTexture(gl, "/World/Forest/Tiles/"+ landHeadNum+ ".png");
		landHead.setAlpha(true);
		lands.add(landHead);
		
		//The loop is used to create desired number of middle land.
		for(int i = 0; i < numberOfSoil; i++)
		{
			float vertex2f1[] = new float[] { (startPosix+(1+i)*50f), startPosiy, 50f, 50f, 1f, 1f, 1f, 0f, 0f, 0f};
			landSoil = new GQuad(vertex2f1);
			landSoil.loadTexture(gl,"/World/Forest/Tiles/"+ landMiddleSoilNum+ ".png");
			landSoil.setAlpha(true);
		
			lands.add(landSoil);
		}
		

		//This is used to create the tail of land.
		vertex2f2 = new float [] {startPosix+(numberOfSoil+1)*50f, startPosiy, 50f, 50f, 0f,0f,0f,0f, 0f, 0f};
		landTail = new GQuad(vertex2f2);
		landTail.loadTexture(gl, "/World/Forest/Tiles/"+ landTailNum+ ".png");
		landTail.setAlpha(true);
		lands.add(landTail);
		
	}
		
	
	public void render(final GL2 gl) {
		gl.glPushMatrix();
		
		for(GQuad lan: lands)
			lan.render(gl);
		
		gl.glPopMatrix();
	}
}
