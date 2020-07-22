package com.ruscassie.video.callable;


import com.ruscassie.video.ffprobe.EnumFFProbe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class CallableFfProbe implements Callable<Integer> {
    private InputStream inputStream;

    public CallableFfProbe(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Integer call() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String lineToPipe;
        boolean stream = false;
        Map<String, String> videoParams = new HashMap<>();
        try {

            while ((lineToPipe = bufferedReader.readLine()) != null) {

// TODO Le chemin d'acc�s sp�cifi� est introuvable.
                if (lineToPipe != null) {
                    // System.out.println("out "+lineToPipe);

                    if ("[/STREAM]".equals(lineToPipe)) {
                        stream = false;
                        System.out.println("END");
                    } else if (stream) {
                        String[] params = lineToPipe.split("=");
                        videoParams.put(params[0], params[1]);
                        if (params[0].equals("nb_frames")) {
                            System.out.println(params[0] + " " + params[1]);
                        }
                    } else if ("[STREAM]".equals(lineToPipe)) {
                        stream = true;
                    }

                }

            }
            String ngFrame = videoParams.get(EnumFFProbe.NB_FRAMES.toString());
            System.out.println("FIN Lecture CallFfProbe " + videoParams.size() + " " + EnumFFProbe.NB_FRAMES + "=" + ngFrame);
            if (ngFrame != null && !ngFrame.isEmpty()) {
                return Integer.parseInt(ngFrame);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
