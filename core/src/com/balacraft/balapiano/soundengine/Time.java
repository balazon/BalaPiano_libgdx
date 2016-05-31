package com.balacraft.balapiano.soundengine;


public class Time {
    private static Time instance = new Time();

    long startTime;
    long currentTime;

    long deltaTime;

	boolean paused;
	long pausedTimeSum;
	long timeAtPause;


    private Time() {
        startTime = System.currentTimeMillis();
        currentTime = 0;

        deltaTime = 0;
	    pausedTimeSum = 0;
    }

    private void updateTime() {
	    if(paused) {
		    return;
	    }
        long lastTime = currentTime;
        currentTime = System.currentTimeMillis() - startTime - pausedTimeSum;
        deltaTime = currentTime - lastTime;
    }
    public static void update() {
        instance.updateTime();
    }

	private void pauseTime(boolean value) {
		paused = value;
		if(value) {
			timeAtPause = System.currentTimeMillis();
			deltaTime = 0;
		} else {
			pausedTimeSum += System.currentTimeMillis() - timeAtPause;
		}
	}
    public static void paused(boolean value) {
		instance.pauseTime(value);
    }

    static long current() {
        return instance.currentTime;
    }

    static long delta() {
        return instance.deltaTime;
    }

}
