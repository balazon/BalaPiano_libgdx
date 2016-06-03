package com.balacraft.balapiano.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardView implements Drawable {//extends Group {

	List<PianoKey> buttons;

	Rectangle clip;


	SoundSystem ss;

	Texture tex_up;
	Texture tex_down;


	Sprite[] topSpritesUp;
	Sprite[] topSpritesDown;
	Sprite[] botSpritesUp;
	Sprite[] botSpritesDown;

	float[] topSpaces = {14.0f, 14.0f, 14.0f, 14.0f, 14.0f, 13.0f, 14.0f, 13.0f, 14.0f, 13.0f, 14.0f, 13.0f};
	//float[] botSpaces = {23.0f, 24.0f, 23.0f, 24.0f, 23.0f, 23.0f, 24.0f};
	float[] botSpaces = {23.0f, 0.f, 24.0f, 0.f, 23.0f, 24.0f, 0.f, 23.0f, 0.f, 23.0f, 0.f, 24.0f};

	public KeyboardView(SoundSystem ss, Texture tex_up, Texture tex_down) {
		this.ss = ss;
		this.tex_up = tex_up;
		this.tex_down = tex_down;
	}

	//TODO set textures for a button with a pitch
	void setTex(int pitch, Button btn) {

		int middle_c = ss.getSoundPlayer().getMiddleC();
		int relPitch = (pitch - middle_c) % 12;
		if(relPitch < 0) {
			relPitch += 12;
		}
		List<Integer> wholeTones = Arrays.asList(0, 2, 4, 5, 7, 9, 11);

		if(wholeTones.contains(relPitch)) {
			btn.setSpritesUp(topSpritesUp[relPitch], botSpritesUp[relPitch]);
			btn.setSpritesDown(topSpritesDown[relPitch], botSpritesDown[relPitch]);
		} else {
			btn.setSpritesUp(topSpritesUp[relPitch]);
			btn.setSpritesDown(topSpritesDown[relPitch]);
		}


	}

	public void init() {
		buttons = new ArrayList<PianoKey>();

		SoundPlayer sp = ss.getSoundPlayer();


		List<Integer> wholeTones = Arrays.asList(0, 2, 4, 5, 7, 9, 11);

		float h = 512.0f;
		//ratio of h for top rect
		float p = 0.6f;

		float w = 820;
		float unit = w / 164.0f;

		float th = 340.0f;
		float tx = 0;
		float tw = 0;

		float bx = 0;
		float bw = 0;

		topSpritesUp = new Sprite[12];
		topSpritesDown = new Sprite[12];
		botSpritesUp = new Sprite[12];
		botSpritesDown = new Sprite[12];

		for(int i = 0; i < 12; i++) {
			tw = topSpaces[i] * unit;
			bw = botSpaces[i] * unit;
			topSpritesUp[i] = new Sprite(tex_up, (int)tx, 0, (int)tw, (int)th);
			topSpritesDown[i] = new Sprite(tex_down, (int)tx, 0, (int)tw, (int)th);

			botSpritesUp[i] = wholeTones.contains(i) ? new Sprite(tex_up, (int) bx, (int) th, (int) bw, (int)(h - th)) : null;
			botSpritesDown[i] = wholeTones.contains(i) ? new Sprite(tex_down, (int) bx, (int) th, (int) bw, (int)(h - th)) : null;

			tx += tw;
			bx += bw;
		}

		int middle_c = ss.getSoundPlayer().getMiddleC();

		//Rectangle rtop = new Rectangle(0, 0, tw, h * p);
		//Rectangle rbot = new Rectangle(0, h * p, bw, h * (1.0f - p));
		//int j = 0;
		for(int i = sp.getRangeMin(); i <= sp.getRangeMax(); i++) {
			int relPitch = (i - middle_c) % 12;

			PianoKey pk = new PianoKey(new Note(i, 0, 1000, false), ss);
			setTex(i, pk);

			//pk.setTransform(rtop, rbot);
			buttons.add(pk);

			//rtop.x += tw;
			//rbot.x += bw;
			//j++;
		}

		s = new Sprite(tex_up, 10, 10, 500, 500);

	}

	public void resize(int width, int height) {
		//Gdx.graphics.getDensity()
		SoundPlayer sp = ss.getSoundPlayer();
		int middle_c = ss.getSoundPlayer().getMiddleC();

		float unit = 820.0f / 164.0f;

		float h = 600.0f;
		float p = 0.6f;

		Rectangle rtop = new Rectangle(0, h * (1.0f - p), 0, h * p);
		Rectangle rbot = new Rectangle(0, 0, 0, h * (1.0f - p));
		for(int i = sp.getRangeMin(); i <= sp.getRangeMax(); i++) {
			int relPitch = (i - middle_c) % 12;
			if(relPitch < 0) {
				relPitch += 12;
			}
			rtop.width = topSpaces[relPitch] * unit;
			rbot.width = botSpaces[relPitch] * unit;

			Button b = buttons.get(i - sp.getRangeMin());
			b.setTransform(rtop, rbot);


			rtop.x += rtop.width;
			rbot.x += rbot.width;

		}
		s.setBounds(500, 200, 100, 100);
	}


	Sprite s;

	@Override
	public void draw(SpriteBatch batch) {

		Group g;
		//TODO investigate this function
		g.clipBegin()
		s.
		//s.draw(batch);
		for(Button b : buttons) {
			//batch.draw();
			b.draw(batch);
		}

	}
}
