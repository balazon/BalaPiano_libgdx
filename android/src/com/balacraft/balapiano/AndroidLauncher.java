package com.balacraft.balapiano;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.balacraft.balapiano.MyGdxPiano;

import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.billthefarmer.mididriver.MidiConstants;
import org.billthefarmer.mididriver.MidiDriver;

public class AndroidLauncher extends AndroidApplication {
	AndroidMidiPlayer midiPlayer;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		midiPlayer = new AndroidMidiPlayer();
		//Log.e("GDX_ANDROID", "CREATE");
		initialize(new MyGdxPiano(midiPlayer), config);

	}



	///////////////////////////////////////////////////////////////////////////
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		//Log.e("GDX_ANDROID", "START");
		//midiPlayer.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//Log.e("GDX_ANDROID", "STOP");
		//midiPlayer.stop();
	}

}
