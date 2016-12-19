package extra;

import controllers.Controller;
import controllers.ControllerDisplay;
import controllers.ControllerUpdater;
import controllers.MouseEvent;
import geom.Rect;
import processing.core.PApplet;
import processing.core.PGraphics;

public class MultiSlider extends Controller {
	private float minValue, maxValue, tick;
	private float[] values, coords;
	private ControllerDisplay<MultiSlider> sliderDisplay;
	private boolean drag;
	private int selectedSlider = -1;

	public MultiSlider(float[] initValues, float minValue, float maxValue, ControllerUpdater updater, float priority) {
		super(updater, priority);
		this.values = initValues;
		this.minValue = minValue;
		this.maxValue = maxValue;
		coords = new float[values.length];
		map(values, minValue, maxValue, coords, getX1(), getX2(), values.length);
		sliderDisplay = new DefaultDisplay();
	}

	@Override
	public void draw(PGraphics g) {
		sliderDisplay.display(g, this);
	}
	
	public static class DefaultDisplay implements ControllerDisplay<MultiSlider> {
		private static int CIRCLE = 0, LINE = 1;
		private int mode;
		
		public DefaultDisplay() {
			this(CIRCLE);
		}
		
		public DefaultDisplay(int mode) {
			this.mode = mode;
		}
		
		@Override
		public void display(PGraphics g, MultiSlider s) {		
			final float diam = 12;

			g.strokeWeight(4);
			g.stroke(s.getColorInCurrentContext());
			g.fill(s.getColorInCurrentContext());
			g.strokeWeight(4);
			g.strokeCap(g.ROUND);
			
			if (s.isHorizontallyOriented()) {		
				g.line(s.getX1(), s.getCeny(), s.getX2(), s.getCeny());
				float prevCoord = s.getX1() - 1; 
				
				for (int i=0; i<s.getValueCount(); i++) {
					float coord = s.getCoord(i);
			
					if (mode == CIRCLE) {
						g.noStroke();
						if (i == s.getSelectedValue()) {
							g.fill(100);
						}
						else if (s.getCoord(i) != prevCoord) {
							g.fill(0);
						}
						g.ellipseMode(g.CENTER);
						g.ellipse(coord, s.getCeny(), diam, diam);
					}
					else if (mode == LINE) {
						if (i == s.getSelectedValue()) {
							g.stroke(100);
						}
						else if (s.getCoord(i) != prevCoord) {
							g.stroke(0);
						}
						g.line(coord, s.getY1(), coord, s.getY2());
					}

					prevCoord = coord;
				}				
			}
			else {
				g.line(s.getCenx(), s.getY1(), s.getCenx(), s.getY2());
				float prevCoord = s.getY1() - 1;
				
				for (int i=0; i<s.getValueCount(); i++) {
					float coord = s.getCoord(i);
			
					if (mode == CIRCLE) {
						g.noStroke();
						if (i == s.getSelectedValue()) {
							g.fill(100);
						}
						else if (s.getCoord(i) != prevCoord) {
							g.fill(0);
						}
						g.ellipseMode(g.CENTER);
						g.ellipse(s.getCenx(), coord, diam, diam);
					}
					else if (mode == LINE) {
						if (i == s.getSelectedValue()) {
							g.stroke(100);
						}
						else if (s.getCoord(i) != prevCoord) {
							g.stroke(0);
						}
						g.line(s.getX1(), coord, s.getX2(), coord);
					}

					prevCoord = coord;
				}		
			}
		}

		private static String format(float f) {
			int numDecimals = PApplet.min(3, numAfterDecimal(f));
			return String.format("%." + numDecimals + "f", f);
		}
		
