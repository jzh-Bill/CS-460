package com.drawing;

import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_CW;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class GRobot implements GShape {

	final float displacement = 5f;
	float x, y, scaleX, scaleY;
	float speed;
	boolean direction;
	GRobotArm botArmLeft, botArmRight;
	GRobotLeg botLegLeft, botLegRight;
	
	ShapeNode headNode, chestNode;
	ArrayList<GShape> drawingArtObjects;

	public GRobot(float x, float y, float scaleX, float scaleY, boolean direction)
	{
		this.x = x;
		this.y = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.direction = direction;
		// x, y is the origin

		// initializing head and chest
		float vertex2f[] = new float[] { 0.9f, 0.7f, 0.6f, 8.5f, 5f, 1f };
		GCRQuad quadObject = new GCRQuad(vertex2f);

		// headNode
		vertex2f = new float[] { 36.0f, -3.5f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 1: rotation axis
		};

		ShapeNode headNode = new ShapeNode(false, quadObject, vertex2f);
		headNode.setJointAngle(90, 90, 90, .0f);

		// chestNode
		vertex2f = new float[] { 0.1f, 0.7f, 0.6f, 28.5f, 6f, 1f };
		quadObject = new GCRQuad(vertex2f);
		vertex2f = new float[] { 0.0f, -10.0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 1: rotation axis
		};

		chestNode = new ShapeNode(true, quadObject, vertex2f);
		chestNode.setJointAngle(90, 90, 90, .0f);
		chestNode.addChildren(headNode);

		// initializing the arms and legs
		this.speed = 3.35f;
		botArmLeft = new GRobotArm(0f, 17f, -125, -40, -40f, speed);
		botArmRight = new GRobotArm(0f, 17f, -125, -40, -125f, speed);
		botLegLeft = new GRobotLeg(0f, -10f, -125, -40, -125f, speed);
		botLegRight = new GRobotLeg(0f, -10f, -125, -40, -40f, speed);

		drawingArtObjects = new ArrayList<GShape>();
		drawingArtObjects.add(botArmRight);
		drawingArtObjects.add(botLegRight);
		drawingArtObjects.add(chestNode);
		drawingArtObjects.add(botLegLeft);
		drawingArtObjects.add(botArmLeft);
	}
	
	public void InitiateStopAnimation() {

		botArmLeft.InitiateStopAnimation();
		botArmRight.InitiateStopAnimation();
		botLegLeft.InitiateStopAnimation();
		botLegRight.InitiateStopAnimation();

	}

	public void InitiateStartAnimation() {
		botArmLeft.InitiateStartAnimation();
		botArmRight.InitiateStartAnimation();
		botLegLeft.InitiateStartAnimation();
		botLegRight.InitiateStartAnimation();

	}

	void setSpeed(float s)
	{
		this.speed = s;
	}

	void moveUp() {
		y += displacement;
		InitiateStartAnimation();
	}

	void moveDown() {
		y -= displacement;
		InitiateStartAnimation();
	}

	void moveForward() {
		x += displacement;
		if (x > GLUTCanvas.DRAWING_WIDTH / 2 + 10)
			x = -GLUTCanvas.DRAWING_WIDTH / 2 - 10;
		direction = true;

		InitiateStartAnimation();
	}

	void moveBackward() {
		x -= displacement;
		if (x < -GLUTCanvas.DRAWING_WIDTH / 2 - 10)
			x = GLUTCanvas.DRAWING_WIDTH / 2 + 10;
		direction = false;

		InitiateStartAnimation();
	}

	public void render(final GL2 gl) {

		gl.glPushMatrix();
		gl.glTranslatef(this.x, this.y, 0.0f);
		gl.glScalef(this.scaleX, this.scaleY, 1.0f);

		gl.glEnable(GL_CULL_FACE);
		// depending on the direction, the robot's heading will change
		if (direction == true) {
			gl.glRotatef(0, 0, 1, 0);
			gl.glFrontFace(GL_CCW);
		} else {
			gl.glRotatef(180.0f, 0, 1, 0);
			gl.glFrontFace(GL_CW);
		}

		for (GShape artObject : drawingArtObjects) {
			artObject.render(gl);
		}

		gl.glDisable(GL_CULL_FACE);
		gl.glPopMatrix();
	}
}
