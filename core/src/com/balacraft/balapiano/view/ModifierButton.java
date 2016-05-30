package com.balacraft.balapiano.view;

import com.balacraft.balapiano.soundengine.SoundSystem;

import com.badlogic.gdx.math.Rectangle;

public class ModifierButton extends Button {
	//#, b
	private boolean sharp;
	SoundSystem ss;
	
	public ModifierButton(boolean isSharp,SoundSystem ss) {
		this.ss = ss;
		sharp=isSharp;
	}

	@Override
	public void pressed(int x, int y) {
		if (this.r1.contains(x, y)) {
			if(sharp) {
				ss.setSharp();
			}
			else {
				ss.setFlat();
			}
		}
	}

	@Override
	public boolean isPressed() {
		if(sharp) return ss.isSharp();
		else return ss.isFlat();
	}

	@Override
	public void draggedFromTo(int x1, int y1, int x2, int y2) {
		
	}


}
