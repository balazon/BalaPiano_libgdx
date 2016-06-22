package com.balacraft.balapiano;

import android.os.Bundle;
import android.util.Log;

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
