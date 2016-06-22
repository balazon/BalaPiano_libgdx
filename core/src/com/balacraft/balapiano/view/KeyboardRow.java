package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.balacraft.balapiano.soundengine.SoundSystem;

//A table, a navigator, and a rangecontrol
public class KeyboardRow extends Group {
	KeyboardTable keyboardTable;
	KeyboardNavigator keyboardNavigator;
	PlusMinusControl rangeControl;

	SoundSystem ss;
	int keyboardChannel;

	float contentWidth;
	float contentHeight;

	Texture texkeys_up;
	Texture texkeys_down;
	Texture texmod_up;
	Texture texmod_down;
	Texture tex_navigator;

	public KeyboardRow(int keyboardChannel, SoundSystem ss, Texture texkeys_up, Texture texkeys_down, Texture texmod_up, Texture texmod_down, Texture tex_navigator) {
		this.keyboardChannel = keyboardChannel;
		this.ss = ss;
		this.texkeys_up = texkeys_up;
		this.texkeys_down = texkeys_down;
		this.texmod_up = texmod_up;
		this.texmod_down = texmod_down;
		this.tex_navigator = tex_navigator;
	}

	public void init() {

		keyboardTable = new KeyboardTable(keyboardChannel, ss, texkeys_up, texkeys_down);
		addActor(keyboardTable);
		keyboardTable.init();


		keyboardNavigator = new KeyboardNavigator(keyboardTable , ss, texkeys_up, tex_navigator);
		addActor(keyboardNavigator);
		keyboardNavigator.init();


		rangeControl = new PlusMinusControl(texmod_up, texmod_down, 200, 128, 200, 128, false) {
			@Override
			public void minusFire() {
				keyboardNavigator.keyboardWidth = MathUtils.clamp(keyboardNavigator.keyboardWidth - 0.146341f / keyboardNavigator.rangeLength, 0.15f, 0.4f);
				keyboardNavigator.resizeKeyboardTable();
			}

			@Override
			public void plusFire() {
				keyboardNavigator.keyboardWidth = MathUtils.clamp(keyboardNavigator.keyboardWidth + 0.146341f / keyboardNavigator.rangeLength, 0.15f, 0.4f);
				keyboardNavigator.resizeKeyboardTable();
			}
		};

		addActor(rangeControl);
		rangeControl.init();


		contentWidth = 1000.0f;
		contentHeight = 400.0f;

		setSize(contentWidth, contentHeight);

		keyboardNavigator.resize(contentWidth * 0.1f, contentHeight * 0.9f, contentWidth * 0.8f, contentHeight * 0.1f);
		keyboardNavigator.resizeKeyboardTable(0, 0, contentWidth, contentHeight * 0.9f);

		rangeControl.setPosition(contentWidth * 0.9f, contentHeight * 0.9f);
		rangeControl.setScale(contentWidth * 0.1f / 200.0f, contentHeight * 0.1f / 64.0f);
	}

	public void resize(float x, float y, float width, float height) {
		setPosition(x, y);
		setScale(width / contentWidth, height / contentHeight);
	}
}
