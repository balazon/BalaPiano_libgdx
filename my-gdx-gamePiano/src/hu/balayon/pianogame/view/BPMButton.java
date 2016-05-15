package hu.balayon.pianogame.view;

import hu.balayon.pianogame.soundengine.SoundSystem;

public class BPMButton extends Button{

	protected SoundSystem ss;
	protected boolean isPressed=false;
	private int relBPMfact;
	
	public BPMButton(SoundSystem ss,int relBPMfact) {
		this.ss = ss;
		this.relBPMfact = relBPMfact;
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
		ss.setChordBPM(relBPMfact);
	}
	public boolean isPressed() { return isPressed;}
}