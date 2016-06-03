package controllers;

/**
 * 
 * @author James Morrow
 *
 * @param <C>
 */
public interface ControllerListener<C extends Controller> {
    void controllerEvent(C c);
}