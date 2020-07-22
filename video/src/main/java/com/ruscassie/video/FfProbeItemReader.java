package com.ruscassie.video;

import com.ruscassie.video.processor.FfProbeInParams;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FfProbeItemReader implements ItemReader<FfProbeInParams> {
    @Override
    public FfProbeInParams read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        final Path file = Paths.get("ffmpeg/bin/ffprobe.exe");
        final Path videoIn = Paths.get("ffmpeg/bin/test.mp4");
        return new FfProbeInParams(file,videoIn);
    }
}
