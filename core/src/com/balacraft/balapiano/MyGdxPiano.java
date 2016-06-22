package com.balacraft.balapiano;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.balacraft.balapiano.soundengine.ChordPlayer;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;
import com.balacraft.balapiano.soundengine.Time;
import com.balacraft.balapiano.view.ChordPitchButton;
import com.balacraft.balapiano.view.ChordVariationButton;
import com.balacraft.balapiano.view.KeyboardRow;
import com.balacraft.balapiano.view.PlusMinusControl;


public class MyGdxPiano extends ApplicationAdapter {

	private SoundSystem ss;

	int w;
	int h;

	Texture texkeys_up;
	Texture texkeys_down;

	Texture texmod_up;
	Texture texmod_down;

	Texture tex_navigator;

	Texture texchord_up;
	Texture texchord_down;

	private Stage stage;


	Group chordParent;
	Group variationParent;

	PlusMinusControl bpmControl;
	PlusMinusControl rowsControl;

	int minRowCount = 1;
	int maxRowCount = 3;
	int defaultRowCount = 1;
	Group rowsParent;

	SoundPlayer sp;

	Logger logger;

	public MyGdxPiano(SoundPlayer sp) {
		this.sp = sp;
	}

	@Override
	public void create() {
		logger = new Logger("BALA_PIANO");

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		texkeys_up = new Texture(Gdx.files.internal("data/tex_unpressed.png"));
		texkeys_down = new Texture(Gdx.files.internal("data/tex_pressed.png"));

		texmod_up = new Texture(Gdx.files.internal("data/modifiers_unpressed.png"));
		texmod_down = new Texture(Gdx.files.internal("data/modifiers_pressed.png"));
		texmod_up.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texmod_down.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		tex_navigator = new Texture(Gdx.files.internal("navigator.png"));

		texchord_up = new Texture(Gdx.files.internal("data/chordbuttons_unpressed.png"));
		texchord_down = new Texture(Gdx.files.internal("data/chordbuttons_pressed.png"));



		stage = new Stage(new ScreenViewport());

		//logger.error("GDX_CORE INIT");
		sp.init();
		ss = new SoundSystem(sp);

		rowsParent = new Group();

		stage.addActor(rowsParent);
		rowsParent.setSize(1000.0f, 0.0f);
		for(int i = 0; i < defaultRowCount; i++) {
			addKeyboardRow();
		}
		//rowsParent.setSize(1000.0f, 400.0f * defaultRowCount);



		loadHud();

		InputMultiplexer impx = new InputMultiplexer();
		impx.addProcessor(stage);
		Gdx.input.setInputProcessor(impx);
	}

	void addKeyboardRow() {
		int count = rowsParent.getChildren().size;
		if(count == maxRowCount) {
			return;
		}
		int channel = count;
		KeyboardRow kr = new KeyboardRow(channel, ss, texkeys_up, texkeys_down, texmod_up, texmod_down, tex_navigator);
		kr.setPosition(0, channel * 400.0f);
		rowsParent.addActor(kr);
		rowsParent.sizeBy(0, 400);
		kr.init();

		rowsParent.setPosition(0, h * 0.15f);
		rowsParent.setScale(w * 0.9f / 1000.0f, h * 0.85f / (400.0f * rowsParent.getChildren().size));
	}

	void removeKeyboardRow() {
		int count = rowsParent.getChildren().size;
		if(count == minRowCount) {
			return;
		}
		KeyboardRow kr = (KeyboardRow) rowsParent.getChildren().get(count - 1);
		kr.clear();
		kr.remove();
		rowsParent.sizeBy(0, -400);

		rowsParent.setPosition(0, h * 0.15f);
		rowsParent.setScale(w * 0.9f / 1000.0f, h * 0.85f / (400.0f * rowsParent.getChildren().size));
	}

