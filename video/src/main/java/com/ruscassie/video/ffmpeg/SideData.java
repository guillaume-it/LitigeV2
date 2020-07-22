package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SideData {
    public static String CPB = "      cpb";
    private String cpb;
}
