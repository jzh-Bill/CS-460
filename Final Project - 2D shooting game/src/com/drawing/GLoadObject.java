package com.drawing;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.jogamp.opengl.GL2;

public class GLoadObject {
	
	//This class is mainly use to store the load file codes.
	public GLoadObject()
	{
		
	}
	
	public void loadObjectDataFromFile(final GL2 gl, ArrayList<GShape> drawingArtObjects, ArrayList<GCRect> collisionRects) {

		collisionRects = new ArrayList<>();

		String textPath = "/world/Floaters.txt";
		String texPath = System.getProperty("user.dir") + textPath;
		File myFile = new File(texPath);
		int count = 0;
		try (Scanner myStream = new Scanner(myFile)) {
			//vertex2f is used to store the information of GAirHotBalloon
			float [] vertex2f = new float[] {0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};
			
			//vertex2f2 is used to store the information of GObjects
			float [] vertex2f2 = new float[] {0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f};
			
			//vertex2f3 is used to store the information of GDragon
			float [] vertex2f3 = new float[] {0f,1f,2f,3f,4f};
			
			//vertex2f4 is used to store the information of GBaseLand
			float [] vertex2f4 = new float[] {0f,1f,2f,3f,4f,5f,6f,7f,8f,9f,10f,11f,12f,13f};
			
			
			// read all the
			while (myStream.hasNextLine()) {
				String values = myStream.nextLine();
				if (values.equals("[GObject]")) {
					// read three lines of data
					//First line: 2 position values
					
					String data = myStream.nextLine();
					
					int indexSlash = data.indexOf("//");
					String dataVal = data.substring(0, indexSlash);
					String dataElements[] = dataVal.split(",");
					if(dataElements.length >=1)
					{
						vertex2f2[0] = Float.parseFloat(dataElements[0]);
						vertex2f2[1] = Float.parseFloat(dataElements[1]);
					}
					
					// second line: 2 scaling ratio values
					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					
					
					dataElements = dataVal.split(",");
					
					if (dataElements.length >= 1) {
						vertex2f2[2] = Float.parseFloat(dataElements[0]);
						vertex2f2[3] = Float.parseFloat(dataElements[1]);				
					}
					
					// third line: color values
					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length >= 1) {
						vertex2f2[4] = Float.parseFloat(dataElements[0]);
						vertex2f2[5] = Float.parseFloat(dataElements[1]);
						vertex2f2[6] = Float.parseFloat(dataElements[2]);
					}
					
					// Fourth line: outline color values
					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length >= 1) {
						vertex2f2[7] = Float.parseFloat(dataElements[0]);
						vertex2f2[8] = Float.parseFloat(dataElements[1]);
						vertex2f2[9] = Float.parseFloat(dataElements[2]);
					}
					
					// Fifth line: texture values
					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length >= 1) {
						vertex2f2[10] = Float.parseFloat(dataElements[0]);
					}
					
					// reading an empty line
					if (myStream.hasNextLine())
					myStream.nextLine();
						
					count++;
					// adding the GCRect object in the ArrayList
						
					GObject myObject = new GObject(gl, vertex2f2);
					drawingArtObjects.add(myObject);
					}
					
					if (values.equals("[GBaseLand]")) {
						// read three lines of data
						// first line: 2 position values
						
						String data = myStream.nextLine();
						
						int indexSlash = data.indexOf("//");
						String dataVal = data.substring(0, indexSlash);
						String dataElements[] = dataVal.split(",");
						if(dataElements.length >=1)
						{
							vertex2f4[0] = Float.parseFloat(dataElements[0]);
							vertex2f4[1] = Float.parseFloat(dataElements[1]);
						}
						
						// second line: texture scaling values
						data = myStream.nextLine();
						indexSlash = data.indexOf("//");
						dataVal = data.substring(0, indexSlash);
						
						
						dataElements = dataVal.split(",");
						
						if (dataElements.length >= 1) {
							vertex2f4[2] = Float.parseFloat(dataElements[0]);
							vertex2f4[3] = Float.parseFloat(dataElements[1]);				
						}
						
						// third line: color values
						data = myStream.nextLine();
						indexSlash = data.indexOf("//");
						dataVal = data.substring(0, indexSlash);
						dataElements = dataVal.split(",");
						if (dataElements.length >= 1) {
							vertex2f4[4] = Float.parseFloat(dataElements[0]);
							vertex2f4[5] = Float.parseFloat(dataElements[1]);
							vertex2f4[6] = Float.parseFloat(dataElements[2]);
						}
						
						// Fourth line: outline color values
						data = myStream.nextLine();
						indexSlash = data.indexOf("//");
						dataVal = data.substring(0, indexSlash);
						dataElements = dataVal.split(",");
						if (dataElements.length >= 1) {
							vertex2f4[7] = Float.parseFloat(dataElements[0]);
							vertex2f4[8] = Float.parseFloat(dataElements[1]);
							vertex2f4[9] = Float.parseFloat(dataElements[2]);
						}
						
						//(Number of middle lands), (Land head texture number),(Middle land texture number),(Tail land texture number)
						data = myStream.nextLine();
						indexSlash = data.indexOf("//");
						dataVal = data.substring(0, indexSlash);
						dataElements = dataVal.split(",");
						if (dataElements.length >= 1) {
							vertex2f4[10] = Float.parseFloat(dataElements[0]);
							vertex2f4[11] = Float.parseFloat(dataElements[1]);
							vertex2f4[12] = Float.parseFloat(dataElements[2]);
							vertex2f4[13] = Float.parseFloat(dataElements[3]);
						}
						
							
						// reading an empty line
						if (myStream.hasNextLine())
						myStream.nextLine();
							
						count++;
						// adding the GCRect object in the ArrayList

						GBaseLand myLand = new GBaseLand(gl, vertex2f4);
						drawingArtObjects.add(myLand);
					}
			}
		} catch (Exception e) {
			System.out.println("File reading error");
		}

		if (DrawWindow.DEBUG_OUTPUT)
			System.out.println(count + " objects loaded");

	}

}
