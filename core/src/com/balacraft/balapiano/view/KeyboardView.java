package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardView extends Group {

	List<Button> buttons;

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
		buttons = new ArrayList<Button>();

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


		for(int i = sp.getRangeMin(); i <= sp.getRangeMax(); i++) {

			PianoKey pk = new PianoKey(new Note(i, 0, 1000, false), ss);
			//setTex(i, pk);

			//buttons.add(pk);
		}

		s = new Sprite(tex_up, 0, 0, 820, 512);


	}

	public void resize(Rectangle bounds, float xmin, float xmax) {
		//Gdx.graphics.getDensity()
		SoundPlayer sp = ss.getSoundPlayer();
		int middle_c = ss.getSoundPlayer().getMiddleC();

		float unit = 820.0f / 164.0f;

		float h = 600.0f;
		float p = 0.6f;

		//NinePatch

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
		s.setBounds(0, 0, 800, 500);
		clip = new Rectangle(0, 720 - 500 + 0, 800, 500);
		//clip = new Rectangle(10, 10, 800, 500);
	}


	Sprite s;

	@Override
	public void draw (Batch batch, float parentAlpha) {
		if (isTransform()) applyTransform(batch, computeTransform());


		if (isTransform()) resetTransform(batch);

	}
}
