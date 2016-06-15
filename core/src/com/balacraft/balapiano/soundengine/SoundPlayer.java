package com.balacraft.balapiano.soundengine;


import com.badlogic.gdx.utils.Disposable;

public interface SoundPlayer extends Disposable {

	void init();

	void processNoteEvent(NoteEvent ne);

	void processNoteEvents(NoteEvent[] noteEvents);

	int getDefaultOctave();
	int getMiddleC();
	int getRangeMin();
	int getRangeMax();


	//void stop();
}
