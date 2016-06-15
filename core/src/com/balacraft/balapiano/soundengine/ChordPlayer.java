package com.balacraft.balapiano.soundengine;

public class ChordPlayer {
	boolean on = false;
	SoundSystem ss;
	int pitch = 0;
	int octave = -1;
	
	
	private int[] pitches=new int[10];
	private boolean flat=false;
	private boolean sharp=false;
	private int mod;
	private int interval=1000;
	
	public static final int DEFAULT = -1;
	public static final int MAJOR=0;
	public static final int MINOR=1;
	public static final int MAJOR7=2;
	public static final int MINOR7=3;


	
	private int chord_mode=DEFAULT;
	
	private static final int[] MAJOR_PITCH={0,4,7};
	private static final int[] MAJOR7_PITCH={0,4,7,10};
	private static final int[] MINOR_PITCH={0,3,7};
	private static final int[] MINOR7_PITCH={0,3,7,10};
	
	private int[] pitches_tmp=MAJOR_PITCH;

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
		if(chord_mode == DEFAULT) chord_mode = MAJOR;
		if(flat) mod=-1;
		else if (sharp) mod=+1;
		else mod=0;
		pitches = new int[pitches_tmp.length];
		for(int i=0;i<pitches_tmp.length;i++) pitches[i]=pitch+mod+pitches_tmp[i]+ ss.getSoundPlayer().getMiddleC() +  12 * (octave + ss.getSoundPlayer().getDefaultOctave());
	}

	public void addOctave(int relOct) {
		this.octave += relOct;
		updateChord();
	}
	public void setChordVariation(final int chord_id) {
		chord_mode=chord_id;
		switch(chord_mode) {
		case MAJOR:
			pitches_tmp=MAJOR_PITCH;
			break;
		case MINOR:
			pitches_tmp=MINOR_PITCH;
			break;
		case MAJOR7:
			pitches_tmp=MAJOR7_PITCH;
			break;
		case MINOR7:
			pitches_tmp=MINOR7_PITCH;
			break;
		default:
			System.err.println("undefined chord value");
		}
		updateChord();
	}
	
	
//	public void modPressed(boolean sharp) {
//		if(!this.sharp && !flat || this.sharp && !sharp || this.flat && sharp) {
//			this.sharp = sharp ;
//			flat = !sharp;
//		}
//		else {
//			this.sharp = false;
//			flat = false;
//		}
//		updateChord();
//	}
	public boolean isChordVariationPressed(int chord_id){
		if (chord_mode == chord_id) return true;
		else return false;
	}
	public boolean isSharp(){
		return sharp;
	}
	public boolean isFlat() {
		return flat;
	}
	public boolean isChordPitchPressed(int pitch2) {
		if(pitch == pitch2 && on) return true;
		else return false;
	}
	
	
}
