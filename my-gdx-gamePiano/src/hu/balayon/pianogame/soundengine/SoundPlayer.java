package hu.balayon.pianogame.soundengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundPlayer implements Disposable{
	float[] pitches_float;
	int[] pitches_int;
	
	private Sound[] sound;
	
	public void setSounds(String[] C) {
		sound = new Sound[C.length];
		for(int i= 0; i< C.length; i++) {
			sound[i] =Gdx.audio.newSound(Gdx.files.internal(C[i]));
		}
	}
	private int index(int pitch) {
		int res = (pitch+24)/12;
		return res;
	}
	public void playNote(Note n) {
		pitches_int= n.pitch;
		pitches_float = new float[pitches_int.length];
		int[] p = new int[pitches_int.length];
		
		for(int i= 0; i< pitches_int.length; i++) {
			p [i]= pitches_int[i]+n.relOct*12;
			int mod = pitches_int[i]%12;if(mod < 0) mod+=12;
			pitches_float[i]= (float)Math.pow(2, (mod)/12f);
		}
		long[] sound_instance_id = new long[pitches_int.length];
		
		if(!n.loop) {
			for(int i=0;i<pitches_int.length;i++){
				sound_instance_id[i] =(int) sound[index(p[i])].play(1, pitches_float[i], 0);
			}
		}else {
			for(int i=0;i<pitches_int.length;i++){
				sound_instance_id[i] =(int) sound[index(p[i])].loop(1, pitches_float[i], 0);
			}
		}
		n.ids=sound_instance_id;
		n.start = System.currentTimeMillis();
	}
	public void stopNote(Note n ){
		for(int i=0; i< n.ids.length; i++){
			sound[index(n.pitch[i]+n.relOct*12)].stop(n.ids[i]);
		}
	}
	@Override
	public void dispose() {
		pitches_float = null;
		pitches_int = null;
		for(int i = 0; i<sound.length; i++) sound[i].dispose();
	}
}
