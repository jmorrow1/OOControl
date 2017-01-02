package extra;

import controllers.Controller;
import controllers.ControllerUpdater;
import controllers.MouseEvent;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T>
 */
public class Window<T extends Controller> extends Controller {
    private PGraphicsWindow graphicsWindow;
    private T controller;
    private boolean selected;

    public Window(T controller, ControllerUpdater updater, float priority) {
        super(updater, priority);
        this.controller = controller;
        controller.hide();
        // PGraphics pg = pa.createGraphics((int)getWidth(), (int)getHeight());
        // graphicsWindow = new PGraphicsWindow(getX1(), getY1(), pg);
    }

    @Override
    public void draw(PGraphics pg) {
        // draw controller to graphics window
        graphicsWindow.beginDraw();
        graphicsWindow.background(0xffffffff);
        controller.draw(graphicsWindow);
        graphicsWindow.endDraw();

        // draw graphics window to the primary PGraphics instance, pg
        pg.rectMode(pg.CORNER);
        pg.image(graphicsWindow.getInnerPGraphics(), graphicsWindow.getX1(), graphicsWindow.getY1(),
                graphicsWindow.getWidth(), graphicsWindow.getHeight());

        pg.stroke(0);
        pg.noFill();
        rect.draw(pg);
    }

    /******************
     ***** Events *****
     ******************/

    public void mouseMoved(MouseEvent e) {
        boolean touches = controller.touches(e.x, e.y);
        if (!selected && touches) {
            controller.mouseEnter(e);
            selected = true;
        } else if (selected && !touches) {
            controller.mouseExit(e);
            selected = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (selected) {
            controller.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selected) {
            controller.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selected) {
            controller.mouseReleased(e);
        }
    }

    @Override
    public void mouseEnter(MouseEvent e) {
        boolean touches = controller.touches(e.x, e.y);
        if (touches) {
            controller.mouseEnter(e);
            selected = true;
        }
    }

    @Override
    public void mouseExit(MouseEvent e) {
        if (selected) {
            controller.mouseExit(e);
            selected = false;
        }
    }

    /*****************************
     ***** Getters / Setters *****
     *****************************/

    @Override
    public void setCenter(float x, float y) {
        super.setCenter(x, y);
        graphicsWindow.setCenter(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        graphicsWindow.setSize((int) width, (int) height);
    }
}