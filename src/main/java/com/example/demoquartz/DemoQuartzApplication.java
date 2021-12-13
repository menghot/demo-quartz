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

    @Bean
    public ApplicationRunner newRunner(SchedulerFactoryBean schedulerFactoryBean) {
        return args -> {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey("syncJob");
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                jobDetail = JobBuilder.newJob()
                        .withIdentity(jobKey)
                        .storeDurably()
                        .ofType(SchedulerSyncJob.class)
                        .build();
                scheduler.addJob(jobDetail, true);
            }

            TriggerKey triggerKey = new TriggerKey("syncJob");

            Trigger trigger =  scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .forJob(jobKey)
                        .startNow()
                        .withSchedule(CronScheduleBuilder
                                .cronSchedule("*/5 * * * * ?")
                                .withMisfireHandlingInstructionDoNothing())
                        .build();

                scheduler.scheduleJob(trigger);
            }


        };
    }
}
