package extra;

import java.awt.Cursor;
import java.util.ArrayList;

import controllers.Controller;
import controllers.ControllerListener;
import controllers.ControllerUpdater;
import controllers.MouseEvent;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

//TODO change size implementation to Rect.

/**
 * 
 * @author James Morrow
 *
 */
public class IntSlider extends Controller {
    // style
    private PFont font;

    // mode
    private final static int UP_DOWN = 0, RIGHT_LEFT = 1;
    private int direction;

    // position
    private int pressY;

    // logic
    private int pixelsPerValue = 4;
    private int minValue, maxValue, value;

    public IntSlider(PFont font, int id, ControllerListener<IntSlider> listener, int minValue, int maxValue,
            ControllerUpdater updater, float updatePriority) {
        super(updater, updatePriority);
        this.id = id;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.font = font;
        setListener(listener);
        defaultColor = 0;
    }

    public void draw(PGraphics pg) {
        pg.fill(getColorInCurrentContext());
        pg.textFont(font);
        pg.textAlign(CENTER, CENTER);
        pg.rectMode(CENTER);
        pg.text("" + value, rect.getCenx(), rect.getCeny(), rect.getWidth(), rect.getHeight());
    }

    public boolean touches(float x, float y) {
        return getX1() <= x && x <= getX2() && getY1() <= y && y <= getY2();
    }

    public void mouseEnter(MouseEvent e) {
        hovered = true;
        // e.pa.getSurface().setCursor(Cursor.N_RESIZE_CURSOR);
    }

    public void mousePressed(MouseEvent e) {
        pressY = e.y;
    }

    public void mouseDragged(MouseEvent e) {
        int dy = e.y - pressY;
        int dValue = -dy / pixelsPerValue;
        value = PApplet.constrain(value + dValue, minValue, maxValue);
        if (dValue != 0)
            pressY = e.y;
        sendEvent(this);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExit(MouseEvent e) {
        hovered = false;
        // e.pa.getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PFont getFont() {
        return font;
    }

    public void setFont(PFont font) {
        this.font = font;
    }

    public void setPixelsPerValue(int pixelsPerValue) {
        this.pixelsPerValue = pixelsPerValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void slideUpAndDown() {
        direction = UP_DOWN;
    }

    public void slideRightAndLeft() {
        direction = RIGHT_LEFT;
    }
}