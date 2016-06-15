package com.balacraft.balapiano.soundengine;

import java.util.LinkedList;
import java.util.PriorityQueue;


import com.badlogic.gdx.utils.Disposable;

public class SoundSystem implements Disposable{


	SoundPlayer sp;


	boolean stop= false;
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
//	public void setFlat() { cp.modPressed(false); }
//	public void setSharp() {
//		cp.modPressed(true);
//	}
//	public void setChordVariation(int chord_id) {
//		cp.setChordVariation(chord_id);
//	}
//	public void setChordPitch(int pitch) { cp.setPitch(pitch); }
//	public void setChordBPM(int rel_bpm_fact) {
//		cp.setBPM(rel_bpm_fact);
//	}
//	public boolean isSharp() {
//		return cp.isSharp();
//	}
//	public boolean isFlat() {
//		return cp.isFlat();
//	}
//	public boolean isChordVariationPressed(int chord_id){ return cp.isChordVariationPressed(chord_id); }
//	public boolean isChordPitchPressed(int pitch) {
//		return cp.isChordPitchPressed(pitch);
//	}

	@Override
	public void dispose() {
		//sp.dispose();
	}
	
	
	
}
