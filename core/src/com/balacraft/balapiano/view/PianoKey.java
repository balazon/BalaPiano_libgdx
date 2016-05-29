package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundSystem;

import com.badlogic.gdx.math.Rectangle;




public abstract class PianoKey extends Button{
	protected Note note;
	protected SoundSystem ss;
	protected boolean isPressed=false;
	Rectangle rect;

	private static int relOct = 0;
	
	
	public PianoKey(Note n,SoundSystem ss) {
        int octave = ss.getSoundPlayer().getDefaultOctave();
        n.setAbsoluteOctave(octave);
		this.note = n;
		this.ss = ss;
		rect = new Rectangle();

    }

	//TODO make this dependent on soundplayer's range
	public static void addRelOct(int rel) {
		if(rel > 0 && relOct +rel <=3) relOct+=rel;
		else if(rel <0 && relOct+rel >= -2) relOct+=rel;
	}
	
	public void draggedFromTo(int x1, int y1, int x2, int y2) {
		if(isPressed) {
			if(contains(x1,y1) && !contains(x2,y2)) isPressed=false;
		}
		else if(contains(x2,y2)) {
			isPressed=true;
			play();
		}else {
			rect.set(r1.x, r1.y, r1.width, r1.height);
			if( Algorithms.lineSegmentIntersectsRect(rect, x1,y1,x2,y2)) {
				play();
			}
		}
	}
	public void pressed(int x, int y) {
		if(contains(x,y) && !isPressed) {
			isPressed=true;
            System.out.println("pianokey press");
            play();
		}
	}
	public void released(int x, int y) {
		if(contains(x,y)) isPressed=false;
	}

	public void play() {
		note.setRelOct(relOct);
		ss.addNote(note);
	}
	public boolean isPressed() { return isPressed;}
}
