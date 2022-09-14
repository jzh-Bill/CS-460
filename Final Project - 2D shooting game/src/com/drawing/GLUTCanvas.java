package com.drawing;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

// import com.jogamp.opengl.util.gl2.GLUT;

/*
* JOGL 2.0 Program Template (GLCanvas) This is a "Component" which can be added
* into a top-level "Container". It also handles the OpenGL events to render
* graphics.
*/
@SuppressWarnings("serial")
class GLUTCanvas extends GLCanvas implements GLEventListener {

	public static int CANVAS_WIDTH = 1200; // width of the drawable
	public static int CANVAS_HEIGHT = 700; // height of the drawable

	public static final float DRAWING_WIDTH = 600f, DRAWING_HEIGHT = 350f; //350
	public static float GL_Width, GL_Height;
	// Setup OpenGL Graphics Renderer
	GDrawOrigin myOrigin;
	GKeyBoard keyBoard;


	//GHotAirBalloon myHotAirBalloon; //HotAirBolloon has been initialized in the file.
	GSea mySeaOne;
	GSea mySeaTwo;
	
	GLoadObject myLoadObjects;

	
	//GBaseLand myBaseLandOne; initialized in the file
	GBaseLand myBaseLandTwo;
	
	
	//The walls we used to decorate the scene.
	GWall myWall;
	GWall myWallTwo;
	GWall myBaseWall;
	GWall myUpperWall;
	
	GBaseLand myFloatLandOne;
	GBaseLand myFloatLandTwo;
	GBaseLand myFloatLandThree;


	GEnemy badGuyOne;
	GEnemyTwo badGuyTwo;
	
	GPatch myPatch;
	GSpriteKey spriteCharacter;
	//GDragon myDragon;  initialized in the file


	ArrayList<GShape> drawingArtObjects;
	ArrayList<GCRect> collisionRects;

