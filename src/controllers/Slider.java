package controllers;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Slider extends Controller {
	private float minValue, maxValue, currValue;
	private ControllerDisplay<Slider> sliderDisplay = DefaultDisplay.instance;
	
	public Slider(float initValue, float minValue, float maxValue, ControllerUpdater updater, float priority) {
		super(updater, priority);
		this.currValue = initValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		setCurrentValue(0);
	}
	
	public Slider(float minValue, float maxValue, ControllerUpdater updater, float priority) {
		this(minValue, minValue, maxValue, updater, priority);
	}

	@Override
	public void draw(PGraphics pg) {
		sliderDisplay.display(pg, this);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		handleMousePress(e.x, e.y);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		handleMousePress(e.x, e.y);
	}
	
	private void handleMousePress(int x, int y) {
		if (getWidth() >= getHeight()) {
			setCurrentValue(PApplet.map(x, getX1(), getX2(), minValue, maxValue));
		}
		else {
			setCurrentValue(PApplet.map(y, getY1(), getY2(), minValue, maxValue));
		}
	}
	
	public static class DefaultDisplay implements ControllerDisplay<Slider> {
		public static DefaultDisplay instance = new DefaultDisplay();
		
		private DefaultDisplay() {}
		
		@Override
		public void display(PGraphics pg, Slider s) {		
			final float diam = 12;
			
			pg.stroke(s.getColorInCurrentContext());
			pg.fill(s.getColorInCurrentContext());
			pg.strokeWeight(4);
			pg.strokeCap(pg.ROUND);
			if (s.isHorizontallyOriented()) {
				float x1 = s.getX1() + diam/2f;
				float x2 = s.getX2() - diam/2f;
				pg.line(x1, s.getCeny(), x2, s.getCeny());
				pg.noStroke();
				float x = PApplet.map(s.getCurrentValue(), s.getMinValue(), s.getMaxValue(), x1, x2);
				pg.ellipseMode(pg.CENTER);
				pg.ellipse(x, s.getCeny(), diam, diam);
			}
			else {
				float y1 = s.getY1() + diam/2f;
				float y2 = s.getY2() - diam/2f;
				pg.line(s.getCenx(), y1, s.getCenx(), y2);
				pg.noStroke();
				float y = PApplet.map(s.getCurrentValue(), s.getMinValue(), s.getMaxValue(), y1, y2);
				pg.ellipseMode(pg.CENTER);
				pg.ellipse(s.getCenx(), y, diam, diam);
			}
		}	
	}
	
	public boolean isHorizontallyOriented() {
		return this.getWidth() >= this.getHeight();
	}
	
	public boolean isVerticallyOriented() {
		return this.getHeight() >= this.getWidth();
	}
	
	public void setCurrentValue(float currValue) {
		this.currValue = PApplet.constrain(currValue, minValue, maxValue);
	}
	
	public void setValueRange(float minValue, float maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public float getCurrentValue() {
		return currValue;
	}
	
	public float getMinValue() {
		return minValue;
	}
	
	public float getMaxValue() {
		return maxValue;
	}
}