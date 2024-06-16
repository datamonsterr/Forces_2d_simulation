package com.application.components.controller;

import javax.swing.Timer;
import java.awt.event.ActionListener;

import com.application.components.object.MyObject;

public class Controller {
    public static final int FPS = 60;
    private static Timer timer;

    private static MyObject obj;
    private static double time = 0.0d; // measure in seconds

    public static void setObj(MyObject obj) {
        Controller.obj = obj;
    }

    public static MyObject getObj() {
        return obj;
    }

    public static double getVelocity() {
        return obj.getCurVelocity(time);
    }

    public static double getTime() {
        return time;
    }

    public static void setTime(double currentTime) {
        Controller.time = currentTime;
    }

    public static void tick() {
        time += 1.0d / FPS;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void pauseTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public static void resetTimer() {
        time = 0.0d;
        if (timer != null) {
            timer.stop();
        }
    }

    public static void resumeTimer() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    public static void startTimer() {
        if (timer != null) {
            timer.start();
        } else {
            timer = new Timer(1000 / FPS, null);
            timer.start();
        }
    }

    public static void createTimer(ActionListener listener) {
        timer = new Timer(1000 / FPS, listener);
        timer.stop();
    }

}
