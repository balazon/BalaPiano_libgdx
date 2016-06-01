package com.balacraft.balapiano.view;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class Button {
	List<Rectangle> clickArea;

	public Button() {
		clickArea = new ArrayList<Rectangle>(2);
	}
	public abstract boolean isPressed();
//	public Rectangle[] getRectsASD() {
//		Rectan
//		return (Rectangle[]) clickArea.toArray();
//	}
	public void addClickRectangle(Rectangle r) {
		clickArea.add(r);
	}
	public void resize(Rectangle[] r) {
		int min = Math.min(clickArea.size(), r.length);
		for(int i = 0; i < min; i++) {
			clickArea.get(0).set(r[i]);
		}
	}
	public boolean contains(int x, int y) {
		for(Rectangle r : clickArea) {
			if(r.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	//empty methods for stuff (hooks)
	public void pressed(int x, int y) {	}
	public void released(int x, int y) { }
	public void draggedFromTo(int x1, int y1, int x2, int y2) { }
}
