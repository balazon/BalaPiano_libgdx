package com.balacraft.balapiano.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.balacraft.balapiano.soundengine.SoundSystem;

public class KeyboardNavigator extends Group {
	KeyboardTable kt;
	Texture tex;
	SoundSystem ss;

	ClickListener clickListener;

	Sprite octave;

	float rangeOffset;
	float rangeLength;

	int texW = 820;
	int texH = 512;

	Actor maskArea;

	public KeyboardNavigator(KeyboardTable kt, SoundSystem ss, Texture tex) {
		this.kt = kt;
		this.ss = ss;
		this.tex = tex;
	}

	public void init() {

		octave = new Sprite(tex, 0, 0, 820, 512);


		setTouchable(Touchable.enabled);
		addListener(clickListener = new ClickListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("KN click!");
				centerX = x / (rangeLength * 820);
				resizeKeyboardTable();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				centerX = x / (rangeLength * 820);
				resizeKeyboardTable();
			}
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				//System.out.println("KN enter");
			}
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				//System.out.println("KN exit");
			}
		});

		rangeOffset = 0.0f;
		rangeLength = 6.0f + (28.0f / 164.0f);

		setDebug(true);

		maskArea = new Actor();
		maskArea.setDebug(true);
		addActor(maskArea);

	}

	float centerX = 0.5f;

	public void resize(float x, float y, float width, float height) {
		setBounds(x, y, rangeLength * 820, 512);
		setScale(width / (820.0f * rangeLength), height / 512.0f);
	}

	int windowW, windowH;
	public void resizeKeyboardTable() {
		resizeKeyboardTable(windowW, windowH);
	}
	public void resizeKeyboardTable(int w, int h) {
		windowW = w;
		windowH = h;
		float conversionRateToMilliMeter = 25.4f / (160.0f * Gdx.graphics.getDensity());
		float c = 1.0f;
		c = 100 / (0.7f * h * conversionRateToMilliMeter);
		kt.resize(0, h * 0.15f, w * 0.9f, h * 0.7f, centerX, w * conversionRateToMilliMeter * 1.0f * c);

		float mw =  (w * conversionRateToMilliMeter * 1.0f * c) / 1012.0f * rangeLength * 820.0f;


		maskArea.setBounds(rangeLength * centerX * 820.0f - mw * 0.5f, 0, mw, 512.0f);
		maskArea.debug();

	}


	@Override
	public void draw (Batch batch, float parentAlpha) {

		if (isTransform()) applyTransform(batch, computeTransform());


		int wholeOffset = 0;
		if(rangeOffset > 0.0f) {
			wholeOffset = (int)((1.0f - rangeOffset) * 820);
			batch.draw(octave.getTexture(), 0, 0, (int)(rangeOffset * 820), 0, wholeOffset * 820, 512);
		}

		int whole = (int) (rangeLength - rangeOffset);
		for(int i = 0; i < whole; i++) {
			batch.draw(octave, wholeOffset + i * 820.0f, 0);
		}
		float lastLength = rangeLength - rangeOffset - whole;
		float lastX = (rangeOffset + whole) * 820.0f;

		batch.draw(octave.getTexture(), lastX, 0, 0, 0, (int)(lastLength * 820), 512);


		if (isTransform()) resetTransform(batch);
	}


}
