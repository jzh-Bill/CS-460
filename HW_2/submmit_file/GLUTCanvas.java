package com.drawing;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

// import com.jogamp.opengl.util.gl2.GLUT;

/*
* JOGL 2.0 Program Template (GLCanvas) This is a "Component" which can be added
* into a top-level "Container". It also handles the OpenGL events to render
* graphics.
*/
@SuppressWarnings("serial")
class GLUTCanvas extends GLCanvas implements GLEventListener {

	private static final int CANVAS_WIDTH = 768; // width of the drawable
	private static final int CANVAS_HEIGHT = 768; // height of the drawable

	private final float DRAWING_WIDTH = 100f, DRAWING_HEIGHT = 100f;
	// Setup OpenGL Graphics Renderer
	
	private GLand myLand;
	private GHouse myHouse;
	private GTree myTree;
	private GSun mySun;
	private GCloud myFirstCloud;
	private GCloud mySecondCloud;
	private GTrainHead myTrainHead;
	private GTrainTail myTrainTail;
	private GTrail myTrail;

	ArrayList<GShape> drawingObjects;

	/** Constructor to setup the GUI for this Component */
	public GLUTCanvas() {
		this.addGLEventListener(this);
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

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

		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		gl.glOrtho(0.0f, DRAWING_WIDTH, 0.0f, DRAWING_HEIGHT, -1.0f, 0.0f); // 2D canvas

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

		gl.glClearColor(0.0f, .90f, 1.0f, 0.5f); // color used to clean the canvas
		gl.glColor3f(1.0f, 0.0f, 0.0f); // drawing color



		// triangle
		float vertex2f[] = { 35.0f, 5.0f, 35f, 35f, 5f, 5f };

		//house
		float vertex2fMajorQuad[] = new float[] {
				30.0f, 65.0f, // left top point
				45.0f, 50.0f, // bottom right point
				0.0f, 0.0f, 1.0f, //main color 
				0.0f, 0.0f, 0.0f //outline color
		};
		
		float vertex2fMinorQuad[] = new float[] {
				33.0f, 62.0f, // left top point
				42.0f, 53.0f, // bottom right point
				1.0f, 1.0f, 0.0f, //main color 
				0.0f, 0.0f, 0.0f //outline color	
		};
		
		float vertex2Triangle[] = new float[] {
				28.0f, 65.0f, //bottom-left point
				47.0f, 65.0f, //bottom-right point
				37.5f, 78.0f, //top point
				1.0f, 0.0f, 1.0f //drawing color
		};
		
		myHouse = new GHouse(vertex2fMajorQuad, vertex2fMinorQuad, vertex2Triangle);
		
		//myTree
		float vertex2tree[] = new float[] {
				15.0f, 81.0f, //Ellipse x, y coordinates
				9.0f, 12.0f, //Ellipse x and y radius
				0.408f, 0.337f, 0.212f, //drawing color of ellipse		                
		};
		
		float vertex2stump[] = new float[] {
				13.5f, 79.0f, // 0: left top point
				16.5f, 45f, // 2: bottom right point
				0.66f, 0.474f, .26f, // 4: main color
				.0f, 0.0f, .0f  // 7: outline color
		};
		
		myTree = new GTree(vertex2tree, vertex2stump);
		
		
		//land 
		myLand = new GLand(50f, -45f, 100f);
		//float landColor[] = {0.07f, 0.201f, 0.020f, 0f, 0f,0f};
		//myLand.set_color(landColor);
		
		//Mysun
		vertex2f = new float[] {90.0f, 87.0f, 6.0f};//x and y coordinates and radius.
		mySun = new GSun(vertex2f);
		
		
		//MyfirstCloud
		vertex2f = new float[] {50.0f, 90.0f, 5.0f};
		myFirstCloud = new GCloud(vertex2f);
		
		//MySecondCloud
		vertex2f = new float[] {70.0f, 87.0f, 4.5f};
		mySecondCloud = new GCloud(vertex2f);
		
		
		//myTrainHead
		vertex2f = new float[] {60.0f, 20.0f};
		myTrainHead = new GTrainHead(vertex2f);
		
		//myTrainTail
		vertex2f = new float[] {30.0f, 20.0f};
		myTrainTail = new GTrainTail(vertex2f);
		
		//myTrail
		vertex2f = new float[] {
				0.0f, 16.0f,
				100.0f, 13.5f,
				0.667f, 0.47f, 0.258f,
				0.0f, 0.0f, 0.0f
		};
		
		myTrail = new GTrail(vertex2f);
		
		//Put all objects in ArrayList and render them later.
		drawingObjects = new ArrayList<GShape>();
		drawingObjects.add(myLand);
		drawingObjects.add(myHouse);
		drawingObjects.add(myTree);
		drawingObjects.add(mySun);
		drawingObjects.add(myFirstCloud);
		drawingObjects.add(mySecondCloud);
		drawingObjects.add(myTrainTail);
		drawingObjects.add(myTrainHead);
		drawingObjects.add(myTrail);
		
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable is
	 * first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		// ----- Your OpenGL initialization code here -----
		float ar = 1f;
		if (height < 1)
			height = 1;
		ar = width / (float) height;

		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		gl.glOrtho(0.0f, DRAWING_WIDTH * ar, 0.0f, DRAWING_HEIGHT, -1.0f, 0.0f); // 2D canvas

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
		gl.glClear(GL_COLOR_BUFFER_BIT); // clear the canvas with color
		gl.glLoadIdentity(); // reset the model-view matrix

		gl.glTranslatef(0.0f, 0.0f, 0.0f); // translate into the screen


		for (GShape artObject : drawingObjects) {
			artObject.render(gl);
		}

		gl.glFlush();
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as
	 * buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}