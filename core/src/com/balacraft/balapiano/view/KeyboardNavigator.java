package com.balacraft.balapiano.view;


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
				return false;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
			public void touchDragged (InputEvent event, float x, float y, int pointer) {

			}
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {

			}
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {

			}
		});

		rangeOffset = 0.3f;
		rangeLength = 6.0f;
	}

	public void resize(float x, float y, float width, float height) {
		setBounds(x, y, width, height);
		setScale(width / (820.0f * rangeLength), height / 512.0f);
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
