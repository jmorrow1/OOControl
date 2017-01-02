package controllers;

import controllers.Scrollbar.Scroller;
import geom.Rect;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Scrollbar extends Controller implements ControllerListener<Scroller> {
    // style
    private int color;

    // scroller
    private Scroller scroller;

    // logic
    private float pageLength;

    public Scrollbar(float initialPageLength, ControllerUpdater updater, float priority) {
        super(updater, priority);
        scroller = new Scroller(computeScrollerMin(), computeScrollerMax(), updater, priority + 1);
        scroller.setListener(this);
        setPageLength(initialPageLength);
    }

    @Override
    public void draw(PGraphics g) {
        g.noStroke();
        g.fill(color);
        rect.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        scroller.translate(e.x - scroller.getCenx(), e.y - scroller.getCeny());
        sendEvent(this);
    }

    @Override
    public void controllerEvent(Scroller scroller) {
        sendEvent(this);
    }

    public float getScrollerMin() {
        return isVerticallyOriented() ? scroller.getY1() : scroller.getX2();
    }

    public float getScrollerMax() {
        return isVerticallyOriented() ? scroller.getY2() : scroller.getX2();
    }

    public float getScrollerLength() {
        return isVerticallyOriented() ? scroller.getHeight() : scroller.getWidth();
    }

    public void setPageLength(float pageLength) {
        this.pageLength = pageLength;
        updateScroller();
    }

    public void setCenter(float cenx, float ceny) {
        super.setCenter(cenx, ceny);
        updateScroller();
    }

    public void setSize(float width, float height) {
        super.setSize(width, height);
        updateScroller();
    }

    public void setRect(Rect rect) {
        super.setRect(rect);
        updateScroller();
    }

    private void updateScroller() {
        if (isVerticallyOriented()) {
            float newHeight = PApplet.min(getHeight() / pageLength * this.getHeight(), getHeight());
            scroller.setRect(new Rect(this.getX1(), scroller.getY1(), this.getWidth(), newHeight, CORNER));
            scroller.setMin(getY1());
            scroller.setMax(getY2());
            scroller.translate(0, 0);
            // if (scroller.getHeight() == this.getHeight()) {
            // scroller.hide();
            // }
            // else {
            // scroller.show();
            // }
        } else {
            float newWidth = PApplet.min(getWidth() / getPageLength() * this.getWidth(), getWidth());
            scroller.setRect(new Rect(scroller.getX1(), this.getY1(), newWidth, this.getHeight(), CORNER));
            scroller.setMin(getX1());
            scroller.setMax(getX2());
            scroller.translate(0, 0);
            // if (scroller.getWidth() == this.getWidth()) {
            // scroller.hide();
            // }
            // else {
            // scroller.show();
            // }
        }
    }

    public float getPageLength() {
        return pageLength;
    }

    public boolean isHorizontallyOriented() {
        return this.getWidth() >= this.getHeight();
    }

    public boolean isVerticallyOriented() {
        return this.getHeight() >= this.getWidth();
    }

    public void setBackgroundColor(int bgColor) {
        this.color = bgColor;
    }

    public void setForegroundColor(int fgColor) {
        this.scroller.setColor(fgColor);
    }

    private float computeScrollerMin() {
        if (isHorizontallyOriented()) {
            return getX1();
        } else {
            return getY1();
        }
    }

    private float computeScrollerMax() {
        if (isHorizontallyOriented()) {
            return getX2();
        } else {
            return getY2();
        }
    }

    public static class Scroller extends Controller {
        protected float min, max;
        protected int color;

        public Scroller(float min, float max, ControllerUpdater updater, float priority) {
            super(updater, priority);
        }

        @Override
        public void draw(PGraphics g) {
            g.noStroke();
            g.fill(color);
            rect.draw(g);
        }

        public void translate(float dx, float dy) {
            if (isHorizontallyOriented()) {
                rect.setCenter(
                        PApplet.constrain(rect.getCenx() + dx, min + rect.getWidth() / 2f, max - rect.getWidth() / 2f),
                        getCeny());
            } else {
                rect.setCenter(getCenx(), PApplet.constrain(rect.getCeny() + dy, min + rect.getHeight() / 2f,
                        max - rect.getHeight() / 2f));
            }
        }

        public void mouseDragged(MouseEvent e) {
            translate(e.dx, e.dy);
            sendEvent(this);
        }

        public boolean isHorizontallyOriented() {
            return this.getWidth() >= this.getHeight();
        }

        public boolean isVerticallyOriented() {
            return this.getHeight() >= this.getWidth();
        }

        public void setMin(float min) {
            this.min = min;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}