		private static int numAfterDecimal(float f) {
			if (PApplet.floor(f) == f) {
				return 0;
			}
			
			String s = Float.toString(f);
			
			int count = 0;
			boolean startCounting = false;
			for (int i=0; i<s.length(); i++) {
				if (startCounting) {
					count++;
				}
				else {
					if (s.charAt(i) == '.') {
						startCounting = true;
					}
				}
			}
			
			return count;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		selectedSlider = (getWidth() >= getHeight()) ? indexOfClosestSlider(e.x) : indexOfClosestSlider(e.y);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		selectedSlider = (getWidth() >= getHeight()) ? indexOfClosestSlider(e.x) : indexOfClosestSlider(e.y);
		handleMousePress(e, true);
		drag = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (drag) {
			handleMousePress(e, false);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		drag = false;
		handleMousePress(e, tick > 0);
	}
	
	private void handleMousePress(MouseEvent e, boolean snap) {
		int i = selectedSlider;
		float prevValue = values[i];
		if (!snap) {
			if (getWidth() >= getHeight()) {
				float ex = PApplet.constrain(e.x, getX1(), getX2());
				if (i+1 < values.length && ex >= coords[i+1] && e.dx > 0) {
					drag = false;
					values[i] = values[i+1];
				}
				else if (i-1 >= 0 && ex <= coords[i-1] && e.dx < 0) {
					drag = false;
					values[i] = values[i-1];
				}
				else {
					values[i] = PApplet.map(ex, getX1(), getX2(), minValue, maxValue);
				}				
			}
			else {
				float ey = PApplet.constrain(e.y, getX1(), getX2());
				if (i+1 < values.length && ey >= coords[i+1] && e.dy > 0) {
					drag = false;
					values[i] = values[i+1];
				}
				else if (i-1 >= 0 && ey <= coords[i-1] && e.dy < 0) {
					drag = false;
					values[i] = values[i-1];
				}
				else {
					values[i] = PApplet.map(ey, getX1(), getX2(), minValue, maxValue);
				}	
			}
		}
		else {
			if (getWidth() >= getHeight()) {
				float ex = PApplet.constrain(e.x, getX1(), getX2());
				values[i] = quantize(PApplet.map(ex, getX1(), getX2(), minValue, maxValue), minValue, tick);
			}
			else {
				float ey = PApplet.constrain(e.y, getY1(), getY2());
				values[i] = quantize(PApplet.map(ey, getY1(), getY2(), minValue, maxValue), minValue, tick);
			}
		}
		if (values[i] != prevValue) {
			sendEvent(this);
			updateCoords();
		}
	}
	
	private int indexOfClosestSlider(int coord) {
		int indexOfMinDist = -1;
		float minDist = getWidth() + 1;
		
		for (int i=0; i<coords.length; i++) {
			float dist = PApplet.abs(coords[i] - coord);
			if (coords[i] - coord > 0) {
				if (dist < minDist) {
					indexOfMinDist = i;
					minDist = dist;
				}
				else {
					return indexOfMinDist;
				}
			}
			else {
				if (dist <= minDist) {
					indexOfMinDist = i;
					minDist = dist;
				}
				else {
					return indexOfMinDist;
				}
			}
		}
		
		return indexOfMinDist;
	}
	
	private static void map(float[] as, float minA, float maxA, float[] bs, float minB, float maxB, int n) {
		for (int i=0; i<n; i++) {
			bs[i] = PApplet.map(as[i], minA, maxA, minB, maxB);
		}
	}
	
	public int getSelectedValue() {
		return selectedSlider;
	}
	
	public int getValueCount() {
		return values.length;
	}
	
	public float getValue(int i) {
		return values[i];		
	}
	
	public float getMinValue() {
		return minValue;
	}
	
	public float getMaxValue() {
		return maxValue;
	}
	
	public float getCoord(int i) {
		return coords[i];
	}
	
	public void setTick(float tick) {
		this.tick = tick;
	}
	
	public boolean isHorizontallyOriented() {
		return this.getWidth() >= this.getHeight();
	}
	
	public boolean isVerticallyOriented() {
		return this.getHeight() >= this.getWidth();
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		updateCoords();
	}
	
	@Override
	public void setRect(Rect r) {
		super.setRect(r);
		updateCoords();
	}
	
	private void updateCoords() {
		if (isHorizontallyOriented()) {
			map(values, minValue, maxValue, coords, getX1(), getX2(), values.length);
		}
		else {
			map(values, minValue, maxValue, coords, getY1(), getY2(), values.length);
		}
	}
	
	private static float quantize(float val, float min, float quantum) {
	    val -= min;
	    val /= quantum;
	    val = PApplet.round(val);
	    return min + val * quantum;
	}
}
