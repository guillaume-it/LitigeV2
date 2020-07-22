package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FfmpegVersion {
    public static String VERSION="version";
    public static String BUILT="built";
    public static String CONFIGURATION="configuration";
    public static String LIBAVUTIL="libavutil";
    public static String LIBAVCODEC="libavcodec";
    public static String LIBAVFORMAT="libavformat";
    public static String LIBAVDEVICE="libavdevice";
    public static String LIBAVFILTER="libavfilter";
    public static String LIBSWSCALE="libswscale";
    public static String LIBSWRESAMPLE="libswresample";
    public static String LIBPOSTPROC="libpostproc";



    private String version;
    private String built;
    private String configuration;
    private String libavutil;
    private String libavcodec;
    private String libavformat;
    private String libavdevice;
    private String libavfilter;
    private String libswscale;
    private String libswresample;
    private String libpostproc;
}
