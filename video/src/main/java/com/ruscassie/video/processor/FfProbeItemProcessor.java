package com.ruscassie.video.processor;

import com.ruscassie.video.callable.CallableFfProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FfProbeItemProcessor implements ItemProcessor<FfProbeInParams, Integer> {

  private static final Logger log = LoggerFactory.getLogger(FfProbeItemProcessor.class);

  @Override
  public Integer process(final FfProbeInParams ffProbeInParams) throws Exception {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder();
      Process proc =   processBuilder.command("cmd.exe","/c" ,ffProbeInParams.getFfProbe().toFile().getCanonicalPath()+ " -select_streams v -show_streams "+ffProbeInParams.getVideoIn().toString())
              .redirectOutput(ProcessBuilder.Redirect.INHERIT)
              .redirectErrorStream(true)
               .start();

      Integer nbFrame = callCallableNfFrame(proc);
      System.out.println("Nb frames "+nbFrame);
      return nbFrame;


    } catch (Exception e) {
      // deal with e here
      e.printStackTrace();
    }

    return null;
  }
  public  Integer callCallableNfFrame(Process process) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    CallableFfProbe callableFfProbe = new CallableFfProbe(process.getInputStream());
    Future<Integer> future = executorService.submit(callableFfProbe);
    while (!future.isDone()){

    }
    executorService.shutdown();
    return future.get();
   }
}