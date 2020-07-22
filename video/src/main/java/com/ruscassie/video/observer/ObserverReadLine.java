package com.ruscassie.video.observer;

import com.ruscassie.video.ffmpeg.Frame;

public class ObserverReadLine implements Observer<Frame> {

    @Override
    public void update(Frame data) {
        System.out.println("Frame: " + data.getFrame());
    }
}