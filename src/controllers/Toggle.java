package controllers;

import java.util.Arrays;

import geom.Rect;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow
 *
 */
public class Toggle extends Controller {
	private PFont font;
	private int fontSize;
	private ControllerDisplay<Toggle> toggleDisplay;
	private int textAlignX = PApplet.CENTER;
	private int textAlignY = PApplet.CENTER;
	
	private boolean state;
	private String[] names = new String[] {"", ""};
	private int onColor;

	public Toggle(Rect rect, PFont font, int fontSize, ControllerUpdater updater, float priority) {
		super(rect, updater, priority);
		this.font = font;
		this.fontSize = fontSize;
		toggleDisplay = TextualDisplay.instance;
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
	public void draw(PGraphics pg) {
		toggleDisplay.display(pg, this);
	}
	
	public static class TextualDisplay implements ControllerDisplay<Toggle> {
		public static TextualDisplay instance = new TextualDisplay();
		
		private TextualDisplay() {} 
		
		@Override
		public void display(PGraphics pg, Toggle t) {
			pg.rectMode(pg.CORNER);
			pg.textFont(t.getFont());
			pg.textAlign(t.getTextAlignX(), t.getTextAlignY());
			pg.textSize(t.getFontSize());
			
			pg.fill(t.getColorInCurrentContext());
			
			pg.text(t.isOn() ? t.getNames()[0] : t.getNames()[1], t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
		}
	}
	
	public static class GraphicDisplay implements ControllerDisplay<Toggle> {
		public static GraphicDisplay instance = new GraphicDisplay();
		
		private GraphicDisplay() {}
		
		@Override
		public void display(PGraphics pg, Toggle t) {
			pg.noStroke();
			pg.fill(t.getColorInCurrentContext());
			pg.rectMode(pg.CORNER);
			pg.rect(t.getX1(), t.getY1(), t.getWidth(), t.getHeight());
		}
	}
	
	/**************************
	 ***** Event Handling *****
	 **************************/
	
	public void mouseEnter(MouseEvent e) {
		hovered = true;
	}
	
	public void mousePressed(MouseEvent e) {
		state = !state;
		sendEvent(this);
	}
	
	public void mouseExit(MouseEvent e) {
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
	
	@Override
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
	
	/**
	 * @deprecated
	 * @param numStates
	 */
	public void setNumStates(int numStates) {
		
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public int getNumStates() {
		return 2;
	}
	
	/**
	 * @deprecated
	 * @param state
	 */
	public void setStateSilently(int state) {
		this.state = (state == 1);
	}
	
	/**
	 * @deprecated
	 * @param state
	 */
	public void setState(int state) {
		this.state = (state == 1);
		sendEvent(this);
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public int getState() {
		return state ? 1 : 0;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean isOn() {
		return state;
	}
	
	public boolean isOff() {
		return !state;
	}
	
	@Override
	public int getColorInCurrentContext() {
		if (isOff()) {
			return super.getColorInCurrentContext();
		}
		else {
			return onColor;
		}
	}
	
	public void setOnColor(int onColor) {
		this.onColor = onColor;
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