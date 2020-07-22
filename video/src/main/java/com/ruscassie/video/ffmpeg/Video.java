package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    public static String VIDEO = "video";
    public static String AUDIO = "audio";
    public static String SUBTITLE = "subtitle";
    public static String OTHER_STREAMS = "other streams";
    public static String GLOBAL_HEADERS = "global headers";
    public static String MUXING_OVERHEAD = "muxing overhead";

    private String video;
    private String audio;
    private String subtitle;
    private String otherStreams;
    private String globalHeaders;
    private String muxingOverhead;
}
