package com.balacraft.balapiano.soundengine;


import java.util.Comparator;

public class NoteEvent {

	public Type type;

	public int pitch;

	public int channel;


	public NoteEvent(Type type, int pitch, int channel) {
		this.type = type;
		this.pitch = pitch;
		this.channel = channel;
	}

	public enum Type {
		NOTE_ON,
		NOTE_OFF
	}

}

