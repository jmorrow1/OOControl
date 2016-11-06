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
	public PApplet pa;
	
	public MouseEvent(PApplet pa) {
		this.pa = pa;
	}
	
	public void set(PApplet pa) {
		this.x = pa.mouseX;
		this.y = pa.mouseY;
		this.dx = pa.mouseX - pa.pmouseX;
		this.dy = pa.mouseY - pa.pmouseY;
	}
	
	public MouseEvent set(int x, int y, int px, int py) {
		this.x = x;
		this.y = y;
		this.dx = x - px;
		this.dy = y - py;
		return this;
	}
	
	public void translate(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
}
