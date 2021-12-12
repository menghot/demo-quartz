package com.example.demoquartz.service.impl;

import com.example.demoquartz.enitiy.JobInfo;
import com.example.demoquartz.repository.JobInfoRepository;
import com.example.demoquartz.service.JobSchedulerService;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Simon
 */
@Slf4j
@Transactional
@Service
public class JobSchedulerServiceImpl implements JobSchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobInfoRepository schedulerRepository;


//    @Autowired
//    private JobScheduleCreator scheduleCreator;

    @Override
    public void startAllSchedulers() {
        List<JobInfo> jobInfoList = schedulerRepository.findAll();
        if (jobInfoList != null) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            jobInfoList.forEach(jobInfo -> {
                try {
                    JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                            .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
                    if (!scheduler.checkExists(jobDetail.getKey())) {
//                        Trigger trigger;
//                        jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
//                                false, context, jobInfo.getJobName(), jobInfo.getJobGroup());
//
//                        if (jobInfo.getCronJob() && CronExpression.isValidExpression(jobInfo.getCronExpression())) {
//                            trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
//                                    jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
//                        } else {
//                            trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
//                                    jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
//                        }
//
//                        scheduler.scheduleJob(jobDetail, trigger);

                    }
                } catch (ClassNotFoundException e) {
                    log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
                } catch (SchedulerException e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
    }

    @Override
    public void scheduleNewJob(JobInfo jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
            if (!scheduler.checkExists(jobDetail.getKey())) {
//                jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
//                        false, context, jobInfo.getJobName(), jobInfo.getJobGroup());
//
//                Trigger trigger;
//                if (jobInfo.getCronJob()) {
//                    trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(), jobInfo.getCronExpression(),
//                            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
//                } else {
//                    trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(), jobInfo.getRepeatTime(),
//                            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
//                }
//
//                scheduler.scheduleJob(jobDetail, trigger);
//                schedulerRepository.save(jobInfo);
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateScheduleJob(JobInfo jobInfo) {
        Trigger newTrigger= TriggerBuilder.newTrigger().build();
        if (jobInfo.getCronJob()) {
//            newTrigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(), jobInfo.getCronExpression(),
//                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
//            newTrigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(), jobInfo.getRepeatTime(),
//                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        try {
            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), newTrigger);
            schedulerRepository.save(jobInfo);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public boolean unScheduleJob(String jobName) {
        try {
            return schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
        } catch (SchedulerException e) {
            log.error("Failed to un-schedule job - {}", jobName, e);
            return false;
        }
    }

    @Override
    public boolean deleteJob(JobInfo jobInfo) {
        try {
            return schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean pauseJob(JobInfo jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean resumeJob(JobInfo jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean startJobNow(JobInfo jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }
}
