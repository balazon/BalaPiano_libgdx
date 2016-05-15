package hu.balayon.pianogame.view;

import hu.balayon.pianogame.soundengine.SoundSystem;

public class OctaveButton extends Button{
	protected SoundSystem ss;
	protected boolean isPressed=false;
	private int relOct;
	
	public OctaveButton(SoundSystem ss,int relOct) {
		this.ss = ss;
		this.relOct = relOct;
	}
	
	public void pressed(int x, int y) {
		if(contains(x,y) && !isPressed) {
			isPressed=true;
			play();
		}
	}
	public void released(int x, int y) {
		if(contains(x,y)) isPressed=false;
	}

	public void play() {
		PianoKey.addRelOct(relOct);
	}
	public boolean isPressed() { return isPressed;}
}

