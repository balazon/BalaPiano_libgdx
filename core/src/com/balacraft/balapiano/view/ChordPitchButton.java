package com.balacraft.balapiano.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.balacraft.balapiano.soundengine.SoundSystem;

public class ChordPitchButton extends ButtonActor{
	protected int pitch;
	protected SoundSystem ss;

	public ChordPitchButton(int pitch,SoundSystem ss) {
		this.ss = ss;
		this.pitch = pitch;
	}

	@Override
	public void fire() {
		ss.getChordPlayer().setPitch(pitch);
	}

	@Override
	public boolean isPressed() {
		return ss.getChordPlayer().isChordPitchPressed(pitch);
	}

	@Override
	public void draggedFromTo(float x1, float y1, float x2, float y2, InputEvent event, int pointer) {

	}

}
