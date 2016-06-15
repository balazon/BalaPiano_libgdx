package com.balacraft.balapiano.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.balacraft.balapiano.MyGdxPiano;
import com.balacraft.balapiano.soundengine.RealSoundPlayer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "balazon's piano";
        config.width = 1280;
        config.height = 720;
		new LwjglApplication(new MyGdxPiano(new DesktopMidiPlayer()), config);
	}
}
