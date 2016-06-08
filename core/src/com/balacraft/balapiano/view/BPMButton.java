package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.SoundSystem;

public class BPMButton extends ButtonActor{

	protected SoundSystem ss;
	protected boolean isPressed=false;
	private int relBPMfact;
	
	public BPMButton(SoundSystem ss,int relBPMfact) {
		this.ss = ss;
		this.relBPMfact = relBPMfact;
	}
	

	@Override
	public void fire() {
		ss.setChordBPM(relBPMfact);
	}


}