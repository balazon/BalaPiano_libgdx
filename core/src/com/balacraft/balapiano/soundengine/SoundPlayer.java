package com.balacraft.balapiano.soundengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SoundPlayer implements Disposable{


    private Map<Integer, Sound> sounds;
    //stores keys usable on Map<> sounds for any note in the range_min -> range_max  e.g: if there is a sound for 301, then soundKeys[getIndex(301)] == 301 will be true
    private int[] soundKeys;
    //stores relative pitches to be used for notes
    private int[] soundPitches;
    int range_min;
    int range_max;
    int default_octave;

    public int getDefaultOctave() {
        return default_octave;
    }

    int getIndex(int absolutePitch) {
        int minOctave = (int)(range_min / 100);
        int absoluteOctave = (int)(absolutePitch / 100);
        int index = 12 - (range_min % 100) + (absoluteOctave - minOctave - 1) * 12 + (absolutePitch % 100);
        return index;
    }
    //TODO create all transformedSounds in loadSounds to eliminate dynamic alloc
    TransformedSound getSound(int absolutePitch) {
        int index = getIndex(absolutePitch);
        return new TransformedSound(sounds.get(soundKeys[index]), soundPitches[index]);
    }
    //read settings from config file
	void loadSounds() {
        sounds = new HashMap<Integer, Sound>();
        FileHandle confFile = Gdx.files.internal("config.txt");
        if(!confFile.exists() || confFile.isDirectory()) {
            System.out.println("Error : config.txt not found");
            return;
        }
        String config = confFile.readString();
        String[] rows = config.split("\\r?\\n");
        for(String row : rows) {
            if(row.startsWith("//") || row.equals("")) {
                continue;
            }
            if(row.startsWith("range")) {
                String[] range = row.split(":\\s*|\\s*-\\s*");
                range_min = Integer.parseInt(range[1]);
                range_max = Integer.parseInt(range[2]);
                soundKeys = new int[getIndex(range_max) + 1];
                soundPitches = new int[soundKeys.length];
//                for(int i = 0; i < soundKeys.length; i++) {
//                    soundKeys[i] = 0;
//                    soundPitches[i] = 0;
//                }
                continue;
            }
            if(row.startsWith("default_octave")) {
                default_octave = Integer.parseInt(row.split(":\\s*")[1]);
                continue;
            }
            if(row.startsWith("notesounds")) {
                continue;
            }
            if(row.matches("[0-9]{3,3}.*")) {
                int notePitch = Integer.parseInt(row.substring(0,3));
                FileHandle noteFile = Gdx.files.internal("sounds/" + row);
                if(!noteFile.exists()) {
                    continue;
                }
                Sound s = Gdx.audio.newSound(noteFile);
                int index = getIndex(notePitch);
                soundKeys[index] = notePitch;
                soundPitches[index] = 0;
                sounds.put(notePitch, s);
            }
            if(row.matches("f\\s*.*")) {
                String[] fill = row.split("\\s*.*|[\\+-]");
                int fillNote = Integer.parseInt(fill[1]);
                int fillWithNote = Integer.parseInt(fill[2]);
                int fillAndPitch = Integer.parseInt(fill[3]);
                if(row.contains("-")) {
                    fillAndPitch = -fillAndPitch;
                }
                int index = getIndex(fillNote);
                soundKeys[index] = fillWithNote;
                soundPitches[index] = fillAndPitch;
            }
        }

        //TODO fill missing keys and pitches


    }

	public void playNote(Note n) {
        long[] sound_instance_id = new long[n.count()];

        for(int i = 0; i < sound_instance_id.length; i++) {
            TransformedSound ts = getSound(n.absolutePitches[i] + 100 * n.relOct);
            Sound s = ts.sound;
            int p = ts.pitchTransform;
            if(p == 0) {
                sound_instance_id[i] = s.play();
            } else {
                sound_instance_id[i] = s.play(1.0f, ts.pitchVal, 0);
            }
        }
        n.ids=sound_instance_id;
        n.start = System.currentTimeMillis();

	}
	public void stopNote(Note n ){
		for(int i=0; i< n.ids.length; i++){
            TransformedSound tf = getSound(n.absolutePitches[i]);
            tf.sound.stop(n.ids[i]);
		}
	}
	@Override
	public void dispose() {

        for(Sound s : sounds.values()) {
            s.dispose();
        }
	}

    private class TransformedSound {
        Sound sound;
        //how many semitones
        int pitchTransform;
        //real number for play speed
        float pitchVal;
        public TransformedSound(Sound sound, int pitchTransform) {
            this.sound = sound;
            this.pitchTransform = pitchTransform;

            pitchVal = pitchTransform == 0 ? 0 : (float)Math.pow(2.0f, pitchTransform / 12.0f);
        }
    }
}
