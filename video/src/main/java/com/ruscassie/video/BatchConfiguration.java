package com.ruscassie.video;

import com.ruscassie.video.processor.FfProbeItemProcessor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public FfProbeItemReader reader() {
    return new FfProbeItemReader();
  }

  @Bean
  public FfProbeItemProcessor processor() {
    return new FfProbeItemProcessor();
  }

  @Bean
  public FfProbeItemWriter writer() {
    return new FfProbeItemWriter ();
  }

//  @Bean
//  public Step step1(FfProbeItemWriter writer) {
//    return stepBuilderFactory.get("step1")
//            .<FfProbeInParams, Integer> chunk(1)
//            .reader(reader())
//            .processor(processor())
//            .writer(writer)
//            .build();
//  }
  @Bean
public   TaskletStep step1() throws IOException {
      Tasklet tasklet = (contribution, context) -> {
      FFmpeg ffmpeg = new FFmpeg("C:\\Users\\frup43860\\Documents\\Git\\LitigeV2\\video\\ffmpeg\\bin\\ffmpeg");
      FFprobe ffprobe = new FFprobe("C:\\Users\\frup43860\\Documents\\Git\\LitigeV2\\video\\ffmpeg\\bin\\ffprobe");

      
      FFmpegBuilder builder = new FFmpegBuilder()

              .setInput("C:\\Users\\frup43860\\Documents\\Git\\LitigeV2\\video\\ffmpeg\\bin\\test.mp4")     // Filename, or a FFmpegProbeResult
              .overrideOutputFiles(true) // Override the output if it exists

              .addOutput("C:\\Users\\frup43860\\Documents\\Git\\LitigeV2\\video\\ffmpeg\\bin\\output.mp4")   // Filename for the destination
              .setFormat("mp4")        // Format is inferred from filename, or can be set
             // .setTargetSize(250_000)  // Aim for a 250KB file

             // .disableSubtitle()       // No subtiles

              .setAudioChannels(1)         // Mono audio
              .setAudioCodec("aac")        // using the aac codec
              .setAudioSampleRate(48_000)  // at 48KHz
              .setAudioBitRate(32768)      // at 32 kbit/s

              .setVideoCodec("libx264")     // Video using x264
              .setVideoFrameRate(24, 1)     // at 24 frames per second
              .setVideoResolution(640, 480) // at 640x480 resolution

            //  .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
              .done();

      FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
// Run a one-pass encode
   //       builder.
     FFmpegJob job = executor.createJob(builder);
          job.run();

// Or run a two-pass encode (which is better quality at the cost of being slower)
//      executor.createTwoPassJob(builder).run();

          return null;
      };
   return stepBuilderFactory.get("step1").tasklet(tasklet).build();
  }
//  private TaskletStep step1() {
//    Tasklet tasklet = (contribution, context) -> {
//      log.info("This is from tasklet step with parameter ->"
//              + context.getStepContext().getJobParameters().get("message"));
//      return RepeatStatus.FINISHED;
//    };
//    return stepBuilderFactory.get("step1").tasklet(tasklet).build();
//  }
//  @Bean
//  public Job importFfProbe(JobCompletionNotificationListener listener, Step step1) {
//    return jobBuilderFactory.get("importFfProbe")
//            .start(step1)
//            .build();
//  }
@Bean
public Job job1() throws IOException {
  return jobBuilderFactory.get("job1")
          .incrementer(new RunIdIncrementer())
          .start(step1()).build();
}}
