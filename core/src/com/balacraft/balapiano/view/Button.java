package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

	//Rectangle[] transform;

	protected Sprite[] sprites_up;
	protected Sprite[] sprites_down;

	boolean isPressed = false;

	public Button() {

		//this(null, null, null, null, null);
	}

	public Button(Sprite up, Sprite down) {
		setSpritesUp(up);
		setSpritesDown(down);
	}


	public void setSpritesUp(Sprite... up) {
		sprites_up = new Sprite[up.length];
		for(int i = 0; i < up.length; i++) {
			sprites_up[i] = new Sprite(up[i]);
		}
	}
	public void setSpritesDown(Sprite... down) {
		sprites_down = new Sprite[down.length];
		for(int i = 0; i < down.length; i++) {
			sprites_down[i] = new Sprite(down[i]);
		}
	}

	public void setTransform(Rectangle... tr) {
		for(int i = 0; i < sprites_up.length; i++) {
			Rectangle r = tr[i];
			sprites_up[i].setBounds(r.x, r.y, r.width, r.height);
			sprites_down[i].setBounds(r.x, r.y, r.width, r.height);
		}
	}


	public boolean isPressed() {
		return isPressed;
	}


	public boolean contains(int x, int y) {
		for(Sprite s : sprites_up) {
			Rectangle r = s.getBoundingRectangle();
			if(r.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	

	public void pressed(int x, int y) {
		if(contains(x, y) && !isPressed()) {
			isPressed = true;
			fire();
		}
	}
	public void released(int x, int y) {
		if(contains(x, y)) {
			isPressed = false;
		}
	}
	public void draggedFromTo(int x1, int y1, int x2, int y2) { }
	public void fire() { }


	public void draw(SpriteBatch batch, Camera cam) {

		Sprite[] sprites = isPressed() ? sprites_down : sprites_up;
		for(Sprite s : sprites) {
			s.draw(batch);
		}

	}




}
