package com.balacraft.balapiano.soundengine;

import java.util.Comparator;

public class Note {

	private static Comparator<Note> startComparator = new NoteStartComparator();
	private static Comparator<Note> endComparator = new NoteEndComparator();

	int[] absolutePitches;
	long start;
	long dur;
	boolean loop;
	long[] ids;
	int relOct=0;
	public Note(int[] absolutePitches,int start, int dur, boolean loop) {
		this.absolutePitches= absolutePitches;
		this.start= start;
		this.dur = dur;
		this.loop= loop;
	}
	public Note(int absolutePitch, int start, int dur, boolean loop) {
		this.absolutePitches = new int[1];
		this.absolutePitches[0] = absolutePitch;
		this.start= start;
		this.dur = dur;
		this.loop= loop;
	}
	public Note(Note n) {
		absolutePitches = n.absolutePitches.clone();
		start = n.start;
		dur = n.dur;
		loop = n.loop;
		ids = null;
		relOct = n.relOct;
	}
	public int count() {
        return absolutePitches.length;
    }

	public void addMiddleCAndDefaultOctave(int middle_c, int defaultOctave) {
		for(int i = 0; i < absolutePitches.length; i++) {
			absolutePitches[i] = absolutePitches[i] + middle_c + defaultOctave * 12;
		}
	}

	public void setRelOct(int relOct) {
		this.relOct = relOct;
	}


	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	public static Comparator<Note> StartComparator() {
		return startComparator;
	}

	public static Comparator<Note> EndComparator() {
		return endComparator;
	}



	private static class NoteStartComparator implements Comparator<Note> {

		@Override
		public int compare(Note o1, Note o2) {
			return Long.valueOf(o1.start).compareTo(Long.valueOf(o2.start));
		}
	}

	private static class NoteEndComparator implements Comparator<Note> {

		@Override
		public int compare(Note o1, Note o2) {
			return Long.valueOf(o1.start+o1.dur).compareTo(Long.valueOf(o2.start+o2.dur));
		}
	}
}


