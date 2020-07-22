package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Frame {
    private Integer frame;
    private Double fps;
    private Double q;
    private String Lsize;
    private String time;
    private String bitrate;
    private String speed;

    public Frame(String lineToPipe) {
        //example frame= 7465 fps= 42 q=0.0 Lsize=   15885kB time=00:04:58.56 bitrate= 435.9kbits/s speed=1.68x
        final String cleanLine = lineToPipe.replaceAll("=(\\s)+", "=");
        final String[] params = cleanLine.split(" ");
        this.frame = Integer.parseInt(params[0].split("=")[1]);
        this.fps = Double.parseDouble(params[1].split("=")[1]);
        this.q = Double.parseDouble(params[2].split("=")[1]);
        this.Lsize = params[3].split("=")[1];
        this.time = params[4].split("=")[1];
        this.bitrate = params[5].split("=")[1];
        this.speed = params[6].split("=")[1];
    }
}
