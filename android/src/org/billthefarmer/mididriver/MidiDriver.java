////////////////////////////////////////////////////////////////////////////////
//
//  MidiDriver - An Android Midi Driver.
//
//  Copyright (C) 2013	Bill Farmer
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//  Bill Farmer	 william j farmer [at] yahoo [dot] co [dot] uk.
//
///////////////////////////////////////////////////////////////////////////////

package org.billthefarmer.mididriver;

public final class MidiDriver {
    private OnMidiStartListener listener;

    static {
        System.loadLibrary("midi");
    }

    public MidiDriver() {
    }

    public boolean start() {
        if (!init()) {
	        return false;
        }

        if (listener != null)
            listener.onMidiStart();

	    return true;
    }

    public void queueEvent(byte[] event) {
        write(event);
    }

    public void stop() {
        shutdown();
    }

    public void setOnMidiStartListener(OnMidiStartListener l) {
        listener = l;
    }

    public interface OnMidiStartListener {
        void onMidiStart();
    }

    private native boolean init();

    public native int[] config();

    public native boolean write(byte a[]);

    private native boolean shutdown();

}
