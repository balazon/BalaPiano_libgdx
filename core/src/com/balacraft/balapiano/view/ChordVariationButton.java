package com.balacraft.balapiano.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.balacraft.balapiano.soundengine.SoundSystem;

public class ChordVariationButton  extends ButtonActor {
	//maj, min, etc
	//private int chord_variation;
	private SoundSystem ss;
	
	public ChordVariationButton(int chord_variation,SoundSystem ss) {
		this.ss = ss;
		//this.chord_variation=chord_variation;
	}

	@Override
	public void fire() {
		//ss.getChordPlayer().setChordVariation(chord_variation);
	}

	@Override
	public boolean isPressed() {
		return false;
		//return ss.getChordPlayer().isChordVariationPressed(chord_variation);
	}

	@Override
	public void draggedFromTo(float x1, float y1, float x2, float y2, InputEvent event, int pointer) {

	}

}
