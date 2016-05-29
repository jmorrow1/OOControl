package controllers;

import geom.Rect;
import processing.core.PApplet;
import processing.core.PFont;

public class Button extends Controller {
	private PFont font;
	private int fontSize;
	private ControllerDisplay<Button> buttonDisplay;
	private int textAlignX = PApplet.CENTER;
	private int textAlignY = PApplet.CENTER;

	public Button(String name, Rect rect, PFont font, int fontSize, ControllerUpdater updater, float priority) {
		super(rect, updater, priority);
		this.name = name;
		this.font = font;
		this.fontSize = fontSize;
		buttonDisplay = DefaultDisplay.instance;
		hoveredColor = 0;
		defaultColor = -10197916;
	}
	
	public Button(ControllerUpdater updater, float priority) {
		this("", new Rect(0, 0, 0, 0, PApplet.CORNER), updater.getDefaultFont(), 16, updater, priority);
	}
	
	/*******************
	 ***** Drawing *****
	 *******************/

	@Override
	public void draw(PApplet pa) {
		buttonDisplay.display(pa, this);
	}
	
	public static class DefaultDisplay implements ControllerDisplay<Button> {
		public static DefaultDisplay instance = new DefaultDisplay();
		
		private DefaultDisplay() {} 
		
		@Override
		public void display(PApplet pa, Button b) {
			pa.rectMode(pa.CORNER);
			pa.textAlign(b.getTextAlignX(), b.getTextAlignY());
			pa.textSize(b.getFontSize());
			pa.fill(b.isHovered() ? b.getHoveredColor() : b.getDefaultColor());
			pa.text(b.getName(), b.getX1(), b.getY1(), b.getWidth(), b.getHeight());
		}
	}
	
	public static class PlusDisplay implements ControllerDisplay<Button> {
		public static PlusDisplay instance = new PlusDisplay();
		
		private PlusDisplay() {}
		
		@Override
		public void display(PApplet pa, Button b) {
			pa.stroke(b.isHovered() ? b.getHoveredColor() : b.getDefaultColor());
			float avgSideLength = ((b.getWidth() + b.getHeight())/2f);
			pa.strokeWeight(avgSideLength / 15f);
			pa.strokeCap(pa.SQUARE);
			pa.line(b.getCenx() - b.getWidth()/2f, b.getCeny(), b.getCenx() + b.getWidth()/2f, b.getCeny());
			pa.line(b.getCenx(), b.getCeny() - b.getHeight()/2f, b.getCenx(), b.getCeny() + b.getHeight()/2f);
		}
	}
	
	/**************************
	 ***** Event Handling *****
	 **************************/
	
	public void mouseEnter(MouseEvent e) {
		hovered = true;
	}
	
	public void mousePressed(MouseEvent e) {
		sendEvent(this);
	}
	
	public void mouseLeave(MouseEvent e) {
		hovered = false;
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/

	public ControllerDisplay<Button> getDisplay() {
		return buttonDisplay;
	}

	public void setDisplay(ControllerDisplay<Button> buttonDisplay) {
		this.buttonDisplay = buttonDisplay;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
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