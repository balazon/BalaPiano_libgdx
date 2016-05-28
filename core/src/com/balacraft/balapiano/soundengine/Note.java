package com.balacraft.balapiano.soundengine;

public class Note implements Comparable<Note>{
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
	public int count() {
        return absolutePitches.length;
    }
    public void setAbsoluteOctave(int octave) {
        for(int i = 0; i < absolutePitches.length; i++) {
            int p = absolutePitches[i];
            int plusOctave = (p % 100) / 12;
            absolutePitches[i] = (p % 100) % 12 + (octave + plusOctave) * 100;
        }
    }
	public void setRelOct(int relOct) {
		this.relOct = relOct;
	}
	@Override
	public int compareTo(Note o) {
		return Long.compare(start+dur, o.start+dur);
	}
}
