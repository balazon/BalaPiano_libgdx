package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.balacraft.balapiano.MyGdxPiano;

public class ButtonActor extends Actor{

	protected Sprite[] sprites_up;
	protected Sprite[] sprites_down;

	boolean isPressed = false;

	ClickListener clickListener;

	protected Rectangle[] rects;

	public ButtonActor() {

		//this(null, null, null, null, null);
	}

	public ButtonActor(Sprite up, Sprite down) {
		setSpritesUp(up);
		setSpritesDown(down);
	}

	private void initialize () {
		setTouchable(Touchable.enabled);
		addListener(clickListener = new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {
				fire();
			}
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				float x1 = MyGdxPiano.instance.x1[pointer];
				float y1 = MyGdxPiano.instance.y1[pointer];
				draggedFromTo(x1, y1, x, y);
			}
		});

	}

	public Actor hit (float x, float y, boolean touchable) {
		Actor h = super.hit(x, y, touchable);
		if(h == this && contains(x, y)) {
			return this;
		}
		return null;
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

//	public void setTransform(Rectangle... tr) {
//		for(int i = 0; i < sprites_up.length; i++) {
//			Rectangle r = tr[i];
//			sprites_up[i].setBounds(r.x, r.y, r.width, r.height);
//			sprites_down[i].setBounds(r.x, r.y, r.width, r.height);
//		}
//	}



	public boolean isPressed() {
		return isPressed;
	}


	public boolean contains(float x, float y) {
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
	public void draggedFromTo(float x1, float y1, float x2, float y2) { }
	public void fire() { }



	@Override
	public void draw (Batch batch, float parentAlpha) {
		Sprite[] sprites = isPressed() ? sprites_down : sprites_up;
		for(Sprite s : sprites) {
			s.draw(batch);
		}
	}
}
