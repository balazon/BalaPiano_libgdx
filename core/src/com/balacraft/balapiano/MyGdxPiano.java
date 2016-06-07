package com.balacraft.balapiano;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.balacraft.balapiano.soundengine.SoundSystem;
import com.balacraft.balapiano.soundengine.Time;
import com.balacraft.balapiano.view.ButtonActor;
import com.balacraft.balapiano.view.KeyboardNavigator;
import com.balacraft.balapiano.view.KeyboardTable;


public class MyGdxPiano extends ApplicationAdapter {

	private SoundSystem ss;

	int w;
	int h;
	private Texture tex1;
	private Texture tex2;

	private Stage stage;

	KeyboardTable kt;
	KeyboardNavigator kn;

	@Override
	public void create() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		//camera = new OrthographicCamera(1, h / w);

		tex1 = new Texture(Gdx.files.internal("data/tex_unpressed.png"));
		tex2 = new Texture(Gdx.files.internal("data/tex_pressed.png"));
		tex1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tex2.setFilter(TextureFilter.Linear, TextureFilter.Linear);



		stage = new Stage(new ScreenViewport());

		ss = new SoundSystem();
		ss.initSoundPlayer();

		kt = new KeyboardTable(ss, tex1, tex2);

		stage.addActor(kt);

		kt.init();



		kn = new KeyboardNavigator(kt , ss, tex1);
		stage.addActor(kn);

		kn.init();
		kn.resize(0, 0, 800.0f, h * 0.15f);
		//kn.resizeKeyboardTable(w, h);


		Texture tex = new Texture(Gdx.files.internal("badlogic.jpg"));
		Sprite s = new Sprite(tex);
		//s.setBounds(0, 0, 100, 100);
		//s.setBounds(100,100,100,100);
		ButtonActor test = new ButtonActor();

		test.setSpritesUp(s, s);
		test.setSpritesDown(s, s);


		test.setSpriteLocalTransform(new Rectangle(0, 100, 100, 100), new Rectangle(100, 0, 100, 100));


		test.setBounds(100, 0, 200, 200);
		test.setPosition(100, 0);
		test.setScale(1.4f, 1.4f);
		test.setRotation(00.0f);

		test.setDebug(true);


		//test.setTransform(new Rectangle(100, 100, 100, 100));
		//stage.addActor(test);



		InputMultiplexer impx = new InputMultiplexer();
		impx.addProcessor(new GestureDetector(new MyGestureListener()));
		impx.addProcessor(stage);
		impx.addProcessor(new MyInputProcessor());

		Gdx.input.setInputProcessor(impx);


		//instance = this;

	}

	@Override
	public void dispose() {
		//batch.dispose();
		ss.dispose();

		stage.dispose();

		tex1.dispose();
		tex2.dispose();
	}

	@Override
	public void render() {
		Time.update();
		ss.process();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Time.delta());
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;

		kn.resizeKeyboardTable(w, h);

		kn.resize(w * 0.45f - w * 0.35f, h * 0.85f, w * 0.7f, h * 0.15f);
		//kn.resize(0, 0, 800.0f, h * 0.15f);

		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		Time.paused(true);
	}

	@Override
	public void resume() {
		Time.paused(false);
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
