package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KeyboardTable extends Table {

	List<PianoKey> buttons;

	Rectangle clip;

	Table buttonParent;

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

	public KeyboardTable(SoundSystem ss, Texture tex_up, Texture tex_down) {
		this.ss = ss;
		this.tex_up = tex_up;
		this.tex_down = tex_down;

		setClip(true);
	}

	//TODO set textures for a button with a pitch
	void setTex(int pitch, ButtonActor btn) {

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

		//source height
		float sh = 512.0f;
		//ratio of sh for top rect
		float p = 0.6f;

		//source width
		float sw = 820;
		float unit = sw / 164.0f;

		//top height, x, width
		float th = 340.0f;
		float tx = 0;
		float tw = 0;

		//bottom x, width
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

			botSpritesUp[i] = wholeTones.contains(i) ? new Sprite(tex_up, (int) bx, (int) th, (int) bw, (int)(sh - th)) : null;
			botSpritesDown[i] = wholeTones.contains(i) ? new Sprite(tex_down, (int) bx, (int) th, (int) bw, (int)(sh - th)) : null;


			tx += tw;
			bx += bw;
		}

		buttonParent = new Table();
		buttonParent.setClip(true);
		addActor(buttonParent);

		int middle_c = sp.getMiddleC();
		float kh = 100;
		float kw = 0;
		float ph = 0.6f;
		unit = 1.0f;
		//absolute positions
		Rectangle rtop = new Rectangle(0, kh * (1.0f - ph), 0, kh * ph);
		Rectangle rbot = new Rectangle(0, 0, 0, kh * (1.0f - ph));
		for(int i = sp.getRangeMin(); i <= sp.getRangeMax(); i++) {
			PianoKey pk = new PianoKey(new Note(i, 0, 1000, false), ss);
			setTex(i, pk);

			buttons.add(pk);
			buttonParent.addActor(pk);



			int relPitch = (i - middle_c) % 12;
			if(relPitch < 0) {
				relPitch += 12;
			}
			pk.setName("PianoKey " + i);
			rtop.width = topSpaces[relPitch] * unit;
			rbot.width = botSpaces[relPitch] * unit;

			Rectangle bounds = new Rectangle(rtop);
			if(wholeTones.contains(relPitch)) {
				bounds.merge(rbot);
			}
			rtop.x -= bounds.x;
			rtop.y -= bounds.y;
			rbot.x -= bounds.x;
			rbot.y -= bounds.y;

			if(wholeTones.contains(relPitch)) {
				pk.setSpriteLocalTransform(rtop, rbot);
			} else {
				pk.setSpriteLocalTransform(rtop);
			}

			pk.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

			rtop.x += bounds.x;
			rtop.y += bounds.y;
			rbot.x += bounds.x;
			rbot.y += bounds.y;
			kw = bounds.x + bounds.width;
			rtop.x += rtop.width;
			rbot.x += rbot.width;
		}



		//setSpriteLocalTransform


		//s = new Sprite(tex_up, 0, 0, 820, 512);




	}

	public void setMaskBounds(Rectangle maskBounds) {
		setBounds(0, 0, maskBounds.width, maskBounds.height);
		buttonParent.setBounds(-maskBounds.x, -maskBounds.y, 1012,100);
		//pad(0,5,0,0);
		//pad(0, 23, 0, 0);
		buttonParent.pad(100 - maskBounds.y - maskBounds.height, maskBounds.x, maskBounds.y, 1012 - maskBounds.x - maskBounds.width);


	}

	public void resize(float x, float y, float width, float height, float centerX, float keyboardWidth) {
		//setBounds(0,0,1012, 100);
		setBounds(x, y, width, height);
		//setClip(true);
		setScale(width / keyboardWidth, height / 100.0f);


//		float maskX = 23;
//		float maskY = 0;
//		float maskW = 1012.0f - 23 - 28;
//		float maskH = 100.0f;

		float maskX = centerX - keyboardWidth * 0.5f;
		float maskY = 0;
		float maskW = keyboardWidth;
		float maskH = 100.0f;


		buttonParent.setBounds(-maskX, -maskY, maskW + maskX,100);
		buttonParent.pad(100 - maskY - maskH, maskX, maskY, 0);

	}


	Sprite s;



	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
