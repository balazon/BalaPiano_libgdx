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
import java.util.TreeSet;

public class SoundPlayer implements Disposable{

    // the actual sounds that you have in the assets folder and also list it in config.txt
    private TreeMap<Integer, Sound> actualSounds;
    // one for every sound inside the range specified in config.txt
    private TransformedSound[] sounds;
    int range_min;
    int range_max;
    int default_octave;

    public int getDefaultOctave() {
        return default_octave;
    }


    int getIndex(int absolutePitch) {
        return absolutePitch - range_min;
    }

    TransformedSound getSound(int absolutePitch) {
        int index = getIndex(absolutePitch);
        return sounds[index];
    }
    //read settings from config file
	void loadSounds() {
        actualSounds = new TreeMap<Integer, Sound>();
        FileHandle confFile = Gdx.files.internal("config_scarce.txt");
        if(!confFile.exists() || confFile.isDirectory()) {
            System.out.println("Error : config.txt not found");
            return;
        }
        String config = confFile.readString();
        String soundsFolder = "";
        String[] rows = config.split("\\r?\\n");
        for(String row : rows) {
            if(row.startsWith("//") || row.equals("")) {
                continue;
            }
            if(row.startsWith("range")) {
                String[] range = row.split(":\\s*|\\s*-\\s*");
                range_min = Integer.parseInt(range[1]);
                range_max = Integer.parseInt(range[2]);
                sounds = new TransformedSound[getIndex(range_max) + 1];

                continue;
            }
            if(row.startsWith("default_octave")) {
                default_octave = Integer.parseInt(row.split(":\\s*")[1]);
                continue;
            }
            if(row.startsWith("notesounds_folder")) {
                soundsFolder = row.split(":\\s*")[1];

                continue;
            }
            if(row.matches("[0-9]+-.*")) {
                int notePitch = Integer.parseInt(row.split("-")[0]);
                FileHandle noteFile = Gdx.files.internal(soundsFolder + "/" + row);
                if(!noteFile.exists()) {
                    continue;
                }
                Sound s = Gdx.audio.newSound(noteFile);
                actualSounds.put(notePitch, s);

                int index = getIndex(notePitch);
                sounds[index] = new TransformedSound(s, 0);
            }
            if(row.matches("f\\s*.*")) {
                String[] fill = row.split("\\s+|[\\+-]");
                int fillNote = Integer.parseInt(fill[1]);
                int fillWithNote = Integer.parseInt(fill[2]);
                int fillAndPitch = Integer.parseInt(fill[3]);
                if(row.contains("-")) {
                    fillAndPitch = -fillAndPitch;
                }
                int index = getIndex(fillNote);
                sounds[index] = new TransformedSound(actualSounds.get(fillWithNote), fillAndPitch );

            }
        }


        //filling missing notes here (with closest)
        Integer[] keys = new Integer[actualSounds.size()];
        actualSounds.keySet().toArray(keys);

        for(int j = 0; j < getIndex(keys[0]); j++) {
            if(sounds[j] != null) {
                continue;
            }
            sounds[j] = new TransformedSound(actualSounds.get(keys[0]), j - getIndex(keys[0]));
        }
        for(int i = 0; i < keys.length - 1; i++) {
            int keyA = keys[i];
            int keyB = keys[i + 1];
            int indexA = getIndex(keyA);
            int indexB = getIndex(keyB);
            int half = (indexA + indexB) / 2;
            boolean foundFill = false;
            for(int j = indexA + 1; j < half; j++) {
                if(sounds[j] != null) {
                    if(sounds[j].sound == actualSounds.get(keyB)) {
                        foundFill = true;
                    }
                    continue;
                }
                if(!foundFill) {
                    sounds[j] = new TransformedSound(actualSounds.get(keyA), j - indexA);
                } else {
                    sounds[j] = new TransformedSound(actualSounds.get(keyB), j - indexB);
                }
            }
            foundFill = false;
            for(int j = indexB - 1; j >= half; j--) {
                if(sounds[j] != null) {
                    if(sounds[j].sound == actualSounds.get(keyA)) {
                        foundFill = true;
                    }
                    continue;
                }
                if(!foundFill) {
                    sounds[j] = new TransformedSound(actualSounds.get(keyB), j - indexB);
                } else {
                    sounds[j] = new TransformedSound(actualSounds.get(keyA), j - indexA);
                }
            }
        }
        int lastActualSoundIndex = getIndex(keys[keys.length - 1]);
        for(int j = sounds.length - 1; j > lastActualSoundIndex; j--) {
            if(sounds[j] != null) {
                continue;
            }
            sounds[j] = new TransformedSound(actualSounds.get(keys[keys.length - 1]), j - lastActualSoundIndex);
        }


    }

	public void playNote(Note n) {
        long[] sound_instance_id = new long[n.count()];

        for(int i = 0; i < sound_instance_id.length; i++) {
            TransformedSound ts = getSound(n.absolutePitches[i] + 12 * n.relOct);
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
        for(Sound s : actualSounds.values()) {
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
