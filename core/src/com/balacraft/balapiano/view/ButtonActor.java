package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonActor extends Group {

	protected Sprite[] sprites_up;
	protected Sprite[] sprites_down;

	protected boolean pressed = false;

	ClickListener clickListener;



	boolean autoTouchSupport;
	public ButtonActor() {
		this(true);
	}
	public ButtonActor(boolean autoTouchSupport) {
		this.autoTouchSupport = autoTouchSupport;
		initialize();
	}

	public ButtonActor(boolean autoTouchSupport, Sprite up, Sprite down) {
		this.autoTouchSupport = autoTouchSupport;
		initialize();

		setSpritesUp(up);
		setSpritesDown(down);
	}

	protected void initialize () {
		setTouchable(Touchable.enabled);

		if(!autoTouchSupport) {
			return;
		}
		clickListener = new ClickListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				ButtonActor.this.enter();
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				ButtonActor.this.exit();
			}

			public void touchDragged (InputEvent event, float x, float y, int pointer) {

			}
		};
		addListener(clickListener);

	}
	int downCount = 0;
	boolean incDownCount() {
		downCount++;
		//System.out.printf("downcount: %d\n", downCount);
		if(downCount == 1) {
			pressed = true;
			fire();
			return true;
		}
		return false;
	}
	void decDownCount() {
		downCount--;
		//System.out.printf("downcount: %d\n", downCount);
		if(downCount == 0) {
			pressed = false;
			release();
		}
	}
	public void enter() {
		incDownCount();
	}
	public void exit() {
		decDownCount();
	}
	public void through() {
		incDownCount();
		decDownCount();
	}

	public Actor hit (float x, float y, boolean touchable) {
		//System.out.println(String.format("hit: %.2f %.2f", x, y ));
		Actor h = super.hit(x, y, touchable);
		boolean c = contains(x,y);

		//System.out.println(String.format("h == this : %b, c: %b, %.2f %.2f", h == this, c, x, y));
		if(h == this) {
			c = contains(x,y);
			//System.out.println(String.format("hit, c: %.2f %.2f, %b", x, y, c));
			//System.out.println(String.format("hit, c: %.2f %.2f, %b", x, y, c));
		}
		if(h == this && contains(x, y)) {
			//System.out.println(String.format("%s, contains, hit: %.2f %.2f", toString(), x, y ));
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

	public void setSpriteLocalTransform(Rectangle... tr) {
		for(int i = 0; i < sprites_up.length; i++) {
			Rectangle r = tr[i];
			sprites_up[i].setBounds(r.x, r.y, r.width, r.height);
			sprites_down[i].setBounds(r.x, r.y, r.width, r.height);
		}
	}

	public void setSpriteGlobalTransform(Rectangle... tr) {
		Rectangle bounds = new Rectangle(tr[0]);
		for(int i = 0; i < sprites_up.length; i++) {
			bounds.merge(tr[i]);
		}
		for(int i = 0; i < sprites_up.length; i++) {
			Rectangle r = tr[i];
			sprites_up[i].setBounds(r.x - bounds.x, r.y - bounds.y, r.width, r.height);
			sprites_down[i].setBounds(r.x - bounds.x, r.y - bounds.y, r.width, r.height);
		}
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
	}



	public boolean isPressed() {
		return pressed;
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


	public void draggedFromTo(float x1, float y1, float x2, float y2, InputEvent event, int pointer) {
		if(isPressed()) {
			if(contains(x1,y1) && !contains(x2,y2)) {
				System.out.printf("%s drag exit (%.2f %.2f) (%.2f %.2f) %d\n", this.toString(),x1,y1,x2,y2,pointer);
				//clickListener.exit(event, x2, y2,pointer, null);
				exit();
			}
		}
		else if(contains(x2,y2)) {
			System.out.printf("%s drag enter (%.2f %.2f) (%.2f %.2f) %d\n", this.toString(),x1,y1,x2,y2,pointer);
			//clickListener.enter(event, x2, y2, pointer, null);
			enter();
		}else {
			for(Sprite s : sprites_up) {
				Rectangle rect = s.getBoundingRectangle();
				if( Algorithms.lineSegmentIntersectsRect(x1,y1,x2,y2, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height)) {
					System.out.printf("%s drag fire (%.2f %.2f) (%.2f %.2f) %d\n", this.toString(),x1,y1,x2,y2,pointer);
					through();
//					clickListener.enter(event, s.getX() + s.getWidth() * 0.5f, s.getY() + s.getHeight() * 0.5f, pointer, null);
//					clickListener.exit(event, s.getX() + s.getWidth() * 0.5f, s.getY() + s.getHeight() * 0.5f, pointer, null);
					break;
				}
			}
		}
	}

	public void fire() { }

	public void release() { }

	@Override
	public void draw (Batch batch, float parentAlpha) {
		if (isTransform()) applyTransform(batch, computeTransform());

		Sprite[] sprites = isPressed() ? sprites_down : sprites_up;
		for(Sprite s : sprites) {
			s.draw(batch);
		}

		if (isTransform()) resetTransform(batch);
	}
}
