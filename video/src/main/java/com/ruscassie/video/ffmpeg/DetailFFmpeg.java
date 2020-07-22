package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailFFmpeg {
    public static String OUTPUT ="Output";
    public static String INPUT ="Input";
    public static String STREAM_MAPPING = "Stream mapping";
    public static String FFMPEG_VERSION = "ffmpeg version";
    public static String FRAME = "frame=";
    public static String VIDEO = "video:";
    private String version;
    private Input input;
    private Output output;
    private StreamMapping streamMapping;
    private Frame frame;
    private Video video;
    private Integer exitCode;
}
