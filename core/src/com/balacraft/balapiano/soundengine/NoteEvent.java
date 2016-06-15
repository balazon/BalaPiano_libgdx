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
	};

//	private static Comparator<NoteEvent> comp = new NoteEvent.NoteEventStartComparator();
//
//	public static Comparator<NoteEvent> comparator() {
//		return comp;
//	}
//
//	private static class NoteEventStartComparator implements Comparator<NoteEvent> {
//
//		@Override
//		public int compare(NoteEvent o1, NoteEvent o2) {
//			return Long.valueOf(o1.start).compareTo(Long.valueOf(o2.start));
//		}
//	}
}

