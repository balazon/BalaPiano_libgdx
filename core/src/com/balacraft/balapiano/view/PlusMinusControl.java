package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class PlusMinusControl extends Group {

	Texture up, down;
	int srcX, srcY, srcW, srcH;

	int halfWidth, halfHeight;


	public PlusMinusControl(Texture up, Texture down, int srcX, int srcY, int srcW, int srcH) {
		this.up = up;
		this.down = down;
		this.srcX = srcX;
		this.srcY = srcY;
		this.srcW = srcW;
		this.srcH = srcH;
		halfWidth = (int)(srcW * 0.50001f);
		halfHeight = (int)(srcH * 0.50001f);
	}

	public void init() {
		debug();
		setSize(srcW, srcH);


		Sprite labelSprite = new Sprite(up, srcX, srcY, srcW, halfHeight);
		Sprite minus_up = new Sprite(up, srcX, srcY + halfHeight, halfWidth, halfHeight);
		Sprite minus_down = new Sprite(down, srcX, srcY + halfHeight, halfWidth, halfHeight);
		Sprite plus_up = new Sprite(up, srcX + halfWidth, srcY + halfHeight, halfWidth, halfHeight);
		Sprite plus_down = new Sprite(down, srcX + halfWidth, srcY + halfHeight, halfWidth, halfHeight);


		Image label = new Image(labelSprite);
		label.setBounds(0, halfHeight, srcW, halfHeight);

		ButtonActor minusButton = new ButtonActor() {
			@Override
			public void fire() {
				minusFire();
			}
		};
		minusButton.setSpritesUp(minus_up);
		minusButton.setSpritesDown(minus_down);
		minusButton.setSpriteGlobalTransform(new Rectangle(0, 0, halfWidth, halfHeight));

		ButtonActor plusButton = new ButtonActor() {
			@Override
			public void fire() {
				plusFire();
			}
		};
		plusButton.setSpritesUp(plus_up);
		plusButton.setSpritesDown(plus_down);
		plusButton.setSpriteGlobalTransform(new Rectangle(halfWidth, 0, halfWidth, halfHeight));
		addActor(label);
		addActor(minusButton);
		addActor(plusButton);
	}

	public abstract void minusFire();
	public abstract void plusFire();


}
