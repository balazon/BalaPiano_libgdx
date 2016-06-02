package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.SoundSystem;

import com.badlogic.gdx.math.Rectangle;

public class ChordPitchButton extends Button{
	private int pitch;
	private char id;
	SoundSystem ss;

	public ChordPitchButton(char id,int pitch,SoundSystem ss) {
		this.ss = ss;
		this.id=id;
		this.pitch = pitch;
	}

	@Override
	public void fire() {
		ss.setChordPitch(pitch);
	}

	@Override
	public boolean isPressed() {
		return ss.isChordPitchPressed(pitch);
	}
	@Override
	public void draggedFromTo(int x1, int y1, int x2, int y2) {

	}

}
