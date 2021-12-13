package com.example.demoquartz;

import com.example.demoquartz.jobs.SchedulerSyncJob;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableKnife4j
@EnableOpenApi
@SpringBootApplication
public class DemoQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoQuartzApplication.class, args);
    }

    /**
     * Start a scheduler to sync jobs from [job_info] to quartz engine, which will create or update
     * quartz jobs and triggers
     * <p>
     * The synchronization job will run every 15 seconds by default
     *
     * @param schedulerFactoryBean
     * @return
     */
    @Bean
    public ApplicationRunner newRunner(SchedulerFactoryBean schedulerFactoryBean) {
        return args -> {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey("syncJob", "DEFAULT");
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                jobDetail = JobBuilder.newJob()
                        .withIdentity(jobKey)
                        .storeDurably()
                        .ofType(SchedulerSyncJob.class)
                        .build();
                scheduler.addJob(jobDetail, true);
            }

            TriggerKey triggerKey = new TriggerKey("syncJob_trigger", "DEFAULT");
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {

                CronScheduleBuilder triggerBuilder = CronScheduleBuilder
                        .cronSchedule("*/15 * * * * ?")
                        .withMisfireHandlingInstructionDoNothing();

                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .forJob(jobKey)
                        .startNow()
                        .withSchedule(triggerBuilder)
                        .build();

                scheduler.scheduleJob(trigger);
            }
        };
    }
}
