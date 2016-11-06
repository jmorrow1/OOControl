package controllers;

import processing.core.PGraphics;

/**
 * 
 * @author James Morrow
 *
 * @param <T>
 */
public interface ControllerDisplay<T extends Controller> {
	public void display(PGraphics g, T controller);
}
