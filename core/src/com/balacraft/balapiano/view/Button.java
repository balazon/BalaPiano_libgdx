package com.balacraft.balapiano.view;

import com.badlogic.gdx.math.Rectangle;

public abstract class Button {
	Rectangle r1;
	
	public abstract boolean isPressed();
	public Rectangle[] getRects() {
		Rectangle[] res = new Rectangle[1];
		res[0] = r1;
		return res;
	}
	public void resize(Rectangle[] r) {
		r1.set(r[0]);
	}
	public boolean contains(int x, int y) {
		return r1.contains(x, y);
	}
	
	//empty methods for stuff (hooks)
	public void pressed(int x, int y) {	}
	public void released(int x, int y) { }
	public void draggedFromTo(int x1, int y1, int x2, int y2) { }
}
