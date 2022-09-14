
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
import java.awt.Image;
//import java.awt.Taskbar;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Creates the main window and adds the canvas; uses animator to call the
 * GLUT_Canvas object at the specified FPS
 */

public class DrawWindow extends Frame implements KeyListener, MouseListener {

	final static boolean DEBUG_OUTPUT = false;
	final static long serialVersionUID = 0l;

	final public String TITLE = "JOGL Interactive Drawing"; // window's title
	final public int FPS = 30; // animator's target frames per second

	GLCanvas canvas;

	// Create a animator that drives canvas' display() at the specified FPS.
	FPSAnimator animator;
	GKeyBoard keyBoard;


	DrawWindow() {

		keyBoard = new GKeyBoard();

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		capabilities.setDoubleBuffered(true); // animation
		canvas = new GLUTCanvas(capabilities, keyBoard);
		animator = new FPSAnimator(canvas, FPS, true);

		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		// Create the top-level container

		// Now creating a frame using Frame class of AWT
		this.setTitle(TITLE);
		this.add(canvas);
		canvas.requestFocusInWindow();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override
					public void run() {
						if (getAnimator().isStarted())
							getAnimator().stop();

						System.exit(0);
					}
				}.start();
			}
		});

	}

	public void startGame() {
		this.setSize(GLUTCanvas.WIDTH, GLUTCanvas.HEIGHT);
		this.pack();
		this.setVisible(true);
		getAnimator().start(); // start the animation loop

	}

	public GLCanvas getCanvas() {
		return this.canvas;
		
	}

	public FPSAnimator getAnimator() {
		return this.animator;
	}


	// keyboard events
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (e != null)
		{
			keyBoard.setCharPressed(e.getKeyChar());
			keyBoard.setPressReleaseStatus(true);

			((GLUTCanvas) canvas).processKeyBoardEvents(key);
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e != null)
		{
			keyBoard.setPressReleaseStatus(false);
			((GLUTCanvas) canvas).processKeyBoardEventsStop();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	// Mouse events
	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	// create a window and start the drawing loop
	public static void main(String[] args) {

		DrawWindow game = new DrawWindow();


		try {
			String iconPath = "/world/CodeKit.png";
			String texPath = System.getProperty("user.dir") + iconPath;
			Image im = null;
			// for windows and macOS
			im = ImageIO.read(new File(texPath));
			game.setIconImage(im);

			// for macOS
//			final Taskbar taskbar = Taskbar.getTaskbar();
//			taskbar.setIconImage(im);
		} catch (IOException ex) {
			System.out.println("Icon not found");
		}

		game.startGame();

	}

}


