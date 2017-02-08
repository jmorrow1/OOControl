package controllers;

import java.util.ArrayList;
import java.util.Comparator;

import geom.Rect;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.data.JSONObject;

/**
 * 
 * @author James [jamesmorrowdesign.com]
 *
 * @param <T>
 */
public abstract class Controller<T extends Controller> implements PConstants {
    //static
    public final static ControllerComparator comparator = new ControllerComparator();
    
    //style
    protected int defaultColor = 0, hoveredColor = 0;    
    
    //identification
    protected String groupName = "";
    protected String name = "";
    protected int id = 0;
    
    //external objects
    protected ControllerUpdater updater;
    private ControllerListener<T> listener;
    
    //area
    protected Rect rect;   
    
    //state
    protected boolean hovered;
    private boolean frozen = false; // If true, will still be displayed as long as (shown == true) but won't be interactive
    private boolean hidden = false; // If true, will not be displayed and will not be interactive
    private boolean sticky = true; // If true, the controller stays selected even when the mouse drags away from the controller
    
    //other
    protected float drawGroup;
    protected ArrayList<Controller> childControllers = new ArrayList<Controller>();   

    /**************************
     ***** Initialization *****
     **************************/

    /**
     * 
     * @param updater
     * @param drawGroup
     */
    public Controller(ControllerUpdater updater, float drawGroup) {
        this(new Rect(0, 0, 50, 50, PApplet.CORNER), updater, drawGroup);
    }

    /**
     * 
     * @param rect
     * @param updater
     * @param drawGroup
     */
    public Controller(Rect rect, ControllerUpdater updater, float drawGroup) {
        this.rect = rect;
        this.updater = updater;
        setDrawGroup(drawGroup);
    }

    /**
     * 
     * @param j
     * @param updater
     * @param drawGroup
     */
    public Controller(JSONObject j, ControllerUpdater updater, float drawGroup) {
        this.updater = updater;
        setDrawGroup(drawGroup);
        this.rect = new Rect(j.getJSONObject("rect"));
        this.id = j.getInt("id", id);
        this.name = j.getString("name", name);
        this.defaultColor = j.getInt("defaultColor", defaultColor);
        this.hoveredColor = j.getInt("hoveredColor", hoveredColor);
    }

