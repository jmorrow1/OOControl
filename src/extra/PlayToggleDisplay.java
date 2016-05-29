package extra;

import controllers.ControllerDisplay;
import controllers.Toggle;
import processing.core.PApplet;

public class PlayToggleDisplay implements ControllerDisplay<Toggle> {
	public final static PlayToggleDisplay instance = new PlayToggleDisplay();
	
	private PlayToggleDisplay() {}

	@Override
	public void display(PApplet pa, Toggle t) {
		pa.fill(t.getColorInCurrentContext());
		if (! (t.getState() == 1)) {
			drawPlayEmblem(t.getCenx(), t.getCeny(), t.getWidth()/2f, t.getHeight()/2f, pa);
		}
		else {
			drawPauseEmblem(t.getCenx(), t.getCeny(), t.getWidth()/2f, t.getHeight()/2f, pa);
		}
	}
	
	private void drawPlayEmblem(float x, float y, float xRadius, float yRadius, PApplet pa) {
		xRadius *= 0.66f;
		yRadius *= 0.66f;
		pa.triangle(x + xRadius, y,
				    x + xRadius*pa.cos(0.7f*pa.PI), y + yRadius*pa.sin(0.7f*pa.PI),
				    x + xRadius*pa.cos(1.3f*pa.PI), y + yRadius*pa.sin(1.3f*pa.PI));
	}
	
	private void drawPauseEmblem(float x, float y, float xRadius, float yRadius, PApplet pa) {
		float rectWidth = xRadius * 0.3f;
		float rectHeight = yRadius * 0.9f;
		
		pa.rectMode(pa.CENTER);
		pa.rect(x - rectWidth, y, rectWidth, rectHeight);
		pa.rect(x + rectWidth, y, rectWidth, rectHeight);
	}
}