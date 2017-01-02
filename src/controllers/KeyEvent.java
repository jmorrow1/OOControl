package controllers;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class KeyEvent {
    public PApplet pa;
    public int key, keyCode;

    public KeyEvent(PApplet pa) {
        this.pa = pa;
    }

    public void set(int key, int keyCode) {
        this.key = key;
        this.keyCode = keyCode;
    }

    public boolean coded() {
        return key == PApplet.CODED;
    }
}