package HW_1;
// specify package name

// GL constants
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;


/*
* JOGL 2.0 Program Template (GLCanvas) This is a "Component" which can be added
* into a top-level "Container". It also handles the OpenGL events to render
* graphics.
*/
@SuppressWarnings("serial")
class GLUT_Canvas extends GLCanvas implements GLEventListener {

	private static final int CANVAS_WIDTH = 1024; // width of the drawable
	private static final int CANVAS_HEIGHT = 768; // height of the drawable

	private final float DRAWING_WIDTH = 35f, DRAWING_HEIGHT = 35f;
	// Setup OpenGL Graphics Renderer

	/** Constructor to setup the GUI for this Component */
	public GLUT_Canvas() {
			this.addGLEventListener(this);
			this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		}


	// ------ Implement methods declared in GLEventListener ------

	/**
	 * Called back immediately after the OpenGL context is initialized. Can be used
	 * to perform one-time initialization. Run only once.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		// ----- Your OpenGL initialization code here -----

		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		gl.glOrtho(0.0f, DRAWING_WIDTH, 0.0f, DRAWING_HEIGHT, -1.0f, 1.0f); // 2D canvas

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glColor3f(0.1f, 0.4f, 0.3f); // drawing color
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable is
	 * first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT); // clear the canvas with color
		gl.glLoadIdentity(); // reset the model-view matrix

		// ----- Your OpenGL rendering code here (Render a white triangle for testing)

		gl.glTranslatef(0.0f, 0.0f, 0.0f); // translate into the screen
		gl.glBegin(GL_TRIANGLES);
		gl.glVertex3f(5.0f, 5.0f, 0.0f);
		gl.glVertex3f(25.0f, 5.0f, 0.0f);
		gl.glVertex3f(25.0f, 25.0f, 0.0f);
		gl.glEnd();
	}


	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as
	 * buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}


/**
 * Creates the main window and adds the canvas; uses animator to call the
 * GLUT_Canvas object at the specified FPS
 */

public class Lab0Jin {

	/** The entry main() method to setup the top-level container and animator */
	public static void main(String[] args) {
		final String TITLE = "Java JOGL Setup for Jin"; // window's title
		final int FPS = 60; // animator's target frames per second
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create the OpenGL rendering canvas
				GLCanvas canvas = new GLUT_Canvas();

				// Create a animator that drives canvas' display() at the specified FPS.
				final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

				// Create the top-level container
				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
				frame.getContentPane().add(canvas);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Use a dedicate thread to run the stop() to ensure that the
						// animator stops before program exits.
						new Thread() {
							@Override
							public void run() {
								if (animator.isStarted())
									animator.stop();

								System.exit(0);
							}
						}.start();
					}
				});
				frame.setTitle(TITLE);
				frame.pack();
				frame.setVisible(true);
				animator.start(); // start the animation loop
			}
		});
	}
}
