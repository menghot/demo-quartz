package com.example.demoquartz;

import com.example.demoquartz.jobs.SimpleJob;
import java.util.Date;
import java.util.List;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.listeners.BroadcastTriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@SpringBootApplication
public class DemoQuartzApplication {



    public static void main(String[] args) {
        SpringApplication.run(DemoQuartzApplication.class, args);
    }

    @Bean
    public ApplicationRunner newRunner(SchedulerFactoryBean schedulerFactoryBean) {
        return args -> {

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey("simpleJob");
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (jobDetail == null) {
                jobDetail = JobBuilder.newJob(
                                (Class<? extends QuartzJobBean>) Class.forName(
                                        "com.example.demoquartz.jobs.SimpleJob"))
                        .withIdentity("simpleJob")
                        .storeDurably()
                        .usingJobData("name", "simon")
                        .usingJobData("age", "18")
                        .build();

                scheduler.addJob(jobDetail, true);
            }

            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            if (triggers.isEmpty()) {
				Trigger trigger = TriggerBuilder.newTrigger()
						.withIdentity(jobKey.getName() + "_simpleTrigger")
						.forJob(jobKey)
						.startNow()
						.withSchedule(SimpleScheduleBuilder
								.simpleSchedule()
								.withIntervalInSeconds(15)
								.repeatForever())
						.build();
				scheduler.scheduleJob(trigger);
			}




//			for (String groupName : scheduler.getJobGroupNames()) {
//				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
//					String jobName = jobKey.getName();
//					String jobGroup = jobKey.getGroup();
//
//					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
//					if (triggers.isEmpty()) {
//						//JobDetail jobDetail =  scheduler.getJobDetail(jobKey);
//						Trigger trigger = TriggerBuilder.newTrigger()
//								.withIdentity("trigger")
//								.forJob(jobKey)
//								.startNow()
//								.withSchedule(SimpleScheduleBuilder
//										.simpleSchedule()
//										.withIntervalInSeconds(15)
//										.repeatForever())
//								.build();
//						scheduler.scheduleJob(trigger);
//
//					} else {
//						Date nextFireTime = triggers.get(0).getNextFireTime();
//						System.out.println("[jobName] : " + jobName + " [groupName] : "
//								+ jobGroup + " - nextFireTime - " + nextFireTime);
//					}
//				}
//			}

                //schedulerFactoryBean.getScheduler().addJob(jobDetail, true);
                //schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);

                //System.out.println("job trigger size: " + schedulerFactoryBean.getScheduler().getTriggersOfJob(new JobKey("simpleJob")).size());
                //schedulerFactoryBean.getScheduler().addJob(jobDetail, true);
                //schedulerFactoryBean.getScheduler().triggerJob(new JobKey("simpleJob", "SIMPLE"));
                //schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey("simpleJob","SIMPLE"));
            };
        }

        public JobKey runJob (Scheduler scheduler, String jobName) throws SchedulerException {
            // if you don't call startAt() then the current time (immediately) is assumed.
            Trigger runOnceTrigger = TriggerBuilder.newTrigger().build();
            JobKey jobKey = new JobKey(jobName);
            JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity(jobKey).build();
            scheduler.scheduleJob(job, runOnceTrigger);
            return jobKey;
        }
    }
