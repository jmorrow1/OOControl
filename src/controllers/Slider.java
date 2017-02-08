package controllers;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Slider extends Controller {
    private float minValue, maxValue, currValue, tick;
    private ControllerDisplay<Slider> sliderDisplay = new DefaultDisplay();

    public Slider(float initValue, float minValue, float maxValue, ControllerUpdater updater, float priority) {
        super(updater, priority);
        this.currValue = PApplet.constrain(initValue, minValue, maxValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Slider(float minValue, float maxValue, ControllerUpdater updater, float priority) {
        this(minValue, minValue, maxValue, updater, priority);
    }

    @Override
    public void draw(PGraphics pg) {
        sliderDisplay.display(pg, this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        handleMousePress(e.x, e.y);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        handleMousePress(e.x, e.y);
    }

    private void handleMousePress(int x, int y) {
        float prevValue = currValue;
        if (tick <= 0) {
            if (getWidth() >= getHeight()) {
                setValue(PApplet.map(x, getX1(), getX2(), minValue, maxValue));
            } else {
                setValue(PApplet.map(y, getY2(), getY1(), minValue, maxValue));
            }
        } else {
            if (getWidth() >= getHeight()) {
                setValue(quantize(PApplet.map(x, getX1(), getX2(), minValue, maxValue), minValue, tick));
            } else {
                setValue(quantize(PApplet.map(y, getY2(), getY1(), minValue, maxValue), minValue, tick));
            }
        }
        if (currValue != prevValue) {
            sendEvent(this);
        }
    }

    public static class DefaultDisplay implements ControllerDisplay<Slider> {
        public boolean drawLimits, drawValue;

        public DefaultDisplay() {
            this(false, true);
        }

        public DefaultDisplay(boolean drawLimits, boolean drawValue) {
            this.drawLimits = drawLimits;
            this.drawValue = drawValue;
        }

        @Override
        public void display(PGraphics pg, Slider s) {
            final float diam = 12;
            float x1 = s.getX1() + diam / 2f;
            float x2 = s.getX2() - diam / 2f;
            float y1 = s.getY1() + diam / 2f;
            float y2 = s.getY2() - diam / 2f;

            pg.stroke(s.getColorInCurrentContext());
            pg.fill(s.getColorInCurrentContext());
            pg.strokeWeight(4);
            pg.strokeCap(ROUND);
            if (s.isHorizontallyOriented()) {
                pg.line(x1, s.getCeny(), x2, s.getCeny());
                pg.noStroke();
                float x = PApplet.map(s.getValue(), s.getMinValue(), s.getMaxValue(), x1, x2);
                pg.ellipseMode(CENTER);
                pg.ellipse(x, s.getCeny(), diam, diam);
            } else {
                pg.line(s.getCenx(), y1, s.getCenx(), y2);
                pg.noStroke();
                float y = PApplet.map(s.getValue(), s.getMinValue(), s.getMaxValue(), y2, y1);
                pg.ellipseMode(CENTER);
                pg.ellipse(s.getCenx(), y, diam, diam);
            }

            if (s.isHorizontallyOriented()) {
                if (drawLimits) {
                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(CENTER, BOTTOM);
                    pg.text(format(s.getMinValue()), s.getX1(), s.getCeny() - 10);

                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(CENTER, BOTTOM);
                    pg.text(format(s.getMaxValue()), s.getX2(), s.getCeny() - 10);
                }

                if (drawValue) {
                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(CENTER, TOP);
                    float currentPosition = PApplet.map(s.getValue(), s.getMinValue(), s.getMaxValue(), x1, x2);
                    pg.text(format(s.getValue()), currentPosition, s.getCeny() + 10);
                }
            } else if (s.isVerticallyOriented()) {
                if (drawLimits) {
                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(CENTER, TOP);
                    pg.text(format(s.getMinValue()), s.getCenx(), s.getY2() - 6);

                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(CENTER, BOTTOM);
                    pg.text(format(s.getMaxValue()), s.getCenx(), s.getY1() + 6);
                }

                if (drawValue) {
                    pg.textSize(12);
                    pg.fill(s.getColorInCurrentContext());
                    pg.textAlign(LEFT, CENTER);
                    float currentPosition = PApplet.map(s.getValue(), s.getMinValue(), s.getMaxValue(), y2, y1);
                    pg.text(format(s.getValue()), s.getCenx() + 10, currentPosition);
                }
            }
        }

        private static String format(float f) {
            int numDecimals = PApplet.min(3, numAfterDecimal(f));
            return String.format("%." + numDecimals + "f", f);
        }

        private static int numAfterDecimal(float f) {
            if (PApplet.floor(f) == f) {
                return 0;
            }

            String s = Float.toString(f);

            int count = 0;
            boolean startCounting = false;
            for (int i = 0; i < s.length(); i++) {
                if (startCounting) {
                    count++;
                } else {
                    if (s.charAt(i) == '.') {
                        startCounting = true;
                    }
                }
            }

            return count;
        }
    }

    public boolean isHorizontallyOriented() {
        return this.getWidth() >= this.getHeight();
    }

    public boolean isVerticallyOriented() {
        return this.getHeight() >= this.getWidth();
    }

    public void setValue(float currValue) {
        this.currValue = PApplet.constrain(currValue, minValue, maxValue);
    }

    public void setValueRange(float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    public float getNormalValue() {
        return PApplet.map(currValue, minValue, maxValue, 0, 1);
    }

    public float getValue() {
        return currValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }

    public void setNoTicks() {
        this.tick = 0;
    }

    public void setDisplay(ControllerDisplay<Slider> display) {
        this.sliderDisplay = display;
    }

    private static float quantize(float val, float min, float quantum) {
        val -= min;
        val /= quantum;
        val = (int) val;
        return min + val * quantum;
    }
}