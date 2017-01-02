package controllers;

import java.util.ArrayList;

import geom.Rect;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 * @param <T>
 */
public abstract class Controller<T extends Controller> implements PConstants {
    public static final int MOUSE_EVENT_RECEIVER = 0, KEY_EVENT_RECEIVER = 1;
    public final int receiverMode;
    protected int defaultColor = 0, hoveredColor = 0;
    protected boolean hovered;
    protected String groupName = "";
    protected String name = "";
    protected int id = 0;
    protected ControllerUpdater updater;
    protected Rect rect;
    private ControllerListener<T> listener;
    private boolean frozen = false; // If true, will still be displayed as long
                                    // as (shown == true) but won't be
                                    // interactive
    private boolean hidden = false; // If true, will not be displayed and will
                                    // not be interactive
    private boolean sticky = true; // If true, the controller stays selected
                                   // even when the mouse drags away from the
                                   // controller

    protected ArrayList<Controller> childControllers = new ArrayList<Controller>();

    /**************************
     ***** Initialization *****
     **************************/

    public Controller(ControllerUpdater updater, float priority) {
        this(MOUSE_EVENT_RECEIVER, new Rect(0, 0, 50, 50, PApplet.CORNER), updater, priority);
    }

    public Controller(int receiverMode, ControllerUpdater updater, float priority) {
        this(receiverMode, new Rect(0, 0, 50, 50, PApplet.CORNER), updater, priority);
    }

    public Controller(Rect rect, ControllerUpdater updater, float priority) {
        this(MOUSE_EVENT_RECEIVER, rect, updater, priority);
    }

    public Controller(int receiverMode, Rect rect, ControllerUpdater updater, float priority) {
        this.receiverMode = receiverMode;
        this.rect = rect;
        this.updater = updater;
        updater.addController(this, priority);
    }

    public Controller(JSONObject j, ControllerUpdater updater, float priority) {
        this.updater = updater;
        updater.addController(this, priority);
        this.rect = new Rect(j.getJSONObject("rect"));
        this.id = j.getInt("id", id);
        this.name = j.getString("name", name);
        this.defaultColor = j.getInt("defaultColor", defaultColor);
        this.hoveredColor = j.getInt("hoveredColor", hoveredColor);
        this.receiverMode = j.getInt("receiverMode", MOUSE_EVENT_RECEIVER);
    }

    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.setJSONObject("rect", rect.toJSON());
        j.setInt("id", id);
        j.setString("name", name);
        j.setInt("defaultColor", defaultColor);
        j.setInt("hoveredColor", hoveredColor);
        j.setInt("receiverMode", receiverMode);
        return j;
    }

    /************************
     ***** Input Events *****
     ************************/

    public void mouseEnter(MouseEvent e) {
        hovered = true;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseDraggedOver(Controller c) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExit(MouseEvent e) {
        hovered = false;
    }

    public void keyPressed(KeyEvent e) {
    }

    /*******************
     ***** Drawing *****
     *******************/

    public abstract void draw(PGraphics g);

    /****************************
     ***** Controller Focus *****
     ****************************/

    public boolean touches(float x, float y) {
        return rect.contains(x, y);
    }

    public boolean isMouseEventReceiver() {
        return receiverMode == MOUSE_EVENT_RECEIVER;
    }

    public boolean isKeyEventReceiver() {
        return receiverMode == KEY_EVENT_RECEIVER;
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    public void setListener(ControllerListener<T> listener) {
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRect(Rect rect) {
        this.rect = new Rect(rect);
    }

    public void setSize(float width, float height) {
        rect.setWidth(width);
        rect.setHeight(height);
    }

    public void setCenter(float x, float y) {
        float dx = x - rect.getCenx();
        float dy = y - rect.getCeny();
        rect.setCenter(x, y);
        for (Controller c : childControllers) {
            c.translate(dx, dy);
        }
    }

    public void setCenx(float cenx) {
        this.setCenter(cenx, this.getCeny());
    }

    public void setCeny(float ceny) {
        this.setCenter(this.getCenx(), ceny);
    }

    public float getX1() {
        return rect.getX1();
    }

    public float getY1() {
        return rect.getY1();
    }

    public float getX2() {
        return rect.getX2();
    }

    public float getY2() {
        return rect.getY2();
    }

    public float getCenx() {
        return rect.getCenx();
    }

    public float getCeny() {
        return rect.getCeny();
    }

    public float getWidth() {
        return rect.getWidth();
    }

    public float getHeight() {
        return rect.getHeight();
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int getHoveredColor() {
        return hoveredColor;
    }

    public void setHoveredColor(int hoveredColor) {
        this.hoveredColor = hoveredColor;
    }

    public boolean isHovered() {
        return hovered;
    }

    public int getColorInCurrentContext() {
        return hovered ? hoveredColor : defaultColor;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public boolean isEnabled() {
        return !hidden && !frozen;
    }

    public boolean isDisabled() {
        return hidden || frozen;
    }

    public void hide() {
        hidden = true;
        for (Controller c : childControllers) {
            c.hide();
        }
    }

    public void show() {
        hidden = false;
        for (Controller c : childControllers) {
            c.show();
        }
    }

    public void freeze() {
        frozen = true;
        for (Controller c : childControllers) {
            c.freeze();
        }
    }

    public void unfreeze() {
        frozen = false;
        for (Controller c : childControllers) {
            c.unfreeze();
        }
    }

    public final boolean isSticky() {
        return sticky;
    }

    public final void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    /*****************
     ***** Other *****
     *****************/

    protected void sendEvent(T controller) {
        if (listener != null) {
            listener.controllerEvent(controller);
        }
    }

    @Override
    public String toString() {
        return "{(" + this.getClass().getName() + ") name = \"" + this.getName() + "\", priority = "
                + updater.getControllerPriority(this) + "}";
    }

    public void translate(float dx, float dy) {
        rect.translate(dx, dy);
        for (Controller c : childControllers) {
            c.translate(dx, dy);
        }
    }

    public void resize(float dwidth, float dheight) {
        rect.setWidth(rect.getWidth() + dwidth);
        rect.setHeight(rect.getHeight() + dheight);
    }

    public void dispose() {
        updater.removeController(this);
        for (Controller c : childControllers) {
            c.dispose();
        }
    }
}