package com.balacraft.balapiano.soundengine;

import java.util.LinkedList;
import java.util.TreeSet;

import com.badlogic.gdx.utils.Disposable;

public class SoundSystem implements Disposable{

	TreeSet<Note> startedNotes = new TreeSet<Note>();
	SoundPlayer sp;
	long currentTime;
	long prevTime;
	// reltime is the time that elapsed since the start of the app
	long relTime;
	boolean stop= false;
	ChordPlayer cp;
	LinkedList<Note> queue = new LinkedList<Note>();

	public SoundSystem() {

		currentTime = System.currentTimeMillis();
		prevTime = currentTime;
		relTime = 0;
		cp = new ChordPlayer(this);

	}
	public void setSoundPlayer(String[] C) {
		sp = new SoundPlayer();
		sp.setSounds(C);
	}
	
	public void addNote(Note n) {
		long ctime = System.currentTimeMillis();
		n.start += ctime;
		queue.add(n);
	}

	public void process() {
		//play notes in the queue
		while(!queue.isEmpty()) {
			Note n = queue.removeFirst();
			sp.playNote(n);
			startedNotes.add(n);
		}

		//stop notes that should be finished
		prevTime = currentTime;
		currentTime = System.currentTimeMillis();
		relTime += currentTime - prevTime;
        //System.out.println("Time: " + currentTime);
        while(true) {
			Note first = null;
			if(!startedNotes.isEmpty()) {
				first = startedNotes.first();
			}

			if(first != null && (first.start + first.dur) < currentTime) {
				sp.stopNote(first);
				startedNotes.remove(first);
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
