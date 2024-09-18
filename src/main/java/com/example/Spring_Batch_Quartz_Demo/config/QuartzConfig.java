package com.example.Spring_Batch_Quartz_Demo.config;

import com.example.Spring_Batch_Quartz_Demo.job.CustomQuartzJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private JobLocator jobLocator;

  @Bean
  public JobDetail jobDetail() {
    //Set Job data map
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("jobName", "customJob");

    return JobBuilder.newJob(CustomQuartzJob.class)
        .withIdentity("customJob")
        .setJobData(jobDataMap)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger jobTrigger() {
//    SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
//        .simpleSchedule()
//        .withIntervalInSeconds(10)
//        .repeatForever();

    return TriggerBuilder
        .newTrigger()
        .forJob(jobDetail())
        .withIdentity("jobTrigger")
        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
        .build();
  }
   @Bean
    public HolidayCalendar holidayCalendar() {
        HolidayCalendar calendar = new HolidayCalendar();
        calendar.addExcludedDate(new java.util.Date());
        return calendar;
    }

    @Bean
    public Scheduler scheduler(Trigger cronTrigger, JobDetail jobDetail, HolidayCalendar holidayCalendar) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.addCalendar("holidays", holidayCalendar, false, false);
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return scheduler;
    }
}



