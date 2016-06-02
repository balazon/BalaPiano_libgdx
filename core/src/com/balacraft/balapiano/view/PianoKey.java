package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;

import com.badlogic.gdx.math.Rectangle;




public abstract class PianoKey extends Button{
	protected Note note;
	protected static SoundSystem ss;



	private static int relOct = 0;
	
	
	public PianoKey(Note n,SoundSystem ss) {
		int middle_c = ss.getSoundPlayer().getMiddleC();
        int octave = ss.getSoundPlayer().getDefaultOctave();
        n.addMiddleCAndDefaultOctave(middle_c, octave);
		this.note = n;
		this.ss = ss;


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
	
	public void draggedFromTo(int x1, int y1, int x2, int y2) {
		if(isPressed) {
			if(contains(x1,y1) && !contains(x2,y2)) isPressed=false;
		}
		else if(contains(x2,y2)) {
			isPressed=true;
			fire();
		}else {
			for(Sprite s : sprites_up) {
			Rectangle rect = s.getBoundingRectangle();
				if( Algorithms.lineSegmentIntersectsRect(rect, x1,y1,x2,y2)) {
					fire();
					break;
				}
			}
		}
	}
//	public void pressed(int x, int y) {
//		if(contains(x,y) && !isPressed) {
//			isPressed=true;
//            System.out.println("pianokey press");
//            play();
//		}
//	}
//	public void released(int x, int y) {
//		if(contains(x,y)) isPressed=false;
//	}

	@Override
	public void fire() {
		note.setRelOct(relOct);
		ss.addNote(note);
	}
	//public boolean isPressed() { return isPressed;}
}
