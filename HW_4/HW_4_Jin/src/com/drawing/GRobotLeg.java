package com.drawing;

import com.jogamp.opengl.GL2;

public class GRobotLeg implements GShape {

	float ref_x, ref_y;
	ShapeNode rootNode;
	int min, max;
	float current, speed;

	public GRobotLeg(float x, float y, int min, int max, float current, float speed) {
		this.min = min;
		this.max = max;
		this.current = current;
		this.speed = speed;
		// color
		float vertex2f[] = { 0.60f, 0.40f, 0.3f, 15f, 3f, 1f };

		GCRQuad quadObject = new GCRQuad(vertex2f);

		// thigh node
		vertex2f = new float[] { 0.0f, 0.0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 1: rotation axis
		};
		ShapeNode thighNode = new ShapeNode(false, quadObject, vertex2f);
		thighNode.setJointAngle(min, max, current, speed);


		// shin node
		vertex2f = new float[] { 0.6f, .20f, 0.2f, 20f, 3f, 1f };
		quadObject = new GCRQuad(vertex2f);

		vertex2f = new float[] { 15.0f, 0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 2: rotation axis
		};

		ShapeNode shinNode = new ShapeNode(false, quadObject, vertex2f);

		shinNode.setJointAngle(-10, 0, -10, .65f); // fourth quadrant


		// foot

		vertex2f = new float[] { 0.4f, 0.4f, 0.7f, 8f, 2.2f, 1f };
		quadObject = new GCRQuad(vertex2f);

		vertex2f = new float[] { 20.0f, 0f, 0f, // 0: translate
				0.0f, 0.0f, 1.0f, // 2: rotation axis
		};

		ShapeNode footNode = new ShapeNode(false, quadObject, vertex2f);

		footNode.setJointAngle(85, 90, 85, .15f);

		// adding the nodes

		shinNode.addChildren(footNode);
		thighNode.addChildren(shinNode);

		// creating the root node
		vertex2f = new float[] { x, y, 0f, 0f, 0f, 0f };

		GEmptyShape emptyShape = new GEmptyShape();
		rootNode = new ShapeNode(true, emptyShape, vertex2f);
		rootNode.addChildren(thighNode);

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
