package com.balacraft.balapiano.soundengine;

import static com.balacraft.balapiano.soundengine.ChordPlayer.ChordVariationType.MAJOR;

public class ChordPlayer {
	boolean on = false;
	SoundSystem ss;
	int pitch = 0;
	int octave = -1;

	int channel = 1;
	
	private int[] pitches=new int[10];

	private int mod;
	private int interval=1000;

	enum ChordVariationType {
		DEFAULT,
		MAJOR,
		MINOR,
		MAJOR7,
		MINOR7
	}


	protected ChordVariationType chord_mode = ChordVariationType.DEFAULT;

	
	private static final int[] MAJOR_PITCH={0,4,7};
	private static final int[] MAJOR7_PITCH={0,4,7,10};
	private static final int[] MINOR_PITCH={0,3,7};
	private static final int[] MINOR7_PITCH={0,3,7,10};
	
	NoteEvent[] major_notes_on;

    //k for how many times the bpm faster button was pressed
	private int k = 0;




    long timer;

	boolean quit;

    //boolean paused;

	public ChordPlayer(SoundSystem ss) {
		this.ss = ss;
		quit = false;
        timer = 0;
	}

	public void process() {
		if(!on) {
			return;
		}
        timer -= Time.delta();
        if(timer < 0) {
            //ss.addNote(new Note(pitches, 0, interval, false));
            timer = interval;
        }


	}

//    public void setPaused(boolean paused) {
//        this.paused = paused;
//        if(paused)
//    }
//	public void chordMode(boolean on) {
//		this.on = on;
//	}
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
		}
        timer = 0;
		this.pitch = pitch;
		updateChord();

	}

	public void updateChord() {
		if(chord_mode == ChordVariationType.DEFAULT) chord_mode = ChordVariationType.MAJOR;

//		pitches = new int[pitches_tmp.length];
//		for(int i=0;i<pitches_tmp.length;i++) pitches[i]=pitch+pitches_tmp[i]+ ss.getSoundPlayer().getMiddleC() +  12 * (octave + ss.getSoundPlayer().getDefaultOctave());
	}

	public void addOctave(int relOct) {
		this.octave += relOct;
		updateChord();
	}
	public void setChordVariation(ChordVariationType variation) {
		chord_mode = variation;
		switch(chord_mode) {
		case MAJOR:

			break;
		case MINOR:

			break;
		case MAJOR7:

			break;
		case MINOR7:

			break;
		default:
			System.err.println("undefined chord value");
		}
		updateChord();
	}

	public boolean isChordVariationPressed(ChordVariationType variation){
		if (chord_mode == variation) return true;
		else return false;
	}
	public boolean isChordPitchPressed(int pitch) {
		if(this.pitch == pitch && on) return true;
		else return false;
	}
	
	
}
