package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Duration {
    private String stream;
    private Metadata metadata;
}
