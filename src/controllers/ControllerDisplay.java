package controllers;

import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow
 *
 * @param <T>
 */
public interface ControllerDisplay<T extends Controller> extends PConstants {
    public void display(PGraphics g, T controller);
}
