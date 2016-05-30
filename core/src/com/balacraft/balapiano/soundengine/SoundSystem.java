package com.balacraft.balapiano.soundengine;

import java.util.LinkedList;
import java.util.TreeSet;

import com.badlogic.gdx.utils.Disposable;

public class SoundSystem implements Disposable{

	TreeSet<Note> startedNotes = new TreeSet<Note>();
	SoundPlayer sp;


	boolean stop= false;
	ChordPlayer cp;
	LinkedList<Note> queue = new LinkedList<Note>();

	public SoundSystem() {
		cp = new ChordPlayer(this);

	}
	public void initSoundPlayer() {
		sp = new SoundPlayer();
		sp.loadSounds();
	}

    public SoundPlayer getSoundPlayer() {
        return sp;
    }
	
	public void addNote(Note n) {
        System.out.println("soundsys addnote");

		n.start += Time.current();
		queue.add(n);
	}

	public void process() {
		cp.process();
        //play notes in the queue
		while(!queue.isEmpty()) {
			Note n = queue.removeFirst();
			sp.playNote(n);
			startedNotes.add(n);
		}

		//stop notes that should be finished
        int loopcount = 0;
        while(true) {
            if(loopcount > 5) {
                System.out.println("problem?");
            }
            Note first = null;
			if(!startedNotes.isEmpty()) {
				first = startedNotes.first();
			}

			if(first != null && (first.start + first.dur) < Time.current()) {
				sp.stopNote(first);
                startedNotes.pollFirst();
                //TODO .remove(first) isn't working : why?
//                boolean contained = startedNotes.remove(first);
//                System.out.println("contained: " + contained);
            } else {
				break;
			}
            loopcount++;
		}
	}
	
	public void setFlat() { cp.modPressed(false); }
	public void setSharp() {
		cp.modPressed(true);
	}
	public void setChordVariation(int chord_id) {
		cp.setChordVariation(chord_id);
	}
	public void setChordPitch(int pitch) { cp.setPitch(pitch); }
	public void setChordBPM(int rel_bpm_fact) {
		cp.setBPM(rel_bpm_fact);
	}
	public boolean isSharp() {
		return cp.isSharp();
	}
	public boolean isFlat() {
		return cp.isFlat();
	}
	public boolean isChordVariationPressed(int chord_id){ return cp.isChordVariationPressed(chord_id); }
	public boolean isChordPitchPressed(int pitch) {
		return cp.isChordPitchPressed(pitch);
	}

	@Override
	public void dispose() {
		sp.dispose();
	}
	
	
	
}
