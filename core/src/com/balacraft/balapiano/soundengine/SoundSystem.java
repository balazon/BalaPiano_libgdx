package com.balacraft.balapiano.soundengine;

import java.util.LinkedList;
import java.util.PriorityQueue;


import com.badlogic.gdx.utils.Disposable;

public class SoundSystem implements Disposable{

    PriorityQueue<Note> comingupNotes = new PriorityQueue<Note>(20, Note.StartComparator());
    PriorityQueue<Note> startedNotes = new PriorityQueue<Note>(60, Note.EndComparator());
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

        //copying note n, so that we don't overwrite anything already in the queues (like their start value)
        Note copy = new Note(n);
        copy.start += Time.current();
		queue.add(copy);
	}

	public void process() {
		cp.process();

        //add notes from input queue to the notes that will be played
		while(!queue.isEmpty()) {
			Note n = queue.removeFirst();
            comingupNotes.add(n);
		}

        //play notes that are due
        while(true) {
            Note first = comingupNotes.peek();
            if(first != null && first.start <= Time.current()) {
                comingupNotes.poll();
                sp.playNote(first);
                startedNotes.add(first);
            } else {
                break;
            }
        }

		//stop notes that should be finished
        while(true) {
            Note first = startedNotes.peek();
			if(first != null && (first.start + first.dur) < Time.current()) {
				sp.stopNote(first);
                startedNotes.poll();
            } else {
				break;
			}
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
