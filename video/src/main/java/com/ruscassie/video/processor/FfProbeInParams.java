package com.ruscassie.video.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FfProbeInParams {
    Path videoIn;
    Path ffProbe;
}
