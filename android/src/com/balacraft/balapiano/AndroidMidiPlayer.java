package com.balacraft.balapiano;

import android.util.Log;

import com.balacraft.balapiano.soundengine.Note;
import com.balacraft.balapiano.soundengine.SoundPlayer;

import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.billthefarmer.mididriver.MidiConstants;
import org.billthefarmer.mididriver.MidiDriver;

/**
 * Created by valentin on 2016.06.12..
 */

public class AndroidMidiPlayer implements SoundPlayer, MidiDriver.OnMidiStartListener{

	static {
		System.loadLibrary("midi");
	}

	MidiDriver midi;

	AndroidMidiPlayer() {

	}

	public void start() {
//		midi = new MidiDriver();
//		if(!midi.start()) {
//			Log.e("MIDI", "midi FAIL");
//		}
	}

	public void stop() {
		if(midi != null) {
			midi.stop();
			midi = null;
		}
	}

	public void init() {
		midi = new MidiDriver();
		if(!midi.start()) {
			Log.e("MIDI", "midi FAIL");
		}
	}

	protected void noteOn(int pitch) {
		int channel = 1;
		int velocity = 127;

		sendMidi(MidiConstants.NOTE_ON + (channel - 1), pitch, velocity);
	}

	protected void noteOff(int pitch) {
		int channel = 1;
		sendMidi(MidiConstants.NOTE_OFF + (channel - 1), pitch, 0);
	}

	@Override
	public void onMidiStart() {
		sendMidi(MidiConstants.PROGRAM_CHANGE, GeneralMidiConstants.ACOUSTIC_GRAND_PIANO);
	}


	protected void sendMidi(int m, int p) {
		byte[] msg = new byte[2];
		msg[0] = (byte) m;
		msg[1] = (byte) p;
		midi.write(msg);
	}

	protected void sendMidi(int m, int n, int v)
	{
		byte[] msg = new byte[3];

		msg[0] = (byte) m;
		msg[1] = (byte) n;
		msg[2] = (byte) v;

		midi.write(msg);
	}

	@Override
	public void playNote(Note n) {
		int velocity = 127;
		int channel = 1;
		int[] pitches = n.absolutePitches;
		byte[] msg = new byte[pitches.length * 3];
		for(int i = 0; i < pitches.length; i++) {
			msg[i * 3] = (byte) (MidiConstants.NOTE_ON + (channel - 1));
			msg[i * 3 + 1] = (byte) pitches[i];
			msg[i * 3 + 2] = (byte) velocity;
		}

		midi.write(msg);
	}

	@Override
	public void stopNote(Note n) {
		int velocity = 0;
		int channel = 1;
		int[] pitches = n.absolutePitches;
		byte[] msg = new byte[pitches.length * 3];
		for(int i = 0; i < pitches.length; i++) {
			msg[i * 3] = (byte) (MidiConstants.NOTE_OFF + (channel - 1));
			msg[i * 3 + 1] = (byte) pitches[i];
			msg[i * 3 + 2] = (byte) velocity;
		}

		midi.write(msg);
	}

	@Override
	public int getDefaultOctave() {
		return 0;
	}
	@Override
	public int getMiddleC() {
		return 60;
	}
	@Override
	public int getRangeMin() {
		return 0;
	}
	@Override
	public int getRangeMax() {
		return 127;
	}

	@Override
	public void dispose() {
		stop();
	}
}
