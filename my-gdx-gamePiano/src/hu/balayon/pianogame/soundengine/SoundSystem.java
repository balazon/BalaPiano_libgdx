package hu.balayon.pianogame.soundengine;

import java.util.TreeSet;

import com.badlogic.gdx.utils.Disposable;

public class SoundSystem implements Disposable{
	Fifo fifo;
	TreeSet<Note> startedNotes = new TreeSet<Note>();
	SoundPlayer sp;
	long currentTime;
	long prevTime;
	long relTime;
	boolean stop= false;
	ChordPlayer cp;
	
	public SoundSystem() {
		fifo = new Fifo();
		currentTime = System.currentTimeMillis();
		prevTime = currentTime;
		relTime = 0;
		cp = new ChordPlayer(this);
	}
	public void setSoundPlayer(String[] C) {
		sp = new SoundPlayer();
		sp.setSounds(C);
	}
	
	public void addNote(Note n) {
		long ctime = System.currentTimeMillis();
		n.start += ctime;
		synchronized(fifo) {
		fifo.add(n);
		fifo.notify();
		}
	}
	public void start() {
		new Thread() {
			@Override
			public void run() {
				while(!stop) {
					Note n;
					synchronized(fifo) {
						if(fifo.todoList.isEmpty()) {
							try {
								fifo.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						n = fifo.getNote();
					}
					sp.playNote(n);
					synchronized(startedNotes) {
						startedNotes.add(n);
						startedNotes.notify();
					}
				}
			}	
		}.start();
		new Thread() {
			public void run() {
				while(!stop) {
					prevTime = currentTime;
					currentTime = System.currentTimeMillis();
					relTime += currentTime - prevTime;
					Note first;
					synchronized(startedNotes) {
						if(startedNotes.isEmpty())  {
							try {
								startedNotes.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						first =startedNotes.first();
					}
					if(first.start+first.dur < currentTime) {
						sp.stopNote(first);
						synchronized(startedNotes) {
							startedNotes.remove(first);
						}
					}
				}
			}
		}.start();
	}
	public void stop() {
		stop = true;
	}
	
	public void setFlat() {
		cp.modPressed(false);
		
	}
	public void setSharp() {
		cp.modPressed(true);
	}
	public void setChordVariation(int chord_id) {
		cp.setChordVariation(chord_id);
	}
	public void setChordPitch(int pitch) {
		cp.setPitch(pitch);
	}
	public void setChordBPM(int rel_bpm_fact) {
		cp.setBPM(rel_bpm_fact);
	}
	public boolean isSharp() {
		return cp.isSharp();
	}
	public boolean isFlat() {
		return cp.isFlat();
	}
	public boolean isChordVariationPressed(int chord_id){
		return cp.isChordVariationPressed(chord_id);
	}
	public boolean isChordPitchPressed(int pitch) {
		return cp.isChordPitchPressed(pitch);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		sp.dispose();
	}
	
	
	
}
