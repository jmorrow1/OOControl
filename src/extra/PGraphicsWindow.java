package extra;

import processing.core.PGraphics;
import processing.core.PImage;

public class PGraphicsWindow extends PGraphics {
	private float x, y;
	private PGraphics pg;
	
	public PGraphicsWindow(float x, float y, PGraphics pg) {
		this.x = x;
		this.y = y;
		this.pg = pg;
	}
	
	/*****************************
	 ***** Getters / Setters *****
	 *****************************/
	
	public PGraphics getInnerPGraphics() {
		return pg;
	}
	
	public float getX1() {
		return x - pg.width/2f;
	}
	
	public void setCenter(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getY1() {
		return y - pg.height/2f;
	}
	
	public int getWidth() {
		return pg.width;
	}
	
	public int getHeight() {
		return pg.height;
	}
	
	@Override
	public void setSize(int width, int height) {
		pg.setSize(width, height);
	}
	
	/*******************
	 ***** General *****
	 *******************/
	
	@Override
	public void beginDraw() {
		pg.beginDraw();
	}
	
	@Override
	public void endDraw() {
		pg.endDraw();
	}
	
	/*************************
	 ***** 2D Primitives *****
	 *************************/
	
	@Override
	public void arc(float a, float b, float c, float d, float start, float stop) {
		switch (pg.getStyle().ellipseMode) {
			case CORNER : pg.arc(a - this.getX1(), b - this.getY1(), c, d, start, stop); break;
			case CENTER : pg.arc(a - this.getX1(), b - this.getY1(), c, d, start, stop); break;
			case RADIUS : pg.arc(a - this.getX1(), b - this.getY1(), c, d, start, stop); break;
			case CORNERS: pg.arc(a - this.getX1(), b - this.getY1(), c - this.getX1(), d - this.getY1(), start, stop); break;
		}
	}
	
	@Override
	public void ellipse(float a, float b, float c, float d) {
		switch (pg.getStyle().ellipseMode) {
			case CORNER : pg.ellipse(a - this.getX1(), b - this.getY1(), c, d); break;
			case CENTER : pg.ellipse(a - this.getX1(), b - this.getY1(), c, d); break;
			case RADIUS : pg.ellipse(a - this.getX1(), b - this.getY1(), c, d); break;
			case CORNERS: pg.ellipse(a - this.getX1(), b - this.getY1(), c - this.getX1(), d - this.getY1()); break;
		}
	}
	
	@Override
	public void line(float x1, float y1, float x2, float y2) {
		pg.line(x1 - this.getX1(), y1 - this.getY1(), x2 - this.getX1(), y2 - this.getY1());
	}
	
	@Override
	public void point(float x, float y) {
		pg.point(x - this.getX1(), y - this.getY1());
	}
	
	@Override
	public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		pg.quad(x1 - this.getX1(), y1 - this.getY1(), x2 - this.getX1(), y2 - this.getY1(),
				x3 - this.getX1(), y3 - this.getY1(), x4 - this.getX1(), y4 - this.getY1());
	}
	
	@Override
	public void rect(float a, float b, float c, float d) {
		switch (pg.getStyle().rectMode) {
			case CORNER : pg.rect(a - this.getX1(), b - this.getY1(), c, d); break;
			case CENTER : pg.rect(a - this.getX1(), b - this.getY1(), c, d); break;
			case RADIUS : pg.rect(a - this.getX1(), b - this.getY1(), c, d); break;
			case CORNERS: pg.rect(a - this.getX1(), b - this.getY1(), c - this.getX1(), d - this.getY1()); break;
		}
	}
	
