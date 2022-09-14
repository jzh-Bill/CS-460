package com.drawing;

public class GKeyBoard {

	private char charPressed;
	private boolean pressReleaseStatus;

	public GKeyBoard()
	{
		charPressed = ' ';
		pressReleaseStatus = false; // not pressed
	}

	public char getCharPressed() {
		return charPressed;
	}


	public void setCharPressed(char charPressed) {
		this.charPressed = charPressed;
	}

	public boolean isPressReleaseStatus() {
		return pressReleaseStatus;
	}

	public void setPressReleaseStatus(boolean pressReleaseStatus) {
		this.pressReleaseStatus = pressReleaseStatus;
	}

}
