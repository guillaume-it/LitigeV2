package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamMapping {
    public static String STREAM = "Stream #";
    private String Stream;
}
