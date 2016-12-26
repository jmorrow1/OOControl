package controllers;

import processing.core.PApplet;

//TODO Make fields only accessible through getters, so they can't be modified except via set.
//The problem is that controller code can change these fields and the same MouseEvent can go out
//to multiple controllers in the same mouse event function.

/**
 * 
 * @author James Morrow
 *
 */
public class MouseEvent {
	public int x, y;
	public int dx, dy;
	public int px, py;
	public int mouseButton;
	
	public MouseEvent() {
		x = 0;
		y = 0;
		dx = 0;
		dy = 0;
		px = 0;
		py = 0;
		mouseButton = 0;
	}
	
	public MouseEvent(PApplet pa) {
		set(pa);
	}
	
	public MouseEvent set(PApplet pa) {
		this.x = pa.mouseX;
		this.y = pa.mouseY;
		this.dx = pa.mouseX - pa.pmouseX;
		this.dy = pa.mouseY - pa.pmouseY;
		this.px = pa.pmouseX;
		this.py = pa.pmouseY;
		this.mouseButton = pa.mouseButton;
		return this;
	}
	
	public MouseEvent set(int x, int y, int px, int py, int mouseButton) {
		this.x = x;
		this.y = y;
		this.dx = x - px;
		this.dy = y - py;
		this.px = px;
		this.py = py;
		this.mouseButton = mouseButton;
		return this;
	}
	
	public void translate(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
	
	private String mouseButtonName(int mouseButton) {
		switch (mouseButton) {
			case PApplet.LEFT : return "LEFT";
			case PApplet.RIGHT : return "RIGHT";
			default : return "" + mouseButton;
		}
	}

	@Override
	public String toString() {
		return "MouseEvent [x=" + x + ", y=" + y + ", dx=" + dx + ", dy=" + dy + ", mouseButton=" + mouseButtonName(mouseButton) + "]";
	}
}
