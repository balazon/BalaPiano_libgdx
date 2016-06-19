package com.balacraft.balapiano.desktop;

import com.balacraft.balapiano.soundengine.NoteEvent;
import com.balacraft.balapiano.soundengine.SoundPlayer;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


public class DesktopMidiPlayer implements SoundPlayer {

	Synthesizer syn;
	MidiChannel[] mc;

	int noteOnVelocity = 64;

	@Override
	public void init() {

		try {
			syn = MidiSystem.getSynthesizer();
			syn.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}

		mc = syn.getChannels();
		Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
		syn.loadInstrument(instr[0]);
	}

	@Override
	public void processNoteEvent(NoteEvent ne) {
		switch(ne.type) {
			case NOTE_ON:
				mc[ne.channel].noteOn(ne.pitch,noteOnVelocity);
				break;
			case NOTE_OFF:
				mc[ne.channel].noteOff(ne.pitch);
				break;
		}
	}



	@Override
	public void processNoteEvents(NoteEvent[] noteEvents) {
		for(NoteEvent ne : noteEvents) {
			processNoteEvent(ne);
		}
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
		syn.close();
	}
}
