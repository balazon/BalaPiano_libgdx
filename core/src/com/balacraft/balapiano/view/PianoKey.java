package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;
import com.balacraft.balapiano.soundengine.SoundSystem;




public class PianoKey extends ButtonActor{
	protected Note note;
	protected static SoundSystem ss;


	private static int relOct = 0;

	
	public PianoKey(Note n,SoundSystem ss) {
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


	public void draggedFromTo(float x1, float y1, float x2, float y2) {
		//System.out.println(String.format("%s (%.2f %.2f)->(%.2f %.2f)", toString(), x1, y1, x2, y2));
		if(isPressed()) {
			if(contains(x1,y1) && !contains(x2,y2)) pressed =false;
		}
		else if(contains(x2,y2)) {
			pressed = true;
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

	@Override
	public void fire() {
		note.setRelOct(relOct);
		ss.addNote(note);

		System.out.println(this.toString() + " fired");
	}

}
