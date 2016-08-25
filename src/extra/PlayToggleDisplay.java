package extra;

import controllers.ControllerDisplay;
import controllers.Toggle;
import processing.core.PApplet;
import processing.core.PGraphics;

public class PlayToggleDisplay implements ControllerDisplay<Toggle> {
	public final static PlayToggleDisplay instance = new PlayToggleDisplay();
	
	private PlayToggleDisplay() {}

	@Override
	public void display(PGraphics pg, Toggle t) {
		pg.fill(t.getColorInCurrentContext());
		if (! (t.getState() == 1)) {
			drawPlayEmblem(t.getCenx(), t.getCeny(), t.getWidth()/2f, t.getHeight()/2f, pg);
		}
		else {
			drawPauseEmblem(t.getCenx(), t.getCeny(), t.getWidth()/2f, t.getHeight()/2f, pg);
		}
	}
	
	private void drawPlayEmblem(float x, float y, float xRadius, float yRadius, PGraphics pg) {
		xRadius *= 0.66f;
		yRadius *= 0.66f;
		pg.triangle(x + xRadius, y,
				    x + xRadius*PApplet.cos(0.7f*pg.PI), y + yRadius*PApplet.sin(0.7f*pg.PI),
				    x + xRadius*PApplet.cos(1.3f*pg.PI), y + yRadius*PApplet.sin(1.3f*pg.PI));
	}
	
	private void drawPauseEmblem(float x, float y, float xRadius, float yRadius, PGraphics pg) {
		float rectWidth = xRadius * 0.3f;
		float rectHeight = yRadius * 0.9f;
		
		pg.rectMode(pg.CENTER);
		pg.rect(x - rectWidth, y, rectWidth, rectHeight);
		pg.rect(x + rectWidth, y, rectWidth, rectHeight);
	}
}