package com.example.Spring_Batch_Quartz_Demo.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomTasklet implements Tasklet {

  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    System.out.println("CustomTasklet start..");
    String str = "hello world";
    String upstr = str.toUpperCase();
    System.out.println(upstr);
    System.out.println("CustomTasklet done..");
    return RepeatStatus.FINISHED;
  }
}


