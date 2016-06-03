package controllers;

import java.util.Arrays;

import geom.Rect;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * 
 * @author James Morrow
 *
 */
public class MultiToggle extends Controller implements ControllerListener<Toggle> {
	private int state = 0;
	private Toggle[] toggles;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public MultiToggle(String[] toggleNames, int rowSize, int colSize, Rect rect, ControllerUpdater updater, float priority) {
		super(updater, priority);
		
		toggles = new Toggle[toggleNames.length];
		
		float x1 = rect.getX1();
		float y1 = rect.getY1();
		float width = rect.getWidth() / rowSize;
		float height = rect.getHeight() / colSize;
		
		int i = 0;
		for (int j=0; j<colSize; j++) {
			for (int k=0; k<rowSize; k++, i++) {
				toggles[i] = new Toggle(new Rect(x1, y1, width, height, PApplet.CORNER), updater, priority + 1);
				toggles[i].setName(toggleNames[i]);
				toggles[i].setListener(this);
				x1 += rect.getWidth() / rowSize;
			}
			y1 += rect.getHeight() / colSize;
			
		}
		
		if (toggles.length > 0) {
			toggles[0].setState(1);
		}
	}
	
	/*******************
	 ***** Drawing *****
	 *******************/

	@Override
	public void draw(PApplet pa) {}
	
	/*****************************
	 ***** Controller Events *****
	 *****************************/

	@Override
	public void controllerEvent(Toggle t) {
		if (t.getState() == 0) {
			t.setState(1);
		}
		else if (t.getState() == 1) {
			toggles[state].setState(0);
			state = indexOf(t, toggles);
			toggles[state].setState(1);
		}
		sendEvent(this);
	}
	
	/*****************
	 ***** Other *****
	 *****************/
	
	private static int indexOf(Toggle t, Toggle[] ts) {
		for (int i=0; i<ts.length; i++) {
			if (ts[i] == t) {
				return i;
			}
		}
		return -1;
	}
	
	public void translate(float dx, float dy) {
		for (Toggle t : toggles) {
			t.translate(dx, dy);
		}
	}
	
	public void setCenter(float x, float y) {
		float dx = x - getCenx();
		float dy = y - getCeny();
		translate(dx, dy);
	}
	
	public void setSize(float width, float height) {
		//TODO
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/

	public void setDisplay(ControllerDisplay<Toggle> toggleDisplay) {
		for (Toggle t : toggles) {
			t.setDisplay(toggleDisplay);
		}
	}
	
	public void setFontSize(int fontSize) {
		for (Toggle t : toggles) {
			t.setFontSize(fontSize);
		}
	}

	public void setName(int i, String name) {
		toggles[i].setName(name);
	}
	
	public int getNumStates() {
		return toggles.length;
	}
	
	public void setState(int state) {
		toggles[this.state].setState(0);
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public void setTextAlignX(int textAlignX) {
		for (Toggle t : toggles) {
			t.setTextAlignX(textAlignX);
		}
	}
	
	public void setTextAlignY(int textAlignY) {
		for (Toggle t : toggles) {
			t.setTextAlignY(textAlignY);
		}
	}
	
	public void setTextAlign(int textAlignX, int textAlignY) {
		for (Toggle t : toggles) {
			t.setTextAlign(textAlignX, textAlignY);
		}
	}
	
	public int getNumToggles() {
		return toggles.length;
	}
}