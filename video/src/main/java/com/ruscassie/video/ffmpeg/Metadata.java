package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Metadata {
    public static String CREATION_TIME = "creation_time";
    public static String HANDLER_NAME = "handler_name";
    public static String ENCODER = "encoder";
    private String creationTime;
    private String handlerName;
    private String encoder;
}