	/** Constructor to setup the GUI for this Component */
	public GLUTCanvas(GLCapabilities capabilities, GKeyBoard kb) {

		super(capabilities);


		// creating a canvas for drawing
		// GLCanvas canvas = new GLCanvas(capabilities);

		this.addGLEventListener(this);
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.keyBoard = kb;
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * 
	 * Called back immediately after the OpenGL context is initialized. Can be used
	 * to perform one-time initialization. Run only once.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		// ----- Your OpenGL initialization code here -----
		GLU glu = new GLU();

		GL_Width = DRAWING_WIDTH / 2.0f;
		GL_Height = DRAWING_HEIGHT / 2.0f;
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		// gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D
		glu.gluOrtho2D(-GL_Width, GL_Width, -GL_Height, GL_Height); // canvas

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glColor3f(1.0f, 1.0f, 1.0f); // drawing color


		// origin
		myOrigin = new GDrawOrigin(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		// learn quad initialization
		float vertex2f[] = new float[] { 105f, 0f, 100f, 100f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		// learnTriangle = new GTriangle(vertex2f);
		// learnTriangle.loadTexture(gl, "/world/Texture.png");

		// patch with texture
		myPatch = new GPatch(-300, -175, 600, 350, 3, "/world/Background.png");
		
		
		badGuyOne = new GEnemy(gl, 50, -82, //The positional information, x and y.
								50,  //The frame interval 
								50, 55 //The scale rate for this object(90,85)
							);
		
		badGuyTwo = new GEnemyTwo(gl, 50, 20, //The positional information, x and y.
				50,  //The frame interval 
				50, 55 //The scale rate for this object(90,85)
			);
		
		
		
		float vertex2f1[] = new float[] {};
		//myHotAirBalloon = new GHotAirBalloon(gl, "HotAirBalloon", vertex2f1);

		mySeaOne = new GSea(gl, 12, -300f, -900f);
		mySeaTwo = new GSea(gl, 12, 300f, -900f);
		//myHotAirBalloon = new GQuad(0, 0, 50, 50, 3, "/world/HotAirBalloon.png");

	
		vertex2f1 = new float[] { -80.0f, 30.0f, 150f, 100f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};


		// character controlled sprite animation

		spriteCharacter = new GSpriteKey(gl, -280, 20, //The positional information, x and y.
											   32 ,  //The frame interval 
											   65, 55 //The scale rate for this object(90,85)
											   );
		spriteCharacter.selectAnimation(0);

		
		
		vertex2f1 = new float[] {-300.0f, -130.0f, //The position of the wall 
								22,        //render texture of wall
								2f, 2f};   //width and height
		myWall = new GWall(gl, vertex2f1);
		
		vertex2f1 = new float[] {-200.0f, -130.0f, //The position of the wall 
								22, 			 //render texture of wall
								2f, 1f}; 		//width and height
		myWallTwo = new GWall(gl, vertex2f1);
		
		vertex2f1 = new float[] {-300.0f, -175.0f,  //The position of the wall 
									22, 			//render texture of wall
									9f, 1f};		//width and height
		myBaseWall = new GWall(gl, vertex2f1);
		
		vertex2f1 = new float[] {-300.0f, 125.0f, //The position of the wall 
								27, 			//render texture of wall
								12f, 1f};		//width and height
		myUpperWall = new GWall(gl, vertex2f1);
		
		// rectangle object
		vertex2f1 = new float[] { .0f, .0f, .0f, // The outline drawing color 
									-300f, 20f,  //The left-bottom corner position(x,y)
									100f, 100f //The width and height
									};
		GCRect baseBoundary = new GCRect(vertex2f1);
		
		
//		vertex2f1 = new float[] {-275f, -140f, 50f, 50f, 0f, 0f, 0f, 0f, 0f, 0f, 3f, 1f, 2f, 3f};
//		myBaseLandOne = new GBaseLand(gl, vertex2f1);  initialed in the  file
		
		vertex2f1 = new float[] { .3f, .3f, .3f, // The outline drawing color 
									-200f, -30f,  //The left-bottom corner position(x,y)
									100f, 100f //The width and height
									};
		GCRect middleBoundary1 = new GCRect(vertex2f1);
		
		vertex2f1 = new float[] {10f, -140f, //The positional information, x and y.
								50f, 50f, //The scale rate for this object
								0f, 0f, 0f, //Drawing color
								0f, 0f, 0f, //The outline color.
								0f, 1f, 2f, 3f//(Number of middle lands), (Land head texture number),(Middle land texture number),(Tail land texture number)
								};
		myBaseLandTwo = new GBaseLand(gl, vertex2f1);
		
		vertex2f1 = new float[] { .3f, .3f, .3f, // The outline drawing color 
									-100f, -80f, //The left-bottom corner position(x,y)
									250f, 100f//The width and height
								};
		GCRect middleBoundary2 = new GCRect(vertex2f1);
		
		vertex2f1 = new float[] {200f, -80f, //The positional information, x and y.
								50f, 50f, //The scale rate for this object
								0f, 0f, 0f, //Drawing color
								0f, 0f, 0f, //The outline color.
								0f, 19f, 20f, 21f //(Number of middle lands), (Land head texture number),(Middle land texture number),(Tail land texture number)
								};
		myFloatLandOne = new GBaseLand(gl, vertex2f1);
		
		vertex2f1 = new float[] { .3f, .3f, .3f, // The outline drawing color 
									200f, -30f, //The left-bottom corner position(x,y)
									100f, 70f//The width and height
								};
		GCRect middleBoundary3 = new GCRect(vertex2f1);
		
		vertex2f1 = new float[] {-50f, -30f,//The positional information, x and y.
									50f, 50f, //The scale rate for this object
									0f, 0f, 0f, //Drawing color
									0f, 0f, 0f, //The outline color.
									2f, 19f, 20f, 21f//(Number of middle lands), (Land head texture number),(Middle land texture number),(Tail land texture number)
									};
		myFloatLandTwo = new GBaseLand(gl, vertex2f1); 
		
		
		vertex2f1 = new float[] { .3f, .3f, .3f, -50f, 20f, 200f, 150f};
		GCRect upperBoundary4 = new GCRect(vertex2f1);
		
		vertex2f1 = new float[] {
				200f, 20f, 
				50f, 50f, 
				0f, 0f, 0f, 
				0f, 0f, 0f, 
				0f, 19f, 20f, 21f};
		myFloatLandThree = new GBaseLand(gl, vertex2f1); 
		
		vertex2f1 = new float[] { .3f, .3f, .3f, 200f, 70f, 100f, 100f};
		GCRect upperBoundary5 = new GCRect(vertex2f1);
		
		// collision rectangles
		collisionRects = new ArrayList<>();
		collisionRects.add(baseBoundary);
		collisionRects.add(middleBoundary1);
		collisionRects.add(middleBoundary2);
		collisionRects.add(middleBoundary3);
		collisionRects.add(upperBoundary4);
		collisionRects.add(upperBoundary5);
		
		spriteCharacter.setCollisionRectangles(collisionRects);

		// adding them all in the arrayList
		drawingArtObjects = new ArrayList<GShape>();
		
		drawingArtObjects.add(myPatch);
		
		//File load functions
		
		//loadObjectDataFromFile(gl);
		myLoadObjects = new GLoadObject();
		
		drawingArtObjects.add(myFloatLandOne);
		drawingArtObjects.add(myFloatLandTwo);
		drawingArtObjects.add(myFloatLandThree);
		//drawingArtObjects.add(myBush);
		drawingArtObjects.add(myWall);
		drawingArtObjects.add(myWallTwo);
		
		drawingArtObjects.add(myUpperWall);
		
		drawingArtObjects.add(badGuyOne);
		drawingArtObjects.add(badGuyTwo);
		drawingArtObjects.add(spriteCharacter);
		
		myLoadObjects.loadObjectDataFromFile(gl, drawingArtObjects, collisionRects);
		drawingArtObjects.add(myBaseWall);
		drawingArtObjects.add(mySeaOne);
		drawingArtObjects.add(mySeaTwo);
		
		drawingArtObjects.add(baseBoundary);
		drawingArtObjects.add(middleBoundary1);
		drawingArtObjects.add(middleBoundary2);
		drawingArtObjects.add(middleBoundary3);
		drawingArtObjects.add(upperBoundary4);
		drawingArtObjects.add(upperBoundary5);
		
		
	}
	



	/**
	 * Call-back handler for window re-size event. Also called when the drawable is
	 * first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		// Have to add this in order for the canvas to properly scale in the window
		// Found at https://forum.jogamp.org/canvas-not-filling-frame-td4040092.html
		double dpiScalingFactorX = ((Graphics2D) getGraphics()).getTransform().getScaleX();
		double dpiScalingFactorY = ((Graphics2D) getGraphics()).getTransform().getScaleY();
		width = (int) (width / dpiScalingFactorX);
		height = (int) (height / dpiScalingFactorY);

		if (DrawWindow.DEBUG_OUTPUT)
		System.out.println(width + ":" + height);

		GLUTCanvas.CANVAS_HEIGHT = height;
		GLUTCanvas.CANVAS_WIDTH = width;

		// we want this aspect ratio in our drawing
		float target_aspect = DRAWING_WIDTH / DRAWING_HEIGHT;

		if (height < 1)
			height = 1;
		// this is the new aspect ratio based on the resize
		float calc_aspect = (float) width / (float) height;

		float aspect = calc_aspect / target_aspect;

		if (calc_aspect >= target_aspect) {
			GL_Width = DRAWING_WIDTH / 2.0f * aspect;
			GL_Height = DRAWING_HEIGHT / 2.0f;
		} else {
			GL_Width = DRAWING_WIDTH / 2.0f;
			GL_Height = DRAWING_HEIGHT / 2.0f / aspect;
		}

		myOrigin.updateOriginVertex(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		GLU glu = new GLU();
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		// gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D
		glu.gluOrtho2D(-GL_Width, GL_Width, -GL_Height, GL_Height); // canvas

		// gl.glViewport(0, 0, (int) GL_Width * 2, -(int) GL_Height * 2);
		gl.glViewport(-(int) GL_Width, (int) GL_Width, -(int) GL_Height, (int) GL_Height);

		// gl.glEnable(GL2.GL_DEPTH_TEST);
		// gl.glDepthFunc(GL2.GL_LESS);
		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the canvas with color

		gl.glLoadIdentity(); // reset the model-view matrix

		// myOrigin.render(gl);
		for (GShape artObject : drawingArtObjects) {
			artObject.render(gl);
		}
		
		passInformaiton();
		gl.glFlush();
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as
	 * buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	/**
	 * This function updates drawing based on keyboard events
	 */
	public void processKeyBoardEvents(int key) {

		this.spriteCharacter.processKeyBoardEvent(key);

	}

	public void processKeyBoardEventsStop() {
		keyBoard.setPressReleaseStatus(false);
		this.spriteCharacter.resetKeyBoardEvent();
	}

	/**
	 * 
	 */
	public void processMouseEvents()
	{

	}
	
	// One of the key algorithms in this objects, used to transfer the information of enemy and shooter to 
	//each other.
	public void passInformaiton(){
		
		//The bullet information position array.
		float[] bullet = new float[10];
		bullet = spriteCharacter.getBulletPositions();
		this.badGuyOne.getBulletPositions(bullet);
		this.badGuyTwo.getBulletPositions(bullet);
		
		//The position information of 1st enemy.
		float[] position1 = new float[2];
		boolean enemy1status;
		position1 = this.badGuyOne.getEnemyPosition();
		enemy1status = this.badGuyOne.getDeadStatus();
		
		//The position information of 2nd enemy.
		float[] position2 = new float[2];
		boolean enemy1statusTwo;
		position2 = this.badGuyTwo.getEnemyPosition();
		enemy1statusTwo = this.badGuyTwo.getDeadStatus();
		
		//pass them to shooter/player
		this.spriteCharacter.getEnemy1Positions(position1, enemy1status, position2, enemy1statusTwo);
		
	}
	
	
	

}