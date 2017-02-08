package extra;

import controllers.ControllerDisplay;
import controllers.Toggle;
import processing.core.PApplet;
import processing.core.PGraphics;

public class PlayToggleDisplay implements ControllerDisplay<Toggle> {
    public final static PlayToggleDisplay instance = new PlayToggleDisplay();

    private PlayToggleDisplay() {
    }

    @Override
    public void display(PGraphics pg, Toggle t) {
        pg.fill(t.getColorInCurrentContext());
        pg.noStroke();
        if (!(t.getState() == 1)) {
            drawPlayEmblem(t.getCenx(), t.getCeny(), 0.3f * t.getWidth(), 0.33f * t.getHeight(), pg);
        } else {
            drawPauseEmblem(t.getCenx(), t.getCeny(), 0.33f * t.getWidth(), 0.25f * t.getHeight(), pg);
        }
    }

    private void drawPlayEmblem(float x, float y, float xRadius, float yRadius, PGraphics pg) {
        pg.triangle(x + xRadius, y, x - xRadius, y + yRadius * PApplet.sin(0.7f * PI), x - xRadius,
                y + yRadius * PApplet.sin(1.3f * PI));
    }

    private void drawPauseEmblem(float x, float y, float xRadius, float yRadius, PGraphics pg) {
        pg.rectMode(RADIUS);
        pg.rect(x - 0.5f * xRadius, y, 0.25f * xRadius, yRadius);
        pg.rect(x + 0.5f * xRadius, y, 0.25f * xRadius, yRadius);
    }
}