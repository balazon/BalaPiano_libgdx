package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.SoundSystem;

public class OctaveButton extends Button{
	protected SoundSystem ss;
	protected boolean isPressed=false;
	private int relOct;
	
	public OctaveButton(SoundSystem ss,int relOct) {
		this.ss = ss;
		this.relOct = relOct;
	}

	@Override
	public void fire() {
		PianoKey.addRelOct(relOct);
	}

}

