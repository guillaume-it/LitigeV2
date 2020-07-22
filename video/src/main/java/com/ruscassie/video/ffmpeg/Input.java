package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Input {
    public static String METADATA = "  Metadata";
    public static String DURATION = "  Duration";
    private InputMetadata metadata;
    private InputDuration duration;
}
