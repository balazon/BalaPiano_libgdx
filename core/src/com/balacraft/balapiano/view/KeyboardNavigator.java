package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.balacraft.balapiano.soundengine.SoundSystem;

public class KeyboardNavigator extends Group {
	KeyboardTable kt;
	Texture tex_keys;
	Texture tex_navigator;
	SoundSystem ss;

	ClickListener clickListener;

	Sprite octave;

	float rangeOffset;
	float rangeLength;

	int texW = 820;
	int texH = 512;

	Actor maskArea;



	public KeyboardNavigator(KeyboardTable kt, SoundSystem ss, Texture tex_keys, Texture tex_navigator) {
		this.kt = kt;
		this.ss = ss;
		this.tex_keys = tex_keys;
		this.tex_navigator = tex_navigator;
	}


	public void init() {
		octave = new Sprite(tex_keys, 0, 0, texW, texH);

		setTouchable(Touchable.enabled);
		addListener(clickListener = new ClickListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				centerX = MathUtils.clamp(x / (rangeLength * texW), keyboardWidth * 0.5f, 1.0f - keyboardWidth * 0.5f);
				resizeKeyboardTable();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				centerX = MathUtils.clamp(x / (rangeLength * texW), keyboardWidth * 0.5f, 1.0f - keyboardWidth * 0.5f);
				resizeKeyboardTable();
			}

		});

		Vector2 offsetAndRange = kt.getOctaveOffsetAndRange();
		rangeOffset = offsetAndRange.x;
		rangeLength = offsetAndRange.y;



		maskArea = new Actor() {
			Texture texture = tex_navigator;
			@Override
			public void draw (Batch batch, float parentAlpha) {
				batch.draw(texture, getX(), getY(), getWidth(), getHeight());
			}
		};
		maskArea.setTouchable(Touchable.disabled);

		addActor(maskArea);

		//debug();
		//maskArea.debug();
	}



	//which part of the whole range should be shown on the KeyboardTable
	// this is in the range of 0-1 as a proportion of the whole range available
	float centerX = 0.5f;

	public void resize(float x, float y, float width, float height) {
		setBounds(x, y, rangeLength * texW, texH);
		setScale(width / (texW * rangeLength), height / texH);
	}

	float ktX, ktY, ktW, ktH;
	float keyboardWidth = 0.2f;
	public void resizeKeyboardTable() {
		resizeKeyboardTable(ktX, ktY, ktW, ktH);
	}
	public void resizeKeyboardTable(float x, float y, float w, float h) {
		ktX = x;
		ktY = y;
		ktW = w;
		ktH = h;

		centerX = MathUtils.clamp(centerX, keyboardWidth * 0.5f, 1.0f - keyboardWidth * 0.5f);

		kt.resize(x, y, w, h, centerX, keyboardWidth * kt.getKeyboardWidth());

		float mw = rangeLength * texW * keyboardWidth;

		maskArea.setBounds(rangeLength * centerX * texW - mw * 0.5f, 0, mw, texH);
		//maskArea.debug();

	}


	@Override
	public void draw (Batch batch, float parentAlpha) {
		if (isTransform()) applyTransform(batch, computeTransform());


		int wholeOffset = 0;
//		if(rangeOffset > 0.0f) {
//
//		}
		wholeOffset = (int)((1.0f - rangeOffset) * texW);
		batch.draw(octave.getTexture(), 0, 0, (int)(rangeOffset * texW), 0, wholeOffset * texW, texH);

		int whole = (int) (rangeLength - (1.0f - rangeOffset));
		for(int i = 0; i < whole; i++) {
			batch.draw(octave, wholeOffset + i * texW, 0);
		}
		float lastLength = rangeLength - (1.0f - rangeOffset) - whole;
		float lastX = ((1.0f - rangeOffset) + whole) * texW;

		batch.draw(octave.getTexture(), lastX, 0, 0, 0, (int)(lastLength * texW), texH);


		if (isTransform()) resetTransform(batch);

		super.draw(batch, parentAlpha);
	}

}