	void loadHud() {


		bpmControl = new PlusMinusControl(texmod_up, texmod_down, 0, 128, 200, 128, true) {
			@Override
			public void minusFire() {
				ss.getChordPlayer().setBPMRelative(-1);
			}

			@Override
			public void plusFire() {
				ss.getChordPlayer().setBPMRelative(1);
			}
		};

		stage.addActor(bpmControl);
		bpmControl.init();

		rowsControl = new PlusMinusControl(texmod_up, texmod_down, 400, 128, 200, 128, true) {
			@Override
			public void minusFire() {
				removeKeyboardRow();
			}

			@Override
			public void plusFire() {
				addKeyboardRow();
			}
		};
		stage.addActor(rowsControl);
		rowsControl.init();

		variationParent = new Group();

		stage.addActor(variationParent);
		ChordPlayer.ChordVariationType[] chordvarTypes = ChordPlayer.ChordVariationType.values();
		for(int i = 0; i < chordvarTypes.length; i++) {
			ChordVariationButton cvb = new ChordVariationButton(chordvarTypes[i], ss);
			//cvb.debug();

			Sprite var_up = new Sprite(texmod_up, i * 200, 0, 200, 128);
			Sprite var_down = new Sprite(texmod_down, i * 200, 0, 200, 128);

			cvb.setSpritesUp(var_up);
			cvb.setSpritesDown(var_down);

			Rectangle pos = new Rectangle(0, (chordvarTypes.length - i - 1) * 128.0f, 200.0f, 128.0f);
			cvb.setSpriteGlobalTransform(pos);

			variationParent.addActor(cvb);

		}

		variationParent.setBounds(w * 0.9f, h * 0.15f, 200.0f, ChordPlayer.ChordVariationType.values().length * 128.0f);
		variationParent.setScale(w * 0.1f / 200.0f, h * 0.7f / (ChordPlayer.ChordVariationType.values().length * 128.0f));
		//variationParent.debug();

		chordParent = new Group();
		chordParent.setBounds(0, 0, 1680.0f, 200.0f);
		chordParent.setScale(w * 0.9f / 1680.0f, h * 0.15f / 200.0f);
		stage.addActor(chordParent);

		//chordParent.debug();
		for(int i = 0; i < 12; i++) {
			ChordPitchButton cpb = new ChordPitchButton(i, ss);
			//cpb.debug();

			Sprite up = new Sprite(texchord_up, i * 140, 0, 140, 200);
			Sprite down = new Sprite(texchord_down, i * 140, 0, 140, 200);

			cpb.setSpritesUp(up);
			cpb.setSpritesDown(down);

			Rectangle pos = new Rectangle(i * 140.0f, 0, 140.0f, 200.0f);
			cpb.setSpriteGlobalTransform(pos);

			chordParent.addActor(cpb);
		}


	}

	@Override
	public void render() {
		Time.update();

		ss.getChordPlayer().process();

		stage.act(Time.delta());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;



		rowsParent.setPosition(0, h * 0.15f);
		rowsParent.setScale(w * 0.9f / 1000.0f, h * 0.85f / (400.0f * rowsParent.getChildren().size));
//		for(int i = 0; i < keyboardRows.size(); i++) {
//			KeyboardRow row = keyboardRows.get(i);
//			row.resize(0, h * 0.15f + i / (float)keyboardRows.size() * h * 0.85f , w * 0.9f, h * 0.85f / (float) keyboardRows.size());
//		}

		chordParent.setScale(w * 0.9f / 1680.0f, h * 0.15f / 200.0f);

		bpmControl.setPosition(w * 0.9f, 0);
		bpmControl.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);

		rowsControl.setPosition(w * 0.9f, h * 0.85f);
		rowsControl.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);

		variationParent.setPosition(w * 0.9f, h * 0.15f);
		variationParent.setScale(w * 0.1f / 200.0f, h * 0.7f / (ChordPlayer.ChordVariationType.values().length * 128.0f));
		//variationParent.debug();

		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		Time.paused(true);
		//logger.error("GDX_CORE PAUSE");
	}

	@Override
	public void resume() {
		Time.paused(false);
		//logger.error("GDX_CORE RESUME");
	}

	@Override
	public void dispose() {
		//logger.error("GDX_CORE DISPOSE");

		sp.dispose();

		stage.dispose();

		texkeys_up.dispose();
		texkeys_down.dispose();

		texmod_up.dispose();
		texmod_down.dispose();

		tex_navigator.dispose();

		texchord_up.dispose();
		texchord_down.dispose();
	}
}
