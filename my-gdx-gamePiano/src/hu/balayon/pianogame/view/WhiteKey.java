package hu.balayon.pianogame.view;

import hu.balayon.pianogame.soundengine.Note;
import hu.balayon.pianogame.soundengine.SoundSystem;

import com.badlogic.gdx.math.Rectangle;

public class WhiteKey extends PianoKey{
	protected Rectangle r2;
	
	public WhiteKey(Note n,SoundSystem ss){
		super(n,ss);
	}
	public boolean contains(int x, int y) {
		return (r1.contains(x, y) || r2.contains(x, y));
	}
	
	@Override
	public void resize(Rectangle r[]) {
		super.resize(r);
		r2.set(r[1]);
		
	}
	@Override
	public Rectangle[] getRects() {
		Rectangle[] res = new Rectangle[2];
		res[0] = r1;
		res[1] = r2;
		return res;
	}
	
}
