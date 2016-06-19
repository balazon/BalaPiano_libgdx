package com.balacraft.balapiano;

import android.util.Log;

import com.balacraft.balapiano.soundengine.NoteEvent;
import com.balacraft.balapiano.soundengine.SoundPlayer;

import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.billthefarmer.mididriver.MidiConstants;
import org.billthefarmer.mididriver.MidiDriver;


public class AndroidMidiPlayer implements SoundPlayer, MidiDriver.OnMidiStartListener{

	static {
		System.loadLibrary("midi");
	}

	MidiDriver midi;

	int noteOnVelocity = 64;
	int noteOffVelocity = 64;

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
	public void processNoteEvent(NoteEvent ne) {
		byte[] msg = new byte[3];
		writeNoteEventToArray(msg, 0, ne);
		midi.write(msg);
	}

	@Override
	public void processNoteEvents(NoteEvent[] noteEvents) {
		byte[] msg = new byte[noteEvents.length * 3];
		for(int i = 0; i < noteEvents.length; i++) {
			writeNoteEventToArray(msg, i * 3, noteEvents[i]);
		}
		midi.write(msg);
	}

	protected void writeNoteEventToArray(byte[] msg, int offset, NoteEvent ne) {
		switch(ne.type) {
			case NOTE_ON:
				writeNoteOnEventToArray(msg, offset, ne);
				break;
			case NOTE_OFF:
				writeNoteOffEventToArray(msg, offset, ne);
				break;
		}
	}
	protected void writeNoteOnEventToArray(byte[] msg, int offset, NoteEvent ne) {
		msg[offset] = (byte) (MidiConstants.NOTE_ON + ne.channel);
		msg[offset + 1] = (byte) ne.pitch;
		msg[offset + 2] = (byte) noteOnVelocity;
	}
	protected void writeNoteOffEventToArray(byte[] msg, int offset, NoteEvent ne) {
		msg[offset] = (byte) (MidiConstants.NOTE_OFF + ne.channel);
		msg[offset + 1] = (byte) ne.pitch;
		msg[offset + 2] = (byte) noteOffVelocity;
	}


	@Override
	public int getMiddleC() {
		return 60;
	}
	@Override
	public int getRangeMin() {
		return 21;
	}
	@Override
	public int getRangeMax() {
		return 108;
	}

	@Override
	public void dispose() {
		stop();
	}
}
