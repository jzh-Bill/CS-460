package com.drawing;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

	public static int CANVAS_WIDTH = 1100; // width of the drawable
	public static int CANVAS_HEIGHT = 700; // height of the drawable

	public static final float DRAWING_WIDTH = 550f, DRAWING_HEIGHT = 350f;
	public static float GL_Width, GL_Height;
	// Setup OpenGL Graphics Renderer
	GDrawOrigin myOrigin;
	GKeyBoard keyBoard;
	GMouse mouse;

	GRobot myRobot;

	GDrawingPoints mousePoints;

	GQuad myQuad;

	GTriangle learnTriangle;

	GPatch myPatch;

	ArrayList<GShape> drawingArtObjects;

	/** Constructor to setup the GUI for this Component */
	public GLUTCanvas(GLCapabilities capabilities, GKeyBoard kb, GMouse mouse) {

		super(capabilities);


		// creating a canvas for drawing
		// GLCanvas canvas = new GLCanvas(capabilities);

		this.addGLEventListener(this);
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.keyBoard = kb;
		this.mouse = mouse;
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
		// gl.glEnable(GL2.GL_DEPTH_TEST);
		// gl.glDepthFunc(GL2.GL_LESS);

		mousePoints = new GDrawingPoints(GL_TRIANGLES);

		// origin
		myOrigin = new GDrawOrigin(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		// robot
		myRobot = new GRobot(0, 10, 1.3f, 1.3f, true);
		
		// texture quad
		float vertex2f[] = new float[] { -175f, -8f, 80f, 90f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		myQuad = new GQuad(vertex2f);
		myQuad.loadTexture(gl, "/world/HotAirBalloon.png");
		myQuad.setAlpha(true);

		// learn quad initialization
		vertex2f = new float[] { 105f, 0f, 100f, 100f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f };
		learnTriangle = new GTriangle(vertex2f);
		learnTriangle.loadTexture(gl, "/world/Texture.png");

		// patch with texture
		myPatch = new GPatch(-275, -175, 550, 350, 5, "/world/Background.png");
		// adding them all in the arrayList
		drawingArtObjects = new ArrayList<GShape>();
		drawingArtObjects.add(myPatch);
		drawingArtObjects.add(mousePoints);
		drawingArtObjects.add(learnTriangle);
		drawingArtObjects.add(myOrigin);
		drawingArtObjects.add(myQuad);
		drawingArtObjects.add(myRobot);
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

		// gl.glTranslatef(0.0f, 0.0f, 0.0f); // translate into the screen

		// myQuad.renderTexture(gl);
		// myPatch.renderTexture(gl, -20, -20);
		// myQuad.renderTextureAlpha(gl, 200, 200);

		// myOrigin.render(gl);
		for (GShape artObject : drawingArtObjects) {
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

	/**
	 * This function updates drawing based on keyboard events
	 */
	public void processKeyBoardEvents() {

		if (keyBoard.getCharPressed() == 'n' && keyBoard.isPressReleaseStatus() == true) {
			if (myRobot != null) {
				myRobot.InitiateStopAnimation();
			}
		}
		else if (keyBoard.getCharPressed() == 'm' && keyBoard.isPressReleaseStatus() == true) {
			if (myRobot != null && this.myRobot != null) {
				myRobot.InitiateStartAnimation();

			}
		}

		else if (keyBoard.getCharPressed() == 't' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_TRIANGLES);
		}

		else if (keyBoard.getCharPressed() == 'l' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_LINES);

		}

		else if (keyBoard.getCharPressed() == 'c' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GDrawingPoints.XGL_CIRCLE);
		}

		else if (keyBoard.getCharPressed() == 'w' && keyBoard.isPressReleaseStatus() == true) {
			myRobot.moveUp();
			
		} else if (keyBoard.getCharPressed() == 's' && keyBoard.isPressReleaseStatus() == true) {
			myRobot.moveDown();
			
		} else if (keyBoard.getCharPressed() == 'a' && keyBoard.isPressReleaseStatus() == true) {
			myRobot.moveBackward();

		} else if (keyBoard.getCharPressed() == 'd' && keyBoard.isPressReleaseStatus() == true) {
			myRobot.moveForward();
		}
	}

	public void processKeyBoardEventsStop() {
		keyBoard.setPressReleaseStatus(false);
		myRobot.InitiateStopAnimation();
	}

	/**
	 * 
	 */
	public void processMouseEvents()
	{
		if (mouse.isPressReleaseStatus() == true) {
			mousePoints.addPoint(mouse.getPointClicked());
		}
	}

}