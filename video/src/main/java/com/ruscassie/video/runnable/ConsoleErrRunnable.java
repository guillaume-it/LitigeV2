package com.ruscassie.video.runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ConsoleErrRunnable implements Runnable {
    private InputStream inputStream;

    public  ConsoleErrRunnable(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        BufferedReader bufferedReaderError = new BufferedReader(new InputStreamReader(inputStream));

        String errorToPipe = null;

        try {

            while ((errorToPipe= bufferedReaderError.readLine())!=null){
                    System.err.println("err: "+errorToPipe);
                   }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
