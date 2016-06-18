package com.balacraft.balapiano;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.balacraft.balapiano.soundengine.ChordPlayer;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;
import com.balacraft.balapiano.soundengine.Time;
import com.balacraft.balapiano.view.ButtonActor;
import com.balacraft.balapiano.view.ChordPitchButton;
import com.balacraft.balapiano.view.ChordVariationButton;
import com.balacraft.balapiano.view.KeyboardNavigator;
import com.balacraft.balapiano.view.KeyboardTable;
import com.balacraft.balapiano.view.RangeControl;


public class MyGdxPiano extends ApplicationAdapter {

	private SoundSystem ss;

	int w;
	int h;
	private Texture tex1;
	private Texture tex2;

	private Stage stage;

	KeyboardTable kt;
	KeyboardNavigator kn;
	Group chordParent;
	Group variationParent;


	RangeControl rangeControl;
	Group bpmParent;

	Group rowsParent;


	SoundPlayer sp;

	Logger logger;

	public MyGdxPiano(SoundPlayer sp) {
		this.sp = sp;
	}

	@Override
	public void create() {
		logger = new Logger("GDX_PIANO");

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		//camera = new OrthographicCamera(1, h / w);

		tex1 = new Texture(Gdx.files.internal("data/tex_unpressed.png"));
		tex2 = new Texture(Gdx.files.internal("data/tex_pressed.png"));
		tex1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tex2.setFilter(TextureFilter.Linear, TextureFilter.Linear);



		stage = new Stage(new ScreenViewport());


		logger.error("GDX_CORE INIT");
		sp.init();
		ss = new SoundSystem(sp);

		kt = new KeyboardTable(0, ss);

		stage.addActor(kt);

		kt.init();



		kn = new KeyboardNavigator(kt , ss, tex1);
		stage.addActor(kn);

		kn.init();
		kn.resize(0, 0, 800.0f, h * 0.15f);
		//kn.resizeKeyboardTable(w, h);





		Table tp = new Table();
		tp.setClip(true);
		tp.setBounds(0, 0, 500, 500);
		tp.setScale(0.5f, 0.5f);
		tp.setRotation(0.0f);
		//stage.addActor(tp);
		tp.debug();

		Texture tex = new Texture(Gdx.files.internal("badlogic.jpg"));
		Sprite s = new Sprite(tex);
		//s.setBounds(0, 0, 100, 100);
		//s.setBounds(100,100,100,100);
		ButtonActor test = new ButtonActor();

		test.setSpritesUp(s, s);
		test.setSpritesDown(s, s);


		test.setSpriteLocalTransform(new Rectangle(0, 100, 100, 100), new Rectangle(100, 0, 100, 100));


		test.setBounds(250, 0, 200, 200);

		test.setScale(1.4f, 1.4f);
		test.setRotation(00.0f);

		test.debug();


		//test.setTransform(new Rectangle(100, 100, 100, 100));
		tp.addActor(test);

		loadHud();

		InputMultiplexer impx = new InputMultiplexer();
		impx.addProcessor(new GestureDetector(new MyGestureListener()));
		impx.addProcessor(stage);
		impx.addProcessor(new MyInputProcessor());

		Gdx.input.setInputProcessor(impx);


		//instance = this;

	}

	void loadHud() {
		Texture texmod_up = new Texture(Gdx.files.internal("data/modifiers_unpressed.png"));
		Texture texmod_down = new Texture(Gdx.files.internal("data/modifiers_pressed.png"));
		Sprite bpmSprite = new Sprite(texmod_up, 0, 128, 200, 64);


		rangeControl = new RangeControl(kn);
		stage.addActor(rangeControl);
		rangeControl.init();
		rangeControl.setPosition(w * 0.8f, h * 0.85f);
		rangeControl.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);


		Sprite rowsSprite = new Sprite(texmod_up, 400, 128, 200, 64);
		Image rowsLabel = new Image(rowsSprite);


		rowsLabel.setBounds(w * 0.9f, h * 0.925f, 200, 64);
		rowsLabel.setScale(w * 0.1f / 200.0f, h * 0.075f / 64.0f);

//		stage.addActor(rowsLabel);

