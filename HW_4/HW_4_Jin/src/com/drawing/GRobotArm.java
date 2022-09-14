package com.drawing;

import com.jogamp.opengl.GL2;

public class GRobotArm implements GShape {

	float ref_x, ref_y;
	ShapeNode rootNode;
	int min, max;
	float current, speed;

	public GRobotArm(float x, float y, int min, int max, float current, float speed) {
		this.min = min;
		this.max = max;
		this.current = current;
		this.speed = speed;
		// color
		float vertex2f[] = { 1.0f, 0.40f, 0f, 15f, 3f, 1f };

		GCRQuad quadObject = new GCRQuad(vertex2f);

		// shoulder node
		vertex2f = new float[] { 0.0f, 0.0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 1: rotation axis
		};
		ShapeNode shoulderNode = new ShapeNode(false, quadObject, vertex2f);
		shoulderNode.setJointAngle(min, max, current, speed);


		// arm node
		vertex2f = new float[] { 0.3f, .80f, 0.4f, 20f, 3f, 1f };
		quadObject = new GCRQuad(vertex2f);

		vertex2f = new float[] { 15.0f, 0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 2: rotation axis
		};

		ShapeNode armNode = new ShapeNode(false, quadObject, vertex2f);

		armNode.setJointAngle(30, 40, 35, .65f);


		// wrist

		vertex2f = new float[] { 0.9f, 0.7f, 0.6f, 5.5f, 1.5f, 1f };
		quadObject = new GCRQuad(vertex2f);

		vertex2f = new float[] { 20.0f, 0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 2: rotation axis
		};

		ShapeNode wristNode = new ShapeNode(false, quadObject, vertex2f);

		wristNode.setJointAngle(-5, 5, -2, .25f);

		// adding the nodes

		armNode.addChildren(wristNode);
		shoulderNode.addChildren(armNode);

		// creating the root node
		vertex2f = new float[] { x, y, 0f, 0f, 0f, 0f };

		GEmptyShape emptyShape = new GEmptyShape();
		rootNode = new ShapeNode(true, emptyShape, vertex2f);
		rootNode.addChildren(shoulderNode);

	}

	public void InitiateStopAnimation() {
		// rootNode.getChildren(0).setJointAngle(min + (max - min) / 2, max, current,
		// 1.35f);
		rootNode.stopAnimation();
	}

	public void InitiateStartAnimation() {
		// rootNode.getChildren(0).setJointAngle(min, max, current, speed);
		rootNode.startAnimation();
	}

	public void render(final GL2 gl) {
		rootNode.render(gl);
	}
}