    /**
     * 
     * @return
     */
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.setJSONObject("rect", rect.toJSON());
        j.setInt("id", id);
        j.setString("name", name);
        j.setInt("defaultColor", defaultColor);
        j.setInt("hoveredColor", hoveredColor);
        return j;
    }

    /******************
     ***** Events *****
     ******************/

    /**
     * 
     * @param e
     */
    public void mouseEnter(MouseEvent e) {
        hovered = true;
    }

    /**
     * 
     * @param e
     */
    public void mouseMoved(MouseEvent e) {}
    
    /**
     * 
     * @param e
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * 
     * @param e
     */
    public void mouseDragged(MouseEvent e) {}

    /**
     * 
     * @param c
     */
    public void mouseDraggedOver(Controller c) {}

    /**
     * 
     * @param e
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * 
     * @param e
     */
    public void mouseExit(MouseEvent e) {
        hovered = false;
    }

    /**
     * 
     * @param e
     */
    public void keyPressed(KeyEvent e) {
    }
    
    /**
     * 
     * @param dx
     * @param dy
     */
    public void translate(float dx, float dy) {
        rect.translate(dx, dy);
        for (Controller c : childControllers) {
            c.translate(dx, dy);
        }
    }

    /**
     * 
     * @param dwidth
     * @param dheight
     */
    public void resize(float dwidth, float dheight) {
        rect.setWidth(rect.getWidth() + dwidth);
        rect.setHeight(rect.getHeight() + dheight);
    }

    /**
     * 
     */
    public void dispose() {
        updater.removeController(this);
        for (Controller c : childControllers) {
            c.dispose();
        }
    }
    
    /**
     * 
     * @param controller
     */
    protected void sendEvent(T controller) {
        if (listener != null) {
            listener.controllerEvent(controller);
        }
    }

    /********************
     ***** Behavior *****
     ********************/

    /**
     * 
     * @param g
     */
    public abstract void draw(PGraphics g);
    
    /****************************
     ***** Controller Focus *****
     ****************************/

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean touches(float x, float y) {
        return rect.contains(x, y);
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    /**
     * 
     * @param listener
     */
    public void setListener(ControllerListener<T> listener) {
        this.listener = listener;
    }

    /**
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 
     * @param rect
     */
    public void setRect(Rect rect) {
        this.rect = new Rect(rect);
    }

    /**
     * 
     * @param width
     * @param height
     */
    public void setSize(float width, float height) {
        rect.setWidth(width);
        rect.setHeight(height);
    }

    /**
     * 
     * @param x
     * @param y
     */
    public void setCenter(float x, float y) {
        float dx = x - rect.getCenx();
        float dy = y - rect.getCeny();
        rect.setCenter(x, y);
        for (Controller c : childControllers) {
            c.translate(dx, dy);
        }
    }

    /**
     * 
     * @param cenx
     */
    public void setCenx(float cenx) {
        this.setCenter(cenx, this.getCeny());
    }

    /**
     * 
     * @param ceny
     */
    public void setCeny(float ceny) {
        this.setCenter(this.getCenx(), ceny);
    }

    /**
     * 
     * @return
     */
    public float getX1() {
        return rect.getX1();
    }

    /**
     * 
     * @return
     */
    public float getY1() {
        return rect.getY1();
    }

    /**
     * 
     * @return
     */
    public float getX2() {
        return rect.getX2();
    }

    /**
     * 
     * @return
     */
    public float getY2() {
        return rect.getY2();
    }

    /**
     * 
     * @return
     */
    public float getCenx() {
        return rect.getCenx();
    }

    /**
     * 
     * @return
     */
    public float getCeny() {
        return rect.getCeny();
    }

    /**
     * 
     * @return
     */
    public float getWidth() {
        return rect.getWidth();
    }

    /**
     * 
     * @return
     */
    public float getHeight() {
        return rect.getHeight();
    }

    /**
     * 
     * @return
     */
    public int getDefaultColor() {
        return defaultColor;
    }

    /**
     * 
     * @param defaultColor
     */
    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * 
     * @return
     */
    public int getHoveredColor() {
        return hoveredColor;
    }

    /**
     * 
     * @param hoveredColor
     */
    public void setHoveredColor(int hoveredColor) {
        this.hoveredColor = hoveredColor;
    }

    /**
     * 
     * @return
     */
    public boolean isHovered() {
        return hovered;
    }

    /**
     * 
     * @return
     */
    public int getColorInCurrentContext() {
        return hovered ? hoveredColor : defaultColor;
    }

    /**
     * 
     * @return
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * 
     * @return
     */
    public boolean isFrozen() {
        return frozen;
    }

    /*
     * 
     */
    public boolean isEnabled() {
        return !hidden && !frozen;
    }

    /**
     * 
     * @return
     */
    public boolean isDisabled() {
        return hidden || frozen;
    }

    /**
     * 
     */
    public void hide() {
        hidden = true;
        for (Controller c : childControllers) {
            c.hide();
        }
    }

    /**
     * 
     */
    public void show() {
        hidden = false;
        for (Controller c : childControllers) {
            c.show();
        }
    }

    /**
     * 
     */
    public void freeze() {
        frozen = true;
        for (Controller c : childControllers) {
            c.freeze();
        }
    }

    /**
     * 
     */
    public void unfreeze() {
        frozen = false;
        for (Controller c : childControllers) {
            c.unfreeze();
        }
    }

    /**
     * 
     * @return
     */
    public final boolean isSticky() {
        return sticky;
    }

    /**
     * 
     * @param sticky
     */
    public final void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
    
    /**
     * 
     * @param drawGroup
     */
    public final void setDrawGroup(float drawGroup) {
        this.drawGroup = drawGroup;
        updater.reinsertController(this);
    }
    
    /**
     * 
     * @return
     */
    public final float getDrawGroup() {
        return drawGroup;
    }
    
    @Override
    public String toString() {
        return "{(" + this.getClass().getName() + ") name = \"" + this.getName() + "\", drawGroup = " + getDrawGroup() + "}";
    }
    
    /**********************
     ***** Comparator *****
     **********************/
    
    public static class ControllerComparator implements Comparator<Controller> {
        private ControllerComparator() {}
        
        @Override
        public int compare(Controller a, Controller b) {
            if (a.getDrawGroup() < b.getDrawGroup()) {
                return -1;
            }
            else if (a.getDrawGroup() == b.getDrawGroup()) {
                return 0;
            }
            else {
                return 1;
            }
        }
    }
}