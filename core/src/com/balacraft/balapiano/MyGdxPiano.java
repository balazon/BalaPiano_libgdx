package com.balacraft.balapiano;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;



import com.balacraft.balapiano.soundengine.SoundSystem;
import com.balacraft.balapiano.soundengine.Time;
import com.balacraft.balapiano.view.ButtonContainer;

public class MyGdxPiano extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private com.balacraft.balapiano.soundengine.SoundSystem ss;
	private com.balacraft.balapiano.view.ButtonContainer bc;
	int w;
	int h;
	private Texture tex1;
	private Texture tex2;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();

		tex1 = new Texture(Gdx.files.internal("data/tex01ps.png"));
		tex2 = new Texture(Gdx.files.internal("data/tex02ps.png"));
		tex1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tex2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		InputMultiplexer impx = new InputMultiplexer();
		impx.addProcessor(new GestureDetector(new MyGestureListener()));
		impx.addProcessor(new MyInputProcessor());
		Gdx.input.setInputProcessor(impx);

		ss = new SoundSystem();
		ss.initSoundPlayer();


		bc = new ButtonContainer(ss, tex1, tex2, 798, 504);
		bc.setDimension(this.w, this.h);

	}

	@Override
	public void dispose() {
		batch.dispose();
		ss.dispose();
		tex1.dispose();
		tex2.dispose();
	}

	@Override
	public void render() {
		Time.update();
		ss.process();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.projection.translate(-w / 2.0f, -h / 2.0f, 0));
		camera.update();
		batch.disableBlending();
		batch.begin();
		bc.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		camera.setToOrtho(true, w, h);
		bc.setDimension(w, h);
	}

	@Override
	public void pause() {
		Time.paused(true);
	}

	@Override
	public void resume() {
		Time.paused(false);
	}


	int[] x1 = new int[6];
	int[] y1 = new int[6];



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
			//System.out.println("touchdown x "+x+"   y "+(h-y));
            System.out.println("press");
            bc.pressed(x2, y2);
			x1[pointer] = x2;
			y1[pointer] = y2;
			return false;
		}

		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {
			bc.released(x, h - y);
			return false;
		}

		@Override
		public boolean touchDragged(int x, int y, int pointer) {
			int x2 = x;
			int y2 = h - y;
			//System.out.println("drag x "+x2+"   y "+y2);
			bc.draggedFromTo(x1[pointer], y1[pointer], x2, y2);
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