		Sprite bpmminus_up = new Sprite(texmod_up, 0, 192, 100, 64);
		Sprite bpmminus_down = new Sprite(texmod_down, 0, 192, 100, 64);
		Sprite bpmplus_up = new Sprite(texmod_up, 100, 192, 100, 64);
		Sprite bpmplus_down = new Sprite(texmod_down, 100, 192, 100, 64);
		Sprite rowsminus_up = new Sprite(texmod_up, 400, 192, 100, 64);
		Sprite rowsminus_down = new Sprite(texmod_down, 400, 192, 100, 64);
		Sprite rowsplus_up = new Sprite(texmod_up, 500, 192, 100, 64);
		Sprite rowsplus_down = new Sprite(texmod_down, 500, 192, 100, 64);

		bpmParent = new Group();
		bpmParent.setBounds(w * 0.9f, 0, 200.0f, 128.0f);
		bpmParent.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);
		stage.addActor(bpmParent);

		Image bpmLabel = new Image(bpmSprite);
		bpmLabel.setBounds(0, 64.0f, 200.0f, 64.0f);

		ButtonActor bpmminus = new ButtonActor() {
			@Override
			public void fire() {
				ss.getChordPlayer().setBPMRelative(-1);
			}
		};
		bpmminus.setSpritesUp(bpmminus_up);
		bpmminus.setSpritesDown(bpmminus_down);
		bpmminus.setSpriteGlobalTransform(new Rectangle(0, 0, 100, 64));

		ButtonActor bpmplus = new ButtonActor() {
			@Override
			public void fire() {
				ss.getChordPlayer().setBPMRelative(1);
			}
		};
		bpmplus.setSpritesUp(bpmplus_up);
		bpmplus.setSpritesDown(bpmplus_down);
		bpmplus.setSpriteGlobalTransform(new Rectangle(100, 0, 100, 64));


		bpmParent.addActor(bpmLabel);
		bpmParent.addActor(bpmminus);
		bpmParent.addActor(bpmplus);




//		Group rowsParent;

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
		variationParent.debug();

		chordParent = new Group();
		chordParent.setBounds(0, 0, 1680.0f, 200.0f);
		chordParent.setScale(w * 0.9f / 1680.0f, h * 0.15f / 200.0f);
		stage.addActor(chordParent);

		chordParent.debug();
		Texture texchord_up = new Texture(Gdx.files.internal("data/chordbuttons_unpressed.png"));
		Texture texchord_down = new Texture(Gdx.files.internal("data/chordbuttons_pressed.png"));
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
	public void dispose() {
		logger.error("GDX_CORE DISPOSE");
		//batch.dispose();
		ss.dispose();
		sp.dispose();

		kt.dispose();

		stage.dispose();

		tex1.dispose();
		tex2.dispose();
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

		kn.resizeKeyboardTable(w, h);

		kn.resize(w * 0.45f - w * 0.35f, h * 0.85f, w * 0.7f, h * 0.15f);
		//kn.resize(0, 0, 800.0f, h * 0.15f);


		chordParent.setScale(w * 0.9f / 1680.0f, h * 0.15f / 200.0f);

		bpmParent.setPosition(w * 0.9f, 0);
		bpmParent.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);

		rangeControl.setPosition(w * 0.8f, h * 0.85f);
		rangeControl.setScale(w * 0.1f / 200.0f, h * 0.15f / 128.0f);

//		bpmLabel.setBounds(w * 0.9f, h * 0.075f, 200, 64);
//		bpmLabel.setScale(w * 0.1f / 200.0f, h * 0.075f / 64.0f);

//		rowsLabel.setBounds(w * 0.9f, h * 0.925f, 200, 64);
//		rowsLabel.setScale(w * 0.1f / 200.0f, h * 0.075f / 64.0f);

		variationParent.setPosition(w * 0.9f, h * 0.15f);
		variationParent.setScale(w * 0.1f / 200.0f, h * 0.7f / (ChordPlayer.ChordVariationType.values().length * 128.0f));
		//variationParent.debug();

		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		Time.paused(true);
		logger.error("GDX_CORE PAUSE");
	}

	@Override
	public void resume() {
		Time.paused(false);
		logger.error("GDX_CORE RESUME");
	}


	public int[] x1 = new int[6];
	public int[] y1 = new int[6];



	private class MyInputProcessor implements InputProcessor {


		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {
			System.out.println("click at thread  " + Thread.currentThread().getId());

			int x2 = x;
			int y2 = h - y;
            //System.out.println("press");

			x1[pointer] = x2;
			y1[pointer] = y2;
			return false;
		}

		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int x, int y, int pointer) {
			int x2 = x;
			int y2 = h - y;

			x1[pointer] = x2;
			y1[pointer] = y2;
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
	}

	private class MyGestureListener implements GestureListener {


		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
			return false;
		}
	}
}
