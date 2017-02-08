package controllers;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

//TODO Bug fix translation logic
//TODO Editor in which to create GUIs.
//TODO Multiple active controllers? So mouseLeave isn't called when entering a nested controller?

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class ControllerUpdater {
    //all controllers
    private ArrayList<Controller> controllers = new ArrayList<Controller>();
    private ArrayList<Float> priorities = new ArrayList<Float>();
    
    //active controllers
    private Controller activeMouseEventReceiver;
    private Controller activeKeyEventReceiver;
    
    //defaults
    private PFont defaultFont;
    private ControllerListener defaultListener;

    //edit mode
    private boolean editMode;
    private int editColor = 0xFFF2CD51;
    
    //other
    private int tx, ty;
    private MouseEvent e;

    /**
     * 
     */
    public ControllerUpdater() {
        defaultFont = new PFont(PFont.findFont("font"), true);
        e = new MouseEvent();
    }

    /**
     * 
     * @param defaultListener
     */
    public ControllerUpdater(ControllerListener defaultListener) {
        this();
        this.defaultListener = defaultListener;
    }
    
    /********************
     ***** Behavior *****
     ********************/

    /**
     * 
     * @param g
     */
    public void draw(PGraphics g) {
        g.pushMatrix();
        g.translate(tx, ty);
        for (int i = 0; i < controllers.size(); i++) {
            Controller c = controllers.get(i);
            if (!c.isHidden()) {
                c.draw(g);
            }
        }
        if (editMode) {
            if (activeMouseEventReceiver != null) {
                Controller c = activeMouseEventReceiver;
                g.noStroke();
                g.fill(0x77ffffff);
                c.rect.draw(g);
            }
        }
        g.popMatrix();
    }
    
    /************************
     ***** Input Events *****
     ************************/

    /**
     * 
     * @param pa
     */
    public void mouseMoved(PApplet pa) {
        mouseMoved(e.set(pa.mouseX + tx, pa.mouseY + ty, pa.pmouseX + tx, pa.pmouseY + ty, pa.mouseButton));
    }

    /**
     * 
     * @param e
     */
    public void mouseMoved(MouseEvent e) {
        // handle mouse movement outside of a controller
        if (activeMouseEventReceiver != null) {
            if (!activeMouseEventReceiver.touches(e.x + tx, e.y + ty)) {
                activeMouseEventReceiver.mouseExit(e);
                activeMouseEventReceiver = null;
            }
        }

        // handle mouse movement within a controller
        int i = controllers.size() - 1;
        while (i >= 0) {
            Controller c = controllers.get(i);
            if (c.touches(e.x + tx, e.y + ty)) {
                if (c.isEnabled()) {
                    if (activeMouseEventReceiver != c) {
                        if (activeMouseEventReceiver != null) {
                            activeMouseEventReceiver.mouseExit(e);
                        }
                        activeMouseEventReceiver = c;
                        if (!editMode) {
                            c.mouseEnter(e);
                        }
                    }

                    activeMouseEventReceiver.mouseMoved(e);

                    break;
                }
            }
            i--;
        }
    }

    /**
     * 
     * @param pa
     */
    public void mousePressed(PApplet pa) {
        mousePressed(e.set(pa.mouseX + tx, pa.mouseY + ty, pa.pmouseX + tx, pa.pmouseY + ty, pa.mouseButton));
    }

    /**
     * 
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        if (editMode) {
            mousePressedEditMode(e);
            return;
        }

        if (activeMouseEventReceiver != null) {
            activeMouseEventReceiver.mousePressed(e);
        }
    }

    /**
     * 
     * @param pa
     */
    public void mouseReleased(PApplet pa) {
        mouseReleased(e.set(pa.mouseX + tx, pa.mouseY + ty, pa.pmouseX + tx, pa.pmouseY + ty, pa.mouseButton));
    }

    /**
     * 
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        if (editMode) {
            mouseReleasedEditMode(e);
            return;
        }

        if (activeMouseEventReceiver != null) {
            activeMouseEventReceiver.mouseReleased(e);
            if (!activeMouseEventReceiver.touches(e.x + tx, e.y + ty)) {
                activeMouseEventReceiver.mouseExit(e);
                activeMouseEventReceiver = null;
            }
        }
    }

    /**
     * 
     * @param pa
     */
    public void mouseDragged(PApplet pa) {
        mouseDragged(e.set(pa.mouseX + tx, pa.mouseY + ty, pa.pmouseX + tx, pa.pmouseY + ty, pa.mouseButton));
    }

    /**
     * 
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        if (editMode) {
            mouseDraggedEditMode(e);
            return;
        }

        if (activeMouseEventReceiver != null) {
            if (activeMouseEventReceiver.isSticky()) {
                activeMouseEventReceiver.mouseDragged(e);
                for (Controller c : controllers) {
                    if (c.touches(e.x + tx, e.y + ty)) {
                        activeMouseEventReceiver.mouseDraggedOver(c);
                    }
                }
            } else {
                // handle mouse movement outside a controller
                if (activeMouseEventReceiver != null) {
                    if (!activeMouseEventReceiver.touches(e.x + tx, e.y + ty)) {
                        activeMouseEventReceiver.mouseExit(e);
                        activeMouseEventReceiver = null;
                    }
                }

                // handle mouse movement within a controller
                int i = controllers.size() - 1;
                while (i >= 0) {
                    Controller c = controllers.get(i);
                    if (c.touches(e.x + tx, e.y + ty)) {
                        if (c.isEnabled()) {
                            if (activeMouseEventReceiver != c) {
                                if (activeMouseEventReceiver != null) {
                                    activeMouseEventReceiver.mouseExit(e);
                                }
                                activeMouseEventReceiver = c;
                                if (!editMode) {
                                    c.mouseEnter(e);
                                    c.mousePressed(e);
                                }
                            }

                            activeMouseEventReceiver.mouseDragged(e);

                            break;
                        }
                    }
                    i--;
                }
            }
        }
    }

    /**
     * 
     * @param e
     */
    public void keyPressed(KeyEvent e) {

    }

    /***********************************
     ***** Input Events, Edit Mode *****
     ***********************************/
    
    /**
     * 
     * @param e
     */
    public void mouseMovedEditMode(MouseEvent e) {
        for (Controller c : controllers) {
            if (c.touches(e.x + tx, e.y + ty)) {

            }
        }
    }

    /**
     * 
     * @param e
     */
    public void mousePressedEditMode(MouseEvent e) {
        for (Controller c : controllers) {
            if (c.touches(e.x + tx, e.y + ty)) {

            }
        }
    }

    /**
     * 
     * @param e
     */
    public void mouseReleasedEditMode(MouseEvent e) {

    }

    /**
     * 
     * @param e
     */
    public void mouseDraggedEditMode(MouseEvent e) {
        for (Controller c : controllers) {
            if (c.touches(e.x + tx, e.y + ty)) {

            }
        }
    }
    
    /************************
     ***** Other Events *****
     ************************/

    /**
     * 
     */
    public void hideAll() {
        for (Controller c : controllers) {
            c.hide();
        }
    }
    
    /**
     * 
     */
    public void freezeAll() {
        for (Controller c : controllers) {
            c.freeze();
        }
    }

    /**
     * 
     */
    public void showAll() {
        for (Controller c : controllers) {
            c.show();
        }
    }

    /**
     * 
     */
    public void unfreezeAll() {
        for (Controller c : controllers) {
            c.unfreeze();
        }
    }

    /**
     * 
     * @param c
     */
    public void addController(Controller c) {
        int insertionPoint = Collections.binarySearch(controllers, c, Controller.comparator);
        controllers.add(insertionPoint, c);
        if (defaultListener != null) {
            c.setListener(defaultListener);
        }
    }   

    /**
     * 
     * @param c
     */
    public void removeController(Controller c) {
        int i = controllers.indexOf(c);
        if (i != -1) {
            controllers.remove(i);
            priorities.remove(i);
        }
        if (c == this.activeMouseEventReceiver) {
            this.activeMouseEventReceiver = null;
        }
        if (c == this.activeKeyEventReceiver) {
            this.activeKeyEventReceiver = null;
        }
    }
    
    /**
     * 
     * @param c
     */
    protected void reinsertController(Controller c) {
        controllers.remove(c);
        int insertionPoint = Collections.binarySearch(controllers, c, Controller.comparator);
        controllers.add(insertionPoint, c);
    }

    @Deprecated
    public float getDrawGroup(Controller c) {
        int i = controllers.indexOf(c);
        if (i != -1) {
            return priorities.get(i);
        } else {
            return -1;
        }
    }

    @Deprecated
    public void setDrawGroup(Controller c, float drawGroup) {
        c.setDrawGroup(drawGroup);
    }

    /**
     * 
     */
    public void clearControllers() {
        controllers.clear();
    }
    
    /**
     * 
     * @param dx
     * @param dy
     */
    public void translate(int dx, int dy) {
        tx += dx;
        ty += dy;
    }
    
    /*******************************
     ***** Getters and Setters *****
     *******************************/

    /**
     * 
     * @param i
     * @return
     */
    public Controller getController(int i) {
        return controllers.get(i);
    }

    /**
     * 
     * @param c
     * @return
     */
    public boolean containsController(Controller c) {
        return controllers.contains(c);
    }

    /**
     * 
     * @return
     */
    public int controllerCount() {
        return controllers.size();
    }

    /**
     * 
     * @return
     */
    public PFont getDefaultFont() {
        return defaultFont;
    }

    /**
     * 
     */
    public void enableEditMode() {
        editMode = true;
        // pa.getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * 
     */
    public void disableEditMode() {
        editMode = false;
        // pa..getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * 
     * @return
     */
    public boolean hasActiveController() {
        return activeMouseEventReceiver != null;
    }

    /**
     * 
     * @return
     */
    public ControllerListener getDefaultListener() {
        return defaultListener;
    }

    /**
     * 
     * @param defaultListener
     */
    public void setDefaultListener(ControllerListener defaultListener) {
        this.defaultListener = defaultListener;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < controllers.size(); i++) {
            Controller c = controllers.get(i);
            s += c.toString() + ((i != controllers.size() - 1) ? ", " : "");
        }
        return s + "]";
    }
}