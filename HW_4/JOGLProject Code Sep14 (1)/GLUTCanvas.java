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

	private GCircle myCircle;
	private GPixels myPoint;
	private GTriangle myTriangle;
	private GQuad myQuad;

	private GEllipse myEllipse;

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

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glColor3f(1.0f, 0.0f, 0.0f); // drawing color

		// circle
		myCircle = new GCircle(60f, 25f, 15f);
		float color[] = { 0.7f, 0.2f, 0.3f, 0.2f, 0.4f, 0.9f };
		myCircle.setColor(color);

		// point
		myPoint = new GPixels(10f, 20f, 20f);

		// triangle
		float vertex2f[] = { 35.0f, 5.0f, 35f, 35f, 5f, 5f };
		myTriangle = new GTriangle(vertex2f);

		// quad
		vertex2f = new float[] { 25.0f, 70.0f, // 0: left top point
				55f, 40f, // 2: bottom right point
				0.2f, 0.9f, .6f, // 4: main color
				.20f, 0.1f, .50f }; // 7: outline color
		myQuad = new GQuad(vertex2f);
		
		// ellipse
		myEllipse = new GEllipse(70f, 65f, 8f, 12f);
		// adding them all in the arrayList
		drawingObjects = new ArrayList<GShape>();
		drawingObjects.add(myTriangle);
		drawingObjects.add(myCircle);
		drawingObjects.add(myPoint);
		drawingObjects.add(myQuad);
		drawingObjects.add(myEllipse);


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

		// myTriangle.render(gl);
		// myCircle.render(gl);
		// myPoint.render(gl);

		// animating the point
		myPoint.updatePoint(0.5f, DRAWING_WIDTH);

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