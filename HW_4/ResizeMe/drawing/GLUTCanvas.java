package com.drawing;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.Graphics2D;
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

	public static int CANVAS_WIDTH = 1100; // width of the drawable
	public static int CANVAS_HEIGHT = 700; // height of the drawable

	public static final float DRAWING_WIDTH = 550f, DRAWING_HEIGHT = 350f;
	public static float GL_Width, GL_Height;
	// Setup OpenGL Graphics Renderer
	GDrawOrigin myOrigin;
	GKeyBoard keyBoard;
	GMouse mouse;



	GDrawingPoints mousePoints;

	ArrayList<GShape> drawingArtObjects;

	/** Constructor to setup the GUI for this Component */
	public GLUTCanvas(GKeyBoard kb, GMouse mouse) {
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

		GL_Width = DRAWING_WIDTH / 2.0f;
		GL_Height = DRAWING_HEIGHT / 2.0f;
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D
																													// canvas

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glColor3f(1.0f, 0.0f, 0.0f); // drawing color

		
		// create a circle object
		
		GCircle myCircle = new GCircle(10f, 5f, 100f);
		
		// the mouse clicked points will be used to draw triangles
		mousePoints = new GDrawingPoints(GL_TRIANGLES);

		// origin
		myOrigin = new GDrawOrigin(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		// adding them all in the arrayList
		drawingArtObjects = new ArrayList<GShape>();

		drawingArtObjects.add(mousePoints);
		drawingArtObjects.add(myOrigin);
		drawingArtObjects.add(myCircle);

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

		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D

		gl.glViewport(0, 0, (int) GL_Width * 2, -(int) GL_Height * 2);

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

	}

	/**
	 * This function updates drawing based on keyboard events
	 */
	public void processKeyBoardEvents() {


		if (keyBoard.getCharPressed() == 't' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_TRIANGLES);
		}

		else if (keyBoard.getCharPressed() == 'l' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_LINES);

		}

		else if (keyBoard.getCharPressed() == 'c' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GDrawingPoints.XGL_CIRCLE);
		}

	}

	public void processKeyBoardEventsStop() {
		keyBoard.setPressReleaseStatus(false);

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

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT); // clear the canvas with color

		gl.glLoadIdentity(); // reset the model-view matrix

		gl.glTranslatef(0.0f, 0.0f, 0.0f); // translate into the screen

		myOrigin.render(gl);
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

}