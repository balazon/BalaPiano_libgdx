package hu.balayon.pianogame.game;

import hu.balayon.pianogame.soundengine.SoundSystem;
import hu.balayon.pianogame.view.ButtonContainer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGamePiano implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SoundSystem ss;
	private ButtonContainer bc;
	int w;
	int h;
	private Texture tex2;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		tex1 = new Texture(Gdx.files.internal("data/tex01ps.png"));
		tex2 = new Texture(Gdx.files.internal("data/tex02ps.png"));
		tex1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tex2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		InputMultiplexer impx= new InputMultiplexer();
		impx.addProcessor(new GestureDetector(new MyGestureListener()));
		impx.addProcessor(new MyInputProcessor());
		Gdx.input.setInputProcessor(impx);
		
		ss = new SoundSystem();
		String[] C = {"data/c1.mp3", "data/c2.mp3","data/c3.mp3","data/c4.mp3","data/c5.mp3","data/c6.mp3","data/c7.mp3", "data/c8.mp3"};
		ss.setSoundPlayer(C);
		
		bc = new ButtonContainer(ss, tex1,tex2, 798,504);
		bc.setDimension(this.w, this.h);
		ss.start();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		ss.dispose();
		tex1.dispose();
		tex2.dispose();
	}

	private Texture tex1;

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.projection.translate(-w/2.0f,-h/2.0f,0));
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
		camera.setToOrtho(true, w,h);
		bc.setDimension(w,h);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	
	
	int[] x1 = new int[6];
	int[] y1 = new int[6];
	private class MyInputProcessor implements InputProcessor{
		
		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean touchDown(int x, int y, int pointer, int button) {
			int x2 = x; int y2 = h-y;
			//System.out.println("touchdown x "+x+"   y "+(h-y));
			bc.pressed(x2,y2);
			x1[pointer] = x2; y1[pointer] = y2;
			return false;
		}

		public boolean touchUp(int x, int y, int pointer, int button) {
			bc.released(x, h-y);
			return false;
		}

		public boolean touchDragged(int x, int y, int pointer) {
			int x2= x; int y2 = h-y;
			//System.out.println("drag x "+x2+"   y "+y2);
			bc.draggedFromTo(x1[pointer],y1[pointer],x2,y2);
			x1[pointer] = x2; y1[pointer] = y2;
			return false;
		}

		public boolean touchMoved(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

	}
	
	private class MyGestureListener implements GestureListener {

		@Override
		public boolean touchDown(int x, int y, int pointer) {
			return false;
		}

		@Override
		public boolean tap(int x, int y, int count) {
			return false;
		}

		@Override
		public boolean longPress(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		@Override
		public boolean fling(float velocityX, float velocityY) {
			return false;
		}
		//debug this!
		@Override
		public boolean pan(int x, int y, int deltaX, int deltaY) {
			return false;
		}

		@Override
		public boolean zoom(float originalDistance, float currentDistance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialFirstPointer,
				Vector2 initialSecondPointer, Vector2 firstPointer,
				Vector2 secondPointer) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
