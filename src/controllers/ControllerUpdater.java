package controllers;

import java.awt.Cursor;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

//TODO Editor in which to create GUIs.

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
    
    private boolean adjustControllersMode;
    
    protected PApplet pa;
    
    public ControllerUpdater(PApplet pa) {
    	defaultFont = new PFont(PFont.findFont("font"), true);
    	this.pa = pa;
    }
    
    public void draw() {
    	for (int i=0; i<controllers.size(); i++) {
    		Controller c = controllers.get(i);
    		if (!c.isHidden()) {
    			c.draw(pa);
    		}
    	}
    }

    public void mouseMoved(MouseEvent e) {
    	if (adjustControllersMode) {
    		mouseMovedAdjustMode(e);
    		return;
    	}
    	
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
	            		c.mouseEnter(e);
	            	}
	                
	                break;
            	}
            }
            i--;
        }  
    }
    
    public void mousePressed(MouseEvent e) {
    	if (adjustControllersMode) {
    		mousePressedAdjustMode(e);
    		return;
    	}
    	
    	if (activeMouseEventReceiver != null) {
    		activeMouseEventReceiver.mousePressed(e);
    	}
    }
    
    public void mouseReleased(MouseEvent e) {
    	if (adjustControllersMode) {
    		mouseReleasedAdjustMode(e);
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
    
    public void mouseDragged(MouseEvent e) {
    	if (adjustControllersMode) {
    		mouseDraggedAdjustMode(e);
    		return;
    	}
    	
    	if (activeMouseEventReceiver != null) {
    		activeMouseEventReceiver.mouseDragged(e);
    	}
    }
    
    public void keyPressed(KeyEvent e) {
    	
    }
    
    public void mouseMovedAdjustMode(MouseEvent e) {
    	for (Controller c : controllers) {
    		if (c.touches(e.x, e.y)) {
    			
    		}
    	}
    }
    
    public void mousePressedAdjustMode(MouseEvent e) {
    	
    }
    
    public void mouseReleasedAdjustMode(MouseEvent e) {
    	
    }
    
    public void mouseDraggedAdjustMode(MouseEvent e) {
    	for (Controller c : controllers) {
    		if (c.touches(e.x, e.y)) {
    			
    		}
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
    
    public void clearControllers() {
    	controllers.clear();
    }
    
    public Controller getController(int i) {
    	return controllers.get(i);
    }
    
    public int size() {
    	return controllers.size();
    }
    
    public PFont getDefaultFont() {
    	return defaultFont;
    }
    
    public void activateAdjustControllersMode() {
    	adjustControllersMode = true;
    	pa.getSurface().setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    public void deactivateAdjustControllersMode() {
    	adjustControllersMode = false;
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