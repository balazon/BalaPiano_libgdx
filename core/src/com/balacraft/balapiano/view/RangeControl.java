package com.balacraft.balapiano.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RangeControl extends Group {

	KeyboardNavigator keyboardNavigator;

	//Group rangeParent;
	public RangeControl(KeyboardNavigator kn) {
		this.keyboardNavigator = kn;
	}


	public void init() {
		debug();
		setSize(200, 128);
		Texture texmod_up = new Texture(Gdx.files.internal("data/modifiers_unpressed.png"));
		Texture texmod_down = new Texture(Gdx.files.internal("data/modifiers_pressed.png"));

		Sprite rangeSprite = new Sprite(texmod_up, 200, 128, 200, 64);
		Sprite rangeminus_up = new Sprite(texmod_up, 200, 192, 100, 64);
		Sprite rangeminus_down = new Sprite(texmod_down, 200, 192, 100, 64);
		Sprite rangeplus_up = new Sprite(texmod_up, 300, 192, 100, 64);
		Sprite rangeplus_down = new Sprite(texmod_down, 300, 192, 100, 64);


		Image rangeLabel = new Image(rangeSprite);
		rangeLabel.setBounds(0, 64.0f, 200.0f, 64.0f);

		ButtonActor rangeminus = new ButtonActor() {
			@Override
			public void fire() {
				keyboardNavigator.keyboardWidth = MathUtils.clamp(keyboardNavigator.keyboardWidth - 0.146341f / keyboardNavigator.rangeLength, 0.15f, 0.4f);
				keyboardNavigator.resizeKeyboardTable();
			}
		};
		rangeminus.setSpritesUp(rangeminus_up);
		rangeminus.setSpritesDown(rangeminus_down);
		rangeminus.setSpriteGlobalTransform(new Rectangle(0, 0, 100, 64));

		ButtonActor rangeplus = new ButtonActor() {
			@Override
			public void fire() {
				keyboardNavigator.keyboardWidth = MathUtils.clamp(keyboardNavigator.keyboardWidth + 0.146341f / keyboardNavigator.rangeLength, 0.15f, 0.4f);
				keyboardNavigator.resizeKeyboardTable();
			}
		};
		rangeplus.setSpritesUp(rangeplus_up);
		rangeplus.setSpritesDown(rangeplus_down);
		rangeplus.setSpriteGlobalTransform(new Rectangle(100, 0, 100, 64));
		addActor(rangeLabel);
		addActor(rangeminus);
		addActor(rangeplus);
	}
}
