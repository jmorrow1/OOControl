package geom;

import processing.core.PApplet;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public class Rect {
	private float cenx, ceny, width, height;
	
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setFloat("cenx", cenx);
		j.setFloat("ceny", ceny);
		j.setFloat("width", width);
		j.setFloat("height", height);
		return j;
	}
	
	public Rect(JSONObject j) {
		cenx = j.getFloat("cenx");
		ceny = j.getFloat("ceny");
		width = j.getFloat("width");
		height = j.getFloat("height");
	}
	
	public Rect(float a, float b, float c, float d, int rectMode) {
		set(a, b, c, d, rectMode);
	}
	
	public Rect(Rect r) {
		this(r.cenx, r.ceny, r.width, r.height, PApplet.CENTER);
	}
	
	public void set(float a, float b, float c, float d, int rectMode) {
		if (rectMode == PApplet.CENTER) {
			this.cenx = a;
			this.ceny = b;
			this.width = c;
			this.height = d;
		}
		else if (rectMode == PApplet.RADIUS) {
			this.cenx = a;
			this.ceny = b;
			this.width = 2*c;
			this.height = 2*d;
		}
		else if (rectMode == PApplet.CORNER) {
			this.width = c;
			this.height = d;
			this.cenx = a + 0.5f*width;
			this.ceny = b + 0.5f*height;
		}
		else if (rectMode == PApplet.CORNERS) {
			this.width = c - a;
			this.height = d - b;
			this.cenx = a + 0.5f*width;
			this.ceny = b + 0.5f*height;
		}
	}
	
	public void set(Rect r) {
		this.cenx = r.cenx;
		this.ceny = r.ceny;
		this.width = r.width;
		this.height = r.height;
	}
	
	public Rect clone() {
		return new Rect(this);
	}
	
	public void display(PApplet pa) {
		pa.rectMode(pa.CENTER);
		pa.rect(cenx, ceny, width, height);
	}

	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}

	public float getCenx() {
		return cenx;
	}
	
	public float getCeny() {
		return ceny;
	}
	
	public boolean touches(float x, float y) {
		return this.getX1() <= x && x <= this.getX2() && this.getY1() <= y && y <= this.getY2();
	}
	
	public boolean intersects(Rect r) {
		return ((r.getX1() <= getX1() && getX1() <= r.getX2()) || (getX1() <= r.getX1() && r.getX1() <= getX2())) &&
				((r.getY1() <= getY1() && getY1() <= r.getY2()) || (getY1() <= r.getY1() && r.getY1() <= getY2()));
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @return the width of the rectangle
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return the height of the rectangle
	 */
	public float getHeight() {
		return height;
	}
	
	public void setCenx(float cenx) {
		this.cenx = cenx;
	}
	

	public void setCeny(float ceny) {
		this.ceny = ceny;
	}
	
	/**
	 * 
	 * @return the rectangle's leftmost x-coordinate
	 */
	public float getX1() {
		return cenx - 0.5f*width;
	}
	
	/**
	 * 
	 * @return the rectangle's uppermost y-coordinate
	 */
	public float getY1() {
		return ceny - 0.5f*height;
	}
	
	/**
	 * 
	 * @return the rectangle's rightmost x-coordinate
	 */
	public float getX2() {
		return cenx + 0.5f*width;
	}
	
	/**
	 * 
	 * @return the rectangle's lowermost y-coordinate
	 */
	public float getY2() {
		return ceny + 0.5f*height;
	}
	
	/**
	 * Set the width of the rectangle to the given float
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * Set the height of the rectangle to the given float
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * Set the center point of the rectangle.
	 * @param cenx
	 * @param ceny
	 */
	public void setCenter(float cenx, float ceny) {
		this.cenx = cenx;
		this.ceny = ceny;
	}

	@Override
	public String toString() {
		return "Rect [cenx=" + cenx + ", ceny=" + ceny + ", width=" + width
				+ ", height=" + height + "]";
	}
}
