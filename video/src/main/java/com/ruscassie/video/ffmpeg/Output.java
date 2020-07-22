package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output {
    public static String METADATA = "  Metadata";
    private String detail;
    private OutPutMetadata metadata;
}
