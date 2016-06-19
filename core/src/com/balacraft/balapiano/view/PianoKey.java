package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.NoteEvent;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;


public class PianoKey extends ButtonActor{
	protected NoteEvent noteEventOn;
	protected NoteEvent noteEventOff;
	protected static SoundSystem ss;




	private static int relOct = 0;

	
	public PianoKey(int pitch, int channel,SoundSystem ss) {
		super(false);
		noteEventOn = new NoteEvent(NoteEvent.Type.NOTE_ON, pitch, channel);
		noteEventOff = new NoteEvent(NoteEvent.Type.NOTE_OFF, pitch, channel);
		this.ss = ss;
		setName("PianoKey " + pitch);
    }

	public static void addRelOct(int rel) {
		SoundPlayer sp = ss.getSoundPlayer();
		int range_min = sp.getRangeMin();
		int range_max = sp.getRangeMax();
		int middle_c = sp.getMiddleC();
		int minRelOct = -(middle_c - range_min) / 12;
		int maxRelOct = (range_max - middle_c - 13) / 12;
		if(rel > 0 && relOct +rel <= maxRelOct) relOct+=rel;
		else if(rel <0 && relOct+rel >= minRelOct) relOct+=rel;
	}

	@Override
	public void fire() {
		ss.getSoundPlayer().processNoteEvent(noteEventOn);

		System.out.println(this.toString() + " fired");
	}

	@Override
	public void release() {
		ss.getSoundPlayer().processNoteEvent(noteEventOff);
	}

}
