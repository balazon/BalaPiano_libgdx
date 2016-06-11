package com.balacraft.balapiano.soundengine;


import com.badlogic.gdx.utils.Disposable;

public interface SoundPlayer extends Disposable {

	void playNote(Note n);

	void stopNote(Note n);

	int getDefaultOctave();
	int getMiddleC();
	int getRangeMin();
	int getRangeMax();

	void init();
	//void stop();
}
