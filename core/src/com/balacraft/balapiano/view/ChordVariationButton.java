package com.balacraft.balapiano.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.balacraft.balapiano.soundengine.ChordPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

public class ChordVariationButton extends ButtonActor {
	//maj, min, etc
	protected ChordPlayer.ChordVariationType chord_variation;
	protected SoundSystem ss;
	
	public ChordVariationButton(ChordPlayer.ChordVariationType chord_variation, SoundSystem ss) {
		this.ss = ss;
		this.chord_variation=chord_variation;
	}

	@Override
	public void fire() {
		ss.getChordPlayer().setChordVariation(chord_variation);
	}

	@Override
	public boolean isPressed() {
		return ss.getChordPlayer().isChordVariationPressed(chord_variation);
	}

	@Override
	public void draggedFromTo(float x1, float y1, float x2, float y2, InputEvent event, int pointer) {

	}

}
