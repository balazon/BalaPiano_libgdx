package com.balacraft.balapiano.soundengine;

public class Note implements Comparable<Note>{
	int[] pitch;
	long start;
	long dur;
	boolean loop;
	long[] ids;
	int relOct=0;
	public Note(int[] pitch,int start, int dur, boolean loop) {
		this.pitch= pitch;
		this.start= start;
		this.dur = dur;
		this.loop= loop;
	}
	public Note(int pitch, int start, int dur, boolean loop) {
		this.pitch = new int[1];
		this.pitch[0] = pitch;
		this.start= start;
		this.dur = dur;
		this.loop= loop;
	}
	public void setRelOct(int relOct) {
		this.relOct = relOct;
	}
	@Override
	public int compareTo(Note o) {
		return Long.compare(start+dur, o.start+dur);
	}
}
