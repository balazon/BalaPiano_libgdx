package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KeyboardTable extends Table {
	ClickListener clickListener;

	List<PianoKey> buttons;

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
		//setTransform(true);
	}


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

		setTouchable(Touchable.enabled);
		addListener(clickListener = new ClickListener() {
			Vector2 temp = new Vector2(0,0);
			Vector2 temp2 = new Vector2(0,0);
			public float[] x1 = new float[6];
			public float[] y1 = new float[6];

			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.printf("kt td: %.2f %.2f\n", x, y);
				x1[pointer] = x;
				y1[pointer] = y;
				return true;
				//return true;
//				x1[pointer] = x;
//				y1[pointer] = y;
//				pointerPressed[pointer] = true;
//				if(contains(x, y) && !isPressed) {
//
//					isPressed = true;
//					fire();
//					return true;
//				}
//				return false;
			}

			//TODO keys stay pressed after dragpress, investigate multitouch button holding/pressing
//			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
////				pointerPressed[pointer] = false;
////				if(contains(x, y)) {
////					isPressed = false;
////				}
//
//			}

			public void touchDragged (InputEvent event, float x, float y, int pointer) {

				//System.out.println(String.format(" drag: %.2f %.2f", x, y));
				if(!isOver(event.getListenerActor(), x, y)) {
					return;
				}
				for(PianoKey p : buttons) {
					buttonParent.parentToLocalCoordinates(temp.set(x1[pointer],y1[pointer]));
					p.parentToLocalCoordinates(temp);
					buttonParent.parentToLocalCoordinates(temp2.set(x, y));
					p.parentToLocalCoordinates(temp2);
					p.draggedFromTo(temp.x, temp.y, temp2.x, temp2.y);
				}
				x1[pointer] = x;
				y1[pointer] = y;
//				draggedFromTo(x1[pointer], y1[pointer], x, y);
//
//				x1[pointer] = x;
//				y1[pointer] = y;
			}
		});

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
		buttonParent.debug();
		buttonParent.setTransform(true);

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


	public void resize(float x, float y, float width, float height, float centerX, float keyboardWidth) {
		debug();

		setBounds(x, y, width, height);
		float scaleX = width / keyboardWidth;
		float scaleY = scaleX;
		buttonParent.setBounds(width * 0.5f - centerX * 1012 * scaleX, height * 0.5f - 50 * scaleY, 1012, 100);
		buttonParent.setScale(scaleX, scaleX);
	}


	Sprite s;



	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
