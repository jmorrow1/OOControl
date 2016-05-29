package controllers;

public interface ControllerListener<C extends Controller> {
    void controllerEvent(C c);
}