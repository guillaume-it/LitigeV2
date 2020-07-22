package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class InputDuration {
   private List<Duration> durationList = new ArrayList();
}