	@Override
	public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		pg.triangle(x1 - this.getX1(), y1 - this.getY1(), x2 - this.getX1(), y2 - this.getY1(), x3 - this.getX1(), y3 - this.getY1());
	}
	
	/******************
	 ***** Vertex ***** 
	 ******************/
	
	@Override
	public void beginContour() {
		pg.beginContour();
	}
	
	@Override
	public void beginShape() {
		pg.beginShape();
	}
	
	@Override
	public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4) {
		pg.bezierVertex(x2 - this.getX1(), y2 - this.getY1(), x3 - this.getX1(), y3 - this.getY1(), x4 - this.getX1(), y4 - this.getY1());
	}
	
	@Override
	public void curveVertex(float x, float y) {
		pg.curveVertex(x - this.getX1(), y - this.getY1());
	}
	
	@Override
	public void endContour() {
		pg.endContour();
	}
	
	@Override
	public void endShape() {
		pg.endShape();
	}
	
	@Override
	public void quadraticVertex(float cx, float cy, float x3, float y3) {
		pg.quadraticVertex(cx - this.getX1(), cy - this.getY1(), x3 - this.getX1(), y3 - this.getY1());
	}
	
	@Override
	public void vertex(float x, float y) {
		pg.vertex(x - this.getX1(), y - this.getY1());
	}
	
	/******************
	 ***** Curves *****
	 ******************/
	
	@Override
	public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		pg.bezier(x1 - this.getX1(), y1 - this.getY1(), x2 - this.getX1(), y2 - this.getY1(),
				  x3 - this.getX1(), y3 - this.getY1(), x4 - this.getX1(), y4 - this.getY1());
	}
	
	@Override
	public void bezierDetail(int detail) {
		pg.bezierDetail(detail);
	}
	
	@Override
	public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		pg.curve(x1 - this.getX1(), y1 - this.getY1(), x2 - this.getX1(), y2 - this.getY1(),
				 x3 - this.getX1(), y3 - this.getY1(), x4 - this.getX1(), y4 - this.getY1());
	}
	
	@Override
	public void curveDetail(int detail) {
		pg.curveDetail(detail);
	}
	
	@Override
	public void curveTightness(float tightness) {
		pg.curveTightness(tightness);
	}
	
	/*****************
	 ***** Color *****
	 *****************/
	
	@Override
	public void background(float gray) {
		pg.background(gray);
	}
	
	@Override
	public void background(float gray, float alpha) {
		pg.background(gray, alpha);
	}
	
	@Override
	public void background(float a, float b, float c) {
		pg.background(a, b, c);
	}
	
	@Override
	public void background(float a, float b, float c, float alpha) {
		pg.background(a, b, c, alpha);
	}
	
	@Override
	public void background(int col) {
		pg.background(col);
	}
	
	@Override
	public void background(int col, float alpha) {
		pg.background(col, alpha);
	}
	
	@Override
	public void background(PImage img) {
		pg.background(img);
	}
	
	@Override
	public void clear() {
		pg.clear();
	}
	
	@Override
	public void colorMode(int colorMode) {
		pg.colorMode(colorMode);
	}
	
	@Override
	public void fill(float gray) {
		pg.fill(gray);
	}
	
	@Override
	public void fill(int col) {
		pg.fill(col);
	}
	
	@Override
	public void fill(int col, float alpha) {
		pg.fill(col, alpha);
	}
	
	@Override
	public void fill(float gray, float alpha) {
		pg.fill(gray, alpha);
	}
	
	@Override
	public void fill(float a, float b, float c) {
		pg.fill(a, b, c);
	}
	
	@Override
	public void fill(float a, float b, float c, float d) {
		pg.fill(a, b, c, d);
	}
	
	@Override
	public void noFill() {
		pg.noFill();
	}
	
	@Override
	public void noStroke() {
		pg.noStroke();
	}
	
	@Override
	public void stroke(int col) {
		pg.stroke(col);
	}
	
	@Override
	public void stroke(int col, float alpha) {
		pg.stroke(col, alpha);
	}
	
	@Override
	public void stroke(float gray) {
		pg.stroke(gray);
	}
	
	@Override
	public void stroke(float gray, float alpha) {
		pg.stroke(gray, alpha);
	}
	
	@Override
	public void stroke(float a, float b, float c) {
		pg.stroke(a, b, c);
	}
	
	@Override
	public void stroke(float a, float b, float c, float alpha) {
		pg.stroke(a, b, c, alpha);
	}
	
	/**********************
	 ***** Attributes *****
	 **********************/
	
	@Override
	public void ellipseMode(int mode) {
		pg.ellipseMode(mode);
	}
	
	@Override
	public void rectMode(int mode) {
		pg.rectMode(mode);
	}
	
	@Override
	public void strokeCap(int cap) {
		pg.strokeCap(cap);
	}
	
	@Override
	public void strokeJoin(int join) {
		pg.strokeJoin(join);
	}
	
	@Override
	public void strokeWeight(float weight) {
		pg.strokeWeight(weight);
	}
	
	/********************************
	 ***** Loading & Displaying *****
	 ********************************/
	
	@Override
	public void image(PImage img, float a, float b) {
		pg.image(img, a, b);
	}
	
	@Override
	public void image(PImage img, float a, float b, float c, float d) {
		pg.image(img, a, b, c, d);
	}
	
	@Override
	public void imageMode(int mode) {
		pg.imageMode(mode);
	}
	
	@Override
	public void noTint() {
		pg.noTint();
	}
	
	@Override
	public void tint(int col) {
		pg.tint(col);
	}
	
	@Override
	public void tint(int col, float alpha) {
		pg.tint(col, alpha);
	}
	
	@Override
	public void tint(float gray) {
		pg.tint(gray);
	}
	
	@Override
	public void tint(float gray, float alpha) {
		pg.tint(gray, alpha);
	}
	
	@Override
	public void tint(float a, float b, float c) {
		pg.tint(a, b, c);
	}
	
	@Override
	public void tint(float a, float b, float c, float alpha) {
		pg.tint(a, b, c, alpha);
	}
	
	/******************
	 ***** Pixels *****
	 ******************/
	
	/*********************
	 ***** Rendering *****
	 *********************/
}
