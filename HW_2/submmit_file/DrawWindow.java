
package com.drawing;

// Swing based Window
//import java.awt.event.WindowAdapter;

//import java.awt.event.WindowEvent;
//
//import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//
//import com.jogamp.opengl.awt.GLCanvas;
//import com.jogamp.opengl.util.FPSAnimator;

// public class DrawWindow {

//	/** The entry main() method to setup the top-level container and animator */
//	public static void main(String[] args) {
//		final String TITLE = "JOGL 2.0 Setup (GLCanvas)"; // window's title
//		final int FPS = 60; // animator's target frames per second
//		// Run the GUI codes in the event-dispatching thread for thread safety
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				// Create the OpenGL rendering canvas
//				GLCanvas canvas = new GLUTCanvas();
//
//				// Create a animator that drives canvas' display() at the specified FPS.
//				final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
//
//				// Create the top-level container
//				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
//				frame.getContentPane().add(canvas);
//				frame.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowClosing(WindowEvent e) {
//						// Use a dedicate thread to run the stop() to ensure that the
//						// animator stops before program exits.
//						new Thread() {
//							@Override
//							public void run() {
//								if (animator.isStarted())
//									animator.stop();
//
//								System.exit(0);
//							}
//						}.start();
//					}
//				});
//				frame.setTitle(TITLE);
//				frame.pack();
//				frame.setVisible(true);
//				animator.start(); // start the animation loop
//			}
//		});
//	}
//}

// AWT based window
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Creates the main window and adds the canvas; uses animator to call the
 * GLUT_Canvas object at the specified FPS
 */

public class DrawWindow {

	public static void main(String[] args) {

		final String TITLE = "JOGL 2.0 Setup (GLCanvas)"; // window's title
		final int FPS = 30; // animator's target frames per second

		final GLCanvas canvas = new GLUTCanvas();

		// Create a animator that drives canvas' display() at the specified FPS.
		final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

		// Create the top-level container

		// Now creating a frame using Frame class of AWT
		final Frame frame = new Frame(TITLE);
		frame.add(canvas);

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

		frame.setSize(GLUTCanvas.WIDTH, GLUTCanvas.HEIGHT);
		frame.pack();
		frame.setVisible(true);
		animator.start(); // start the animation loop
	}
}

