package controllers;

import java.util.Arrays;

import controllers.Button.DefaultDisplay;
import geom.Rect;
import processing.core.PApplet;
import processing.core.PFont;

public class Toggle extends Controller {
	private PFont font;
	private int fontSize;
	private ControllerDisplay<Toggle> toggleDisplay;
	private int textAlignX = PApplet.CENTER;
	private int textAlignY = PApplet.CENTER;
	
	private int state;
	private int numStates = 2;
	private String[] names = new String[] {"", ""};

	public Toggle(Rect rect, PFont font, int fontSize, ControllerUpdater updater, float priority) {
		super(rect, updater, priority);
		this.font = font;
		this.fontSize = fontSize;
		toggleDisplay = DefaultDisplay.instance;
		hoveredColor = 0;
		defaultColor = -10197916;
	}
	
	public Toggle(ControllerUpdater updater, float priority) {
		this(new Rect(0, 0, 0, 0, PApplet.CORNER), updater.getDefaultFont(), 16, updater, priority);
	}
	
	public Toggle(Rect rect, ControllerUpdater updater, float priority) {
		this(rect, updater.getDefaultFont(), 16, updater, priority);
	}
	
	/*******************
	 ***** Drawing *****
	 *******************/

	@Override
	public void draw(PApplet pa) {
		toggleDisplay.display(pa, this);
	}
	
	public static class DefaultDisplay implements ControllerDisplay<Toggle> {
		public static DefaultDisplay instance = new DefaultDisplay();
		
		private DefaultDisplay() {} 
		
		@Override
		public void display(PApplet pa, Toggle t) {
			pa.rectMode(pa.CORNER);
			pa.textFont(t.getFont());
			pa.textAlign(t.getTextAlignX(), t.getTextAlignY());
			pa.textSize(t.getFontSize());
			if (t.getState() == 1) {
				pa.fill(0);
			}
			else {
				pa.fill(t.getColorInCurrentContext());
			}
			pa.text(t.getNames()[t.getState()], t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
		}
	}
	
	/**************************
	 ***** Event Handling *****
	 **************************/
	
	public void mouseEnter(MouseEvent e) {
		hovered = true;
	}
	
	public void mousePressed(MouseEvent e) {
		state = (state+1) % numStates;
		sendEvent(this);
	}
	
	public void mouseLeave(MouseEvent e) {
		hovered = false;
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/

	public ControllerDisplay<Toggle> getDisplay() {
		return toggleDisplay;
	}

	public void setDisplay(ControllerDisplay<Toggle> toggleDisplay) {
		this.toggleDisplay = toggleDisplay;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public PFont getFont() {
		return font;
	}
	
	public void setName(String name) {
		super.setName(name);
		for (int i=0; i<this.names.length; i++) {
			this.names[i] = name;
		}
	}
	
	public void setName(int i, String name) {
		this.names[i] = name;
	}
	
	public String[] getNames() {
		return names;
	}
	
	public String getName(int i) {
		return names[i];
	}
	
	public void setNumStates(int numStates) {
		this.numStates = PApplet.max(numStates, 1);
		names = Arrays.copyOfRange(names, 0, numStates);
		for (int i=0; i<names.length; i++) {
			if (names[i] == null) {
				names[i] = "";
			}
		}
	}
	
	public int getNumStates() {
		return numStates;
	}
	
	public void setState(int state) {
		this.state = PApplet.constrain(state, 0, numStates-1);
	}
	
	public int getState() {
		return state;
	}
	
	public void setTextAlignX(int textAlignX) {
		this.textAlignX = textAlignX;
	}
	
	public int getTextAlignX() {
		return textAlignX;
	}
	
	public void setTextAlignY(int textAlignY) {
		this.textAlignY = textAlignY;
	}
	
	public int getTextAlignY() {
		return textAlignY;
	}
	
	public void setTextAlign(int textAlignX, int textAlignY) {
		this.textAlignX = textAlignX;
		this.textAlignY = textAlignY;
	}
}