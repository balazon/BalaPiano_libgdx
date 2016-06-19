package com.balacraft.balapiano.soundengine;


import java.util.HashMap;
import java.util.Map;



public class ChordPlayer {
	boolean on = false;
	SoundSystem ss;
	int pitch = 0;
	int octave = -1;

	int channel = 5;
	

	private int interval = 1000;

	public enum ChordVariationType {
		POW,
		MAJ,
		MIN,
		DIM,
		AUG,
		SUS2,
		SUS4,
		DOM7,
		MAJ7,
		MIN7
	}

	Map<ChordVariationType, int[]> pitchFromType;

	protected ChordVariationType chord_mode = ChordVariationType.POW;

	protected static final int[] POW_PITCH = {0,7,12};
	protected static final int[] MAJ_PITCH = {0,4,7};
	protected static final int[] MIN_PITCH = {0,3,7};
	protected static final int[] DIM_PITCH = {0,3,6};
	protected static final int[] AUG_PITCH = {0,4,8};
	protected static final int[] SUS2_PITCH = {0,2,7};
	protected static final int[] SUS4_PITCH = {0,5,7};
	protected static final int[] DOM7_PITCH = {0,4,7,10};
	protected static final int[] MAJ7_PITCH = {0,4,7,11};
	protected static final int[] MIN7_PITCH = {0,3,7,10};




	NoteEvent[] actualChord;

	NoteEvent[] actualChordOff;
	NoteEvent[] lastChordOff;


    //k for how many times the bpm faster button was pressed
	private int k = 0;

    long timer;



	public ChordPlayer(SoundSystem ss) {
		this.ss = ss;
        timer = 0;
		init();
	}

	protected void init() {
		lastChordOff = null;
		pitchFromType = new HashMap<ChordVariationType, int[]>(20);
		pitchFromType.put(ChordVariationType.POW, POW_PITCH);
		pitchFromType.put(ChordVariationType.MAJ, MAJ_PITCH);
		pitchFromType.put(ChordVariationType.MIN, MIN_PITCH);
		pitchFromType.put(ChordVariationType.DIM, DIM_PITCH);
		pitchFromType.put(ChordVariationType.AUG, AUG_PITCH);
		pitchFromType.put(ChordVariationType.SUS2, SUS2_PITCH);
		pitchFromType.put(ChordVariationType.SUS4, SUS4_PITCH);
		pitchFromType.put(ChordVariationType.DOM7, DOM7_PITCH);
		pitchFromType.put(ChordVariationType.MAJ7, MAJ7_PITCH);
		pitchFromType.put(ChordVariationType.MIN7, MIN7_PITCH);
	}

	NoteEvent[] getEventsForPitchVariationType(int pitch, ChordVariationType variation, NoteEvent.Type type) {
		int middleC = ss.getSoundPlayer().getMiddleC();
		int[] pitches = pitchFromType.get(variation);
		NoteEvent[] events = new NoteEvent[pitches.length];
		for(int i = 0; i < pitches.length; i++) {
			int p = pitches[i] + 12 * octave + middleC + pitch;
			NoteEvent ne = new NoteEvent(type, p, channel);
			events[i] = ne;
		}
		return events;
	}

	void takeHandsOffLastChord() {
		if(lastChordOff != null) {
			ss.getSoundPlayer().processNoteEvents(lastChordOff);
		}
	}

	public void process() {
		if(!on) {
			return;
		}
        timer -= Time.delta();
        if(timer < 0) {
	        takeHandsOffLastChord();
            ss.getSoundPlayer().processNoteEvents(actualChord);
	        lastChordOff = actualChordOff;
            timer = interval;
        }


	}

	public void setBPM(int bpm) {
		interval = 60 * 1000 / bpm;
	}
	public void setBPMRelative(int rel_bpm_factor) {
		if(k < 20 && rel_bpm_factor > 0 || k > -20 && rel_bpm_factor < 0) {
			k+=rel_bpm_factor;
			int bpm = (int) (60.0f*Math.pow(1.05, k));
			interval = 60*1000/bpm;
		}
	}


	public void setPitch(int pitch) {
		if(!on) {
			on= true;
		}
		else if(this.pitch == pitch) {
			on = false;
			takeHandsOffLastChord();
			timer = 0;
		}


		this.pitch = pitch;
		updateChord();

	}

	public void updateChord() {
		actualChord = getEventsForPitchVariationType(pitch, chord_mode, NoteEvent.Type.NOTE_ON);
		actualChordOff = getEventsForPitchVariationType(pitch, chord_mode, NoteEvent.Type.NOTE_OFF);
	}

	public void addOctave(int relOct) {
		this.octave += relOct;
		updateChord();
	}
	public void setChordVariation(ChordVariationType variation) {
		chord_mode = variation;
		updateChord();
	}

	public boolean isChordVariationPressed(ChordVariationType variation){
		return chord_mode == variation;
	}
	public boolean isChordPitchPressed(int pitch) {
		return this.pitch == pitch && on;
	}
	
	
}
