package com.balacraft.balapiano.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.balacraft.balapiano.soundengine.SoundSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KeyboardTable extends Table implements Disposable{
	ClickListener clickListener;

	List<ButtonActor> buttons;

	Table buttonParent;

	SoundSystem ss;

	int channel;

	Texture tex_up;
	Texture tex_down;

	//unscaled, unclipped
	public float getKeyboardWidth() {
		return buttons.get(buttons.size() - 1).getRight();
	}
	//unscaled, unclipped
	public float getKeyboardHeight() {
		return buttons.get(0).getHeight();
	}

	public KeyboardTable(int channel, SoundSystem ss) {
		this.channel = channel;
		this.ss = ss;

		setClip(true);
		//setTransform(true);
	}

	public void init() {

		buttonParent = new Table();
		buttonParent.debug();
		buttonParent.setTransform(true);
		addActor(buttonParent);

		buttonParent.setTouchable(Touchable.enabled);
		buttonParent.addListener(clickListener = new ClickListener() {
			public float[] x1 = new float[6];
			public float[] y1 = new float[6];

			Vector2 temp = new Vector2(0,0);
			Vector2 temp2 = new Vector2(0,0);

			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.printf("KT td: (%.2f %.2f) %d %d\n", x, y, pointer, button);
				x1[pointer] = x;
				y1[pointer] = y;

				for(ButtonActor b : buttons) {
					b.parentToLocalCoordinates(temp.set(x, y));
					if(b.contains(temp.x, temp.y)) {
						System.out.printf("%s enter (%.2f %.2f) %d\n", b.toString(), temp.x, temp.y, pointer);
						b.enter();
						break;
					}
				}

				return true;
			}


			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.printf("KT tu: (%.2f %.2f) %d %d\n", x, y, pointer, button);

				for(ButtonActor b : buttons) {
					b.parentToLocalCoordinates(temp.set(x, y));
					if(b.contains(temp.x, temp.y)) {
						System.out.printf("%s exit (%.2f %.2f) %d\n", b.toString(), temp.x, temp.y, pointer);
						b.exit();
						break;
					}
				}
			}

			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				System.out.printf("KT drag: (%.2f %.2f) -> (%.2f %.2f)) %d\n", x1[pointer], y1[pointer], x, y, pointer);

				//TODO commenting these lines make the stuck keys go away, but they introduce another thing: you can slide (and play) on keys outside the rectangle region
//				if(!isOver(event.getListenerActor(), x, y)) {
//					return;
//				}
				for(ButtonActor b : buttons) {
					b.parentToLocalCoordinates(temp.set(x1[pointer], y1[pointer]));
					b.parentToLocalCoordinates(temp2.set(x, y));
					b.draggedFromTo(temp.x, temp.y, temp2.x, temp2.y, event, pointer);
				}
				x1[pointer] = x;
				y1[pointer] = y;
			}
		});

		buttons = new ArrayList<ButtonActor>();

		loadPianoButtons();
	}

	protected void loadPianoButtons() {
		Map<String, Sprite> spritesUp = new HashMap<String, Sprite>(20);
		Map<String, Sprite> spritesDown = new HashMap<String, Sprite>(20);

		FileHandle file = Gdx.files.internal("pianobuttons.txt");
		if(!file.exists() || file.isDirectory()) {
			System.err.printf("Error : %s not found\n", file.name());
			return;
		}
		String fileString = file.readString();
		String[] rows = fileString.split("\\r?\\n");
		final int STATE_DEFAULT = 0;
		final int STATE_BUTTON = 1;
		int state = STATE_DEFAULT;

		PianoKey pk = null;
		List<String> tempSpriteNames = new ArrayList<String>(2);
		List<Rectangle> tempTransforms = new ArrayList<Rectangle>(2);

		float xoff = 0.0f;
		int pitchoff = 0;

		boolean firstNote = false;
		float firstOff = 0.0f;
		for(String row : rows) {
			if(row.equals("") && state == STATE_BUTTON) {
				if(firstNote) {
					firstNote = false;
					Rectangle b = new Rectangle(tempTransforms.get(0));
					for(int i = 1; i < tempTransforms.size(); i++) {
						b.merge(tempTransforms.get(i));
					}
					firstOff = -b.x;
					for(int i = 0; i < tempTransforms.size(); i++) {
						tempTransforms.get(i).x += firstOff;
					}
				}
				Sprite[] up = new Sprite[tempSpriteNames.size()];
				Sprite[] down = new Sprite[tempSpriteNames.size()];

				Rectangle[] rects = new Rectangle[tempTransforms.size()];
				tempTransforms.toArray(rects);
				for(int i = 0; i < tempSpriteNames.size(); i++) {
					up[i] = spritesUp.get(tempSpriteNames.get(i));
					down[i] = spritesDown.get(tempSpriteNames.get(i));
				}
				pk.setSpritesUp(up);
				pk.setSpritesDown(down);
				pk.setSpriteGlobalTransform(rects);

				buttons.add(pk);
				buttonParent.addActor(pk);

				state = STATE_DEFAULT;
				continue;
			}
			if(row.startsWith("//") || row.equals("")) {
				continue;
			}
			if(row.matches("tex_up\\s*:.*")) {
				String path = row.split("\\s*:\\s*")[1];
				tex_up = new Texture(Gdx.files.internal(path));
				continue;
			}
			if(row.matches("tex_down\\s*:.*")) {
				String path = row.split("\\s*:\\s*")[1];
				tex_down = new Texture(Gdx.files.internal(path));
				continue;
			}

			if(row.matches("s\\s*:.*")) {
				String[] spriteData = row.split(":\\s*|\\s*-\\s*|\\s*,\\s*");

				Sprite s_up = new Sprite(tex_up, Integer.parseInt(spriteData[2]), Integer.parseInt(spriteData[3]), Integer.parseInt(spriteData[4]), Integer.parseInt(spriteData[5]));
				Sprite s_down = new Sprite(tex_down, Integer.parseInt(spriteData[2]), Integer.parseInt(spriteData[3]), Integer.parseInt(spriteData[4]), Integer.parseInt(spriteData[5]));
				spritesUp.put(spriteData[1], s_up);
				spritesDown.put(spriteData[1], s_down);
				continue;
			}
			if(row.matches("[0-9]+\\s*:")) {
				int pitch = Integer.parseInt(row.split("\\s*:")[0]) + pitchoff;
				if(pitch < ss.getSoundPlayer().getRangeMin() || pitch > ss.getSoundPlayer().getRangeMax()) {
					continue;
				}
				firstNote = (pitch == ss.getSoundPlayer().getRangeMin());
				state = STATE_BUTTON;
				pk = new PianoKey(pitch, channel, ss);
				tempSpriteNames.clear();
				tempTransforms.clear();
				continue;
			}
			if(state == STATE_BUTTON && row.matches(".*,.*,.*,.*,.*")) {
				String[] buttonData = row.split("\\s*,\\s*");
				tempSpriteNames.add(buttonData[0]);
				float x = Float.parseFloat(buttonData[1]) + xoff + firstOff;
				float y = Float.parseFloat(buttonData[2]);
				float w = Float.parseFloat(buttonData[3]);
				float h = Float.parseFloat(buttonData[4]);

				tempTransforms.add(new Rectangle(x, y, w, h));
				continue;
			}
			if(row.matches("xoff\\s*:.*")) {
				xoff = Float.parseFloat(row.split("\\s*:\\s*")[1]);
				continue;
			}
			if(row.matches("pitchoff\\s*:.*")) {
				pitchoff = Integer.parseInt(row.split("\\s*:\\s*")[1]);
				continue;
			}

		}
	}

	//must be called after init
	//1.0f meaning a whole octave :
	// 0.5f offset and 4.2f range means the range starts at half an octave from C (display length): this is about F#'s left side
	// and the whole displayable range is 4.2 octave's length
	public Vector2 getOctaveOffsetAndRange() {
		float octaveLength = 164.0f;
		int min = ss.getSoundPlayer().getRangeMin();
		int middle_c = ss.getSoundPlayer().getMiddleC();
		//mod 12 and not negative
		int minPitchOffset = ((min - middle_c) % 12 + 12) % 12;

		float rangeOffset = 0;
		if(min % 12 != 0) {
			rangeOffset = octaveLength - buttons.get((11 - minPitchOffset) % 12).getRight();
		}

		rangeOffset /= octaveLength;
		float range = (buttons.get(buttons.size() - 1).getRight() - buttons.get(0).getX()) / octaveLength;
		return new Vector2(rangeOffset, range);
	}


	public void resize(float x, float y, float width, float height, float centerX, float keyboardWidth) {
		debug();

		setBounds(x, y, width, height);
		float scaleX = width / keyboardWidth;
		float scaleY = height / getKeyboardHeight();
		buttonParent.setBounds(width * 0.5f - centerX * getKeyboardWidth() * scaleX, height * 0.5f - getKeyboardHeight() * 0.5f * scaleY, getKeyboardWidth(), getKeyboardHeight());
		buttonParent.setScale(scaleX, scaleY);
	}

	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}


	@Override
	public void dispose() {
		tex_up.dispose();
		tex_down.dispose();
	}
}

