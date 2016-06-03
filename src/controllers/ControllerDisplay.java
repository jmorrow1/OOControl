package controllers;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 * @param <T>
 */
public interface ControllerDisplay<T extends Controller> {
	public void display(PApplet pa, T controller);
}
