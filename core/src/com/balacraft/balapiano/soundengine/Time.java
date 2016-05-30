package com.balacraft.balapiano.soundengine;


public class Time {
    private static Time instance = new Time();

    long startTime;
    long currentTime;
    long lastTime;
    long deltaTime;
    long elapsedTime;
    private Time() {
        startTime = System.currentTimeMillis();
        currentTime = startTime;
        lastTime = startTime;
        deltaTime = 0;
        elapsedTime = 0;
    }
    private void updateTime() {
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        deltaTime = currentTime - lastTime;
        elapsedTime = currentTime - startTime;
    }
    public static void update() {
        instance.updateTime();
    }
    static long current() {
        return instance.currentTime;
    }

    static long delta() {
        return instance.deltaTime;
    }

}
