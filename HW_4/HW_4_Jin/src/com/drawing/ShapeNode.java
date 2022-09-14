package com.drawing;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class ShapeNode implements GShape {

	private float translate[];
	// private float scale[];
	private float rotate;
	private float rotateAxis[];
	
	private int rotateMin, rotateMax;
	private float rotateDisp;
	private boolean animationFlag, requestStopAnimation;

	private GShape art;

	private boolean root_Node;

	ArrayList<ShapeNode> children;

	public ShapeNode(boolean root, GShape art, float vertex2f[]) {
		if (vertex2f.length < 6) {
			System.out.println("ShapeNode vertex paramter requires 6 values ..");
			// using default values
			translate = new float[] { 0f, 0f, 0f };
			rotateAxis = new float[] { 0f, 0f, 0f }; // no rotation
			this.art = art; // getting the drawing object
			


		} else {
			translate = new float[] { vertex2f[0], vertex2f[1], vertex2f[2] };
			rotateAxis = new float[] { vertex2f[3], vertex2f[4], vertex2f[5] };

			this.art = art; // getting the drawing object
		}

		// initializing the array list
		children = new ArrayList<ShapeNode>();
		// joint rotation setup
		rotate = 0f;
		rotateMin = 0;
		rotateMax = 360;
		rotateDisp = -0.5f;
		animationFlag = false;
		requestStopAnimation = false;
		this.root_Node = root;
	}

	public void setJointAngle(int min, int max, float r, float disp) {
		rotate = r;
		rotateMin = min;
		rotateMax = max;
		rotateDisp = disp;
	}

	void stopAnimation() {
		// make sure it is negative
		// rotateDisp = -Math.abs(rotateDisp);

		setAnimationFlag(false);
		requestStopAnimation = true;
		// ask to stop animating to all the child objects
		for (ShapeNode childShape : children) {
			childShape.stopAnimation();
		}

	}

	void startAnimation() {
		// rotateDisp = Math.abs(rotateDisp);
		setAnimationFlag(true);
		requestStopAnimation = false;
		
		for (ShapeNode childShape : children) {
			childShape.startAnimation();
		}
	}

	void setAnimationFlag(boolean flag) {
		animationFlag = flag;
	}

	public void addChildren(ShapeNode child) {
		children.add(child);
	}

	// get children at specified position
	public ShapeNode getChildren(int index)
	{
		if(children.size()>index)
		{
			return children.get(index);
		}
		else
			return null;
	}

	private void animateJoint() {

		if (animationFlag == true) {
			animateJointAngles();

		} else if (requestStopAnimation == true) {
			animateJointAngles();

			if (Math.abs(rotate - (rotateMin + (this.rotateMax - this.rotateMin) / 2)) <= 5) {
				setAnimationFlag(false);
				// rotateDisp = Math.abs(rotateDisp);
				requestStopAnimation = false;
				setAnimationFlag(false);
			}

		}
	}

	private void animateJointAngles() {

		// we have reached the max angle, return
		if (rotate >= rotateMax)
			rotateDisp = -Math.abs(rotateDisp);

		// we have reached the min angle, return
		else if (rotate <= rotateMin) {
			rotateDisp = Math.abs(rotateDisp);
		}

		rotate += rotateDisp;
	}

	public void render(final GL2 gl) {
		gl.glPushMatrix();


		if (this.root_Node == false)
			this.animateJoint();


		gl.glTranslatef(translate[0], translate[1], translate[2]);
		gl.glRotatef(rotate, rotateAxis[0], rotateAxis[1], rotateAxis[2]);
		// gl.glScalef(scale[0], scale[1], scale[2]);

		art.render(gl);

		for (ShapeNode childShape : children) {
			childShape.render(gl);
		}

		gl.glPopMatrix();

	}

}
