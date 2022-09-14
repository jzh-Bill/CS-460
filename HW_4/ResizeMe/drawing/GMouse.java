package com.drawing;

import java.awt.Point;

public class GMouse {

	private Point pointClicked;
	private boolean pressReleaseStatus;

	public GMouse() {
		pointClicked = new Point(0, 0);
		pressReleaseStatus = false; // not pressed
	}

	public Point getPointClicked() {
		return pointClicked;
	}

	public void setPointClicked(Point pointClicked) {

		double y = GLUTCanvas.CANVAS_HEIGHT / 2.0f - pointClicked.getY();
		y = y * (GLUTCanvas.GL_Height) / (GLUTCanvas.CANVAS_HEIGHT / 2.0);

		double x = pointClicked.getX() - GLUTCanvas.CANVAS_WIDTH / 2.0;
		x = x * (GLUTCanvas.GL_Width) / (GLUTCanvas.CANVAS_WIDTH / 2.0);

		Point myPoint = new Point();
		myPoint.setLocation(x, y);
		System.out.printf("\nConverted point: %.2f %.2f\n", x, y);

		this.pointClicked = myPoint;
	}

	public boolean isPressReleaseStatus() {
		return pressReleaseStatus;
	}

	public void setPressReleaseStatus(boolean pressReleaseStatus) {
		this.pressReleaseStatus = pressReleaseStatus;
	}

}
