package com.balacraft.balapiano.soundengine;

public class SoundSystem {


	SoundPlayer sp;

	ChordPlayer cp;


	public SoundSystem(SoundPlayer sp) {
		cp = new ChordPlayer(this);
		this.sp = sp;
    }


    public SoundPlayer getSoundPlayer() {
        return sp;
    }
	public ChordPlayer getChordPlayer() {
		return cp;
	}

	
	
	
}
