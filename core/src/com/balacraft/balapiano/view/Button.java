package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class Button implements Drawable {

	//Rectangle[] transform;

	protected Sprite[] sprites_up;
	protected Sprite[] sprites_down;

	boolean isPressed = false;

	public Button() {
		//this(null, null, null, null, null);
	}
	//TODO do it with sprites!

	public Button(Sprite up, Sprite down) {
		setSpritesUp(up).setSpritesUp(down);
	}

	public Button setSpritesUp(Sprite... up) {
		sprites_up = new Sprite[up.length];
		for(int i = 0; i < up.length; i++) {
			sprites_up[i] = new Sprite(up[i]);
		}
		return this;
	}
	public Button setSpritesDown(Sprite... down) {
		sprites_down = new Sprite[down.length];
		for(int i = 0; i < down.length; i++) {
			sprites_down[i] = new Sprite(down[i]);
		}
		return this;
	}

	public Button setTransform(Rectangle... tr) {
		for(int i = 0; i < sprites_up.length; i++) {
			Rectangle r = tr[i];
			sprites_up[i].setBounds(r.x, r.y, r.width, r.height);
			sprites_down[i].setBounds(r.x, r.y, r.width, r.height);
		}
		return this;
	}

//	public Button(Texture unpressed, Texture pressed, Rectangle tr, Rectangle src_up, Rectangle src_down) {
//		this.texture_unpressed = unpressed;
//		this.texture_pressed = pressed;
//		setTransform(tr);
//		setSrcUp(src_up);
//		setSrcDown(src_down);
//	}
//
//	public Button setTex(Texture up, Texture down) {
//		texture_unpressed = up;
//		texture_pressed = down;
//		return this;
//	}
//
//	public Button setTransform(Rectangle... tr) {
//		transform = tr;
//		return this;
//	}
//
//	public Button setSrcUp(Rectangle... src_up) {
//		src_unpressed = src_up;
//		return this;
//	}
//
//	public Button setSrcDown(Rectangle... src_down) {
//		src_pressed = src_down;
//		return this;
//	}



//	public Button(Rectangle clickArea, Texture unpressed, Texture pressed, Rectangle texRegion_unpressed, Rectangle texRegion_pressed) {
//		this.clickArea = new ArrayList<Rectangle>();
//		this.clickArea.add(clickArea);
//		this.texture_unpressed = unpressed;
//		this.texture_pressed = pressed;
//		texAre
//	}
	public boolean isPressed() {
		return isPressed;
	}


//	public void addClickRectangle(Rectangle r) {
//		clickArea.add(r);
//	}

//	public void resize(Rectangle[] r) {
//		int min = Math.min(transform.length, r.length);
//		for(int i = 0; i < min; i++) {
//			transform[0].set(r[i]);
//		}
//	}
	public boolean contains(int x, int y) {
		for(Sprite s : sprites_up) {
			Rectangle r = s.getBoundingRectangle();
			if(r.contains(x, y)) {
				return true;
			}
		}
		return false;
//
//		for(Rectangle r : transform) {
//			if(r.contains(x, y)) {
//				return true;
//			}
//		}
//		return false;
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


	public void draw(SpriteBatch batch) {

		Sprite[] sprites = isPressed() ? sprites_down : sprites_up;
		for(Sprite s : sprites) {
			s.draw(batch);
		}

//		int min = Math.min(transform.length, Math.min(src_unpressed.length, src_pressed.length));
//		boolean pressed = isPressed();
//		Texture tex = pressed ? texture_pressed : texture_unpressed;
//		Rectangle[] texSources = pressed ? src_pressed : src_unpressed;
//		for(int i = 0; i < min; i++) {
//			Rectangle r = transform[i];
//			Rectangle src = texSources[i];
//			batch.draw(tex,r.x,r.y,r.width,r.height,
//					(int)src.x,(int)src.y,(int)src.width,(int)src.height,false,false);
//		}

	}




}
