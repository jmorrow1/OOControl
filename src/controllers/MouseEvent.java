package controllers;

import processing.core.PApplet;

//TODO Make fields only accessible through getters, so they can't be modified except via set.
//The problem is that controller code can change these fields and the same MouseEvent can go out
//to multiple controllers in the same mouse event function.
public class MouseEvent {
	public int x, y;
	public int dx, dy;
	public PApplet pa;
	
	public MouseEvent(PApplet pa) {
		this.pa = pa;
	}
	
	public MouseEvent set(int x, int y, int px, int py) {
		this.x = x;
		this.y = y;
		this.dx = x - px;
		this.dy = y - py;
		return this;
	}
}
