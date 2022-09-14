package com.drawing;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class GDrawOrigin implements GShape {


	private float width, height;

	ArrayList<GShape> lines;
	public GDrawOrigin(float w, float h )
	{
		this.width = w;
		this.height = h;
		
		lines = new ArrayList<GShape>();

		float vertex2f[] = new float[] { -this.width, 0f, // 0: left top point
				this.width, 0f, // 2: bottom right point
				0.2f, 0.9f, .6f, // 4: main color
				.1f, 0.5f, .90f }; // 7: outline color

		GLine lineSegment = new GLine(vertex2f);
		lineSegment.setLineWidth(8f);
		lines.add(lineSegment);


		vertex2f = new float[] { 0f, this.height, // 0: left top point
				0f, -this.height, // 2: bottom right point
				0.2f, 0.9f, .6f, // 4: main color
				.1f, 0.5f, .90f }; // 7: outline color
		lineSegment = new GLine(vertex2f);
		lineSegment.setLineWidth(8f);
		lines.add(lineSegment);

	}

	public void updateOriginVertex(float w, float h) {
		this.width = w;
		this.height = h;

		lines = new ArrayList<GShape>();

		float vertex2f[] = new float[] { -this.width, 0f, // 0: left top point
				this.width, 0f, // 2: bottom right point
				0.2f, 0.9f, .6f, // 4: main color
				.1f, 0.5f, .90f }; // 7: outline color

		GLine lineSegment = new GLine(vertex2f);
		lineSegment.setLineWidth(8f);
		lines.add(lineSegment);

		vertex2f = new float[] { 0f, this.height, // 0: left top point
				0f, -this.height, // 2: bottom right point
				0.2f, 0.9f, .6f, // 4: main color
				.1f, 0.5f, .90f }; // 7: outline color
		lineSegment = new GLine(vertex2f);
		lineSegment.setLineWidth(8f);
		lines.add(lineSegment);

	}

	public void render(final GL2 gl) {

		// gl.glPushMatrix();
		// gl.glTranslatef(5.0f, 5.0f, 0.0f);

		// gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		for (GShape artObject : lines) {
			artObject.render(gl);
		}

		// gl.glPopMatrix();
	}

}
