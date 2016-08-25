package controllers;

import java.awt.Cursor;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

//TODO Editor in which to create GUIs.
//TODO Multiple active controllers? So mouseLeave isn't called when entering a nested controller?

/**
 * 
 * @author James Morrow
 *
 */
public class ControllerUpdater {
	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	private ArrayList<Float> priorities = new ArrayList<Float>();
	private PFont defaultFont;
	
    private Controller activeMouseEventReceiver;
    private Controller activeKeyEventReceiver;
    
    private boolean editMode;
    
    protected PApplet pa;
    
    private MouseEvent e;
    
    private int editColor = 0xFFF2CD51;
    
    public ControllerUpdater(PApplet pa) {
    	defaultFont = new PFont(PFont.findFont("font"), true);
    	this.pa = pa;
    	e = new MouseEvent(pa);
    }
    
    public void draw() {
    	for (int i=0; i<controllers.size(); i++) {
    		Controller c = controllers.get(i);
    		if (!c.isHidden()) {
    			c.draw(pa.getGraphics());
    		}
    	}
    	if (editMode) {
    		if (activeMouseEventReceiver != null) {
    			Controller c = activeMouseEventReceiver;
    			pa.noStroke();
    			pa.fill(0x77ffffff);
    			c.rect.display(pa.getGraphics());
    		}
    	}
    }
    
    public void mouseMoved() {
    	mouseMoved(e.set(pa.mouseX, pa.mouseY, pa.pmouseX, pa.pmouseY));
    }

    public void mouseMoved(MouseEvent e) {
        if (activeMouseEventReceiver != null) {
            if (!activeMouseEventReceiver.touches(e.x, e.y)) {   	
                activeMouseEventReceiver.mouseLeave(e);
                activeMouseEventReceiver = null;
            }
        }

    	int i = controllers.size() - 1;
        while (i >= 0) {
        	Controller c = controllers.get(i);
            if (c.touches(e.x, e.y)) {
            	if (c.isEnabled()) {
	            	if (activeMouseEventReceiver != c) {
	            		if (activeMouseEventReceiver != null) {
	            			activeMouseEventReceiver.mouseLeave(e);
	            		}
	            		activeMouseEventReceiver = c;
	            		if (!editMode) {
	            			c.mouseEnter(e);
	            		}
	            	}
	                
	                break;
            	}
            }
            i--;
        }  
    }
    
    public void mousePressed() {
    	mousePressed(e.set(pa.mouseX, pa.mouseY, pa.pmouseX, pa.pmouseY));
    }
    
    public void mousePressed(MouseEvent e) {
    	if (editMode) {
    		mousePressedEditMode(e);
    		return;
    	}
    	
    	if (activeMouseEventReceiver != null) {
    		activeMouseEventReceiver.mousePressed(e);
    	}
    }
    
    public void mouseReleased() {
    	mouseReleased(e.set(pa.mouseX, pa.mouseY, pa.pmouseX, pa.pmouseY));
    }
    
    public void mouseReleased(MouseEvent e) {
    	if (editMode) {
    		mouseReleasedEditMode(e);
    		return;
    	}
    	
    	if (activeMouseEventReceiver != null) {
    		activeMouseEventReceiver.mouseReleased(e);
    		if (!activeMouseEventReceiver.touches(e.x, e.y)) {
    			activeMouseEventReceiver.mouseLeave(e);
    			activeMouseEventReceiver = null;
    		}
    	}
    }
    
    public void mouseDragged() {
    	mouseDragged(e.set(pa.mouseX, pa.mouseY, pa.pmouseX, pa.pmouseY));
    }
    
    public void mouseDragged(MouseEvent e) {
    	if (editMode) {
    		mouseDraggedEditMode(e);
    		return;
    	}
    	
    	if (activeMouseEventReceiver != null) {
    		activeMouseEventReceiver.mouseDragged(e);
    	}
    }
    
    public void keyPressed(KeyEvent e) {
    	
    }
    
    public void mouseMovedEditMode(MouseEvent e) {
    	for (Controller c : controllers) {
    		if (c.touches(e.x, e.y)) {
    			
    		}
    	}
    }
    
    public void mousePressedEditMode(MouseEvent e) {
    	for (Controller c : controllers) {
    		if (c.touches(e.x, e.y)) {
    			
    		}
    	}
    }
    
    public void mouseReleasedEditMode(MouseEvent e) {
    	
    }
    
    public void mouseDraggedEditMode(MouseEvent e) {
    	for (Controller c : controllers) {
    		if (c.touches(e.x, e.y)) {
    			
    		}
    	}
    }
    
    public void hideAll() {
    	for (Controller c : controllers) {
    		c.hide();
    	}
    }
    
    public void freezeAll() {
    	for (Controller c : controllers) {
    		c.freeze();
    	}
    }
    
    public void showAll() {
    	for (Controller c : controllers) {
    		c.show();
    	}
    }
    
    public void unfreezeAll() {
    	for (Controller c : controllers) {
    		c.unfreeze();
    	}
    }
    
    public void addController(Controller e, float priorityValue) {
    	for (Controller d : controllers) {
    		if (d == e) return;
    	}
    	
    	for (int i=0; i<controllers.size(); i++) {
    		if (priorities.get(i) > priorityValue) {
    			controllers.add(i, e);
    			priorities.add(i, priorityValue);
    			return;
    		}
    	}
    	
    	controllers.add(e);
    	priorities.add(priorityValue);
    }
    
    public void removeController(Controller e) {
    	int i = controllers.indexOf(e);
    	if (i != -1) {
    		controllers.remove(i);
    		priorities.remove(i);
    	}
    	if (e == this.activeMouseEventReceiver) {
    		this.activeMouseEventReceiver = null;
    	}
    	if (e == this.activeKeyEventReceiver) {
    		this.activeKeyEventReceiver = null;
    	}
    }
    
    public float getControllerPriority(Controller c) {
    	int i = controllers.indexOf(c);
    	if (i != -1) {
    		return priorities.get(i);
    	}
    	else {
    		return -1;
    	}
    }
    
    public void setControllerPriority(Controller c, float priority) {
    	int i = controllers.indexOf(c);
    	if (i != -1) {
    		controllers.remove(i);
    		priorities.remove(i);
    		addController(c, priority);
    	}
    }
    
    public void clearControllers() {
    	controllers.clear();
    }
    
    public Controller getController(int i) {
    	return controllers.get(i);
    }
    
    public boolean containsController(Controller c) {
    	return controllers.contains(c);
    }
    
    public int size() {
    	return controllers.size();
    }
    
    public PFont getDefaultFont() {
    	return defaultFont;
    }
    
    public void enableEditMode() {
    	editMode = true;
    	pa.getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    public void disableEditMode() {
    	editMode = false;
    	pa.getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    public boolean hasActiveController() {
    	return activeMouseEventReceiver != null;
    }
    
    public void setPApplet(PApplet pa) {
    	this.pa = pa;
    }
    
    public String toString() {
    	String s = "[";
    	for (int i=0; i<controllers.size(); i++) {
    		Controller c = controllers.get(i);
    		s += c.toString() + ((i != controllers.size()-1) ? ", " : "");
    	}
    	return s + "]";
    }
}