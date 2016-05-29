package controllers;

import processing.core.PApplet;

public interface ControllerDisplay<T extends Controller> {
	public void display(PApplet pa, T controller);
}
