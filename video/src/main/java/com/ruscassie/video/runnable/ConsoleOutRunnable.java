package com.ruscassie.video.runnable;

import com.ruscassie.video.ffmpeg.*;
import com.ruscassie.video.observer.Observer;
import com.ruscassie.video.observer.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleOutRunnable implements Runnable, Subject<Frame> {
    private InputStream inputStream;
    private List<Observer<Frame>> observers = new ArrayList<>();

    public ConsoleOutRunnable(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String lineToPipe;

        try {
            DetailFFmpeg detailFFmpeg = new DetailFFmpeg();

            while ((lineToPipe = bufferedReader.readLine()) != null) {
                if (lineToPipe.startsWith(DetailFFmpeg.FFMPEG_VERSION)) {
                    detailFFmpeg.setVersion(lineToPipe);
                   // System.out.println("Version " + lineToPipe);
                } else if (lineToPipe.startsWith(DetailFFmpeg.INPUT)) {
                    detailFFmpeg.setInput(new Input());
                  //  System.out.println("Input: " + lineToPipe);
                } else if (lineToPipe.startsWith(DetailFFmpeg.STREAM_MAPPING)) {
                    detailFFmpeg.setStreamMapping(new StreamMapping());
                  //  System.out.println("StreamMapping: " + lineToPipe);
                } else if (lineToPipe.startsWith(DetailFFmpeg.OUTPUT)) {
                    detailFFmpeg.setOutput(new Output());
       //            System.out.println("Output: " + lineToPipe);
                } else if (isOutput(detailFFmpeg,lineToPipe)) {
             //       System.out.println("Out partie: " + lineToPipe);
                } else if (isFrameOrVideo(detailFFmpeg,lineToPipe)) {
                    if (lineToPipe.startsWith(DetailFFmpeg.FRAME)) {
                        final Frame frame = new Frame(lineToPipe);
                        detailFFmpeg.setFrame(frame);
                        notifyObservers(frame);
                    } else if (lineToPipe.startsWith(DetailFFmpeg.VIDEO)) {
                        //System.out.println("Out video: " + lineToPipe);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOutput(DetailFFmpeg detailFFmpeg, String lineToPipe) {
        return detailFFmpeg.getVersion() != null && detailFFmpeg.getInput() != null && detailFFmpeg.getFrame() == null && detailFFmpeg.getVideo() == null && detailFFmpeg.getExitCode() == null && !lineToPipe.startsWith(DetailFFmpeg.FRAME);
    }

    public static boolean isFrameOrVideo(DetailFFmpeg detailFFmpeg, String lineToPipe) {
        return detailFFmpeg.getVersion() != null && detailFFmpeg.getInput() != null && detailFFmpeg.getOutput() != null && detailFFmpeg.getVideo() == null && detailFFmpeg.getExitCode() == null && !lineToPipe.startsWith(DetailFFmpeg.VIDEO);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Frame frame) {
        observers.forEach( frameObserver -> frameObserver.update(frame));
    }

}
