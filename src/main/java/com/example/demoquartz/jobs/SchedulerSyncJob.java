package com.example.demoquartz.jobs;

import com.example.demoquartz.enitiy.JobInfo;
import com.example.demoquartz.service.JobInfoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
@DisallowConcurrentExecution
public class SchedulerSyncJob extends QuartzJobBean {

    @Autowired
    private JobInfoService jobInfoService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SchedulerSyncJob begin... ");
        List<JobInfo> jobInfos = jobInfoService.getCurrentSchedulerJobInfos();
        for (JobInfo jobInfo : jobInfos) {
            JobKey jobKey = new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
            try {
                if (schedulerFactoryBean.getScheduler().getJobDetail(jobKey) == null) {
                    addJobAndTrigger(jobInfo);
                } else {
                    updateJobAndTrigger(jobInfo);
                }
            } catch (SchedulerException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("SchedulerSyncJob end");
    }

    public void addJobAndTrigger(JobInfo jobInfo) {
        //TODO add job & trigger
    }

    public void updateJobAndTrigger(JobInfo jobInfo) {
        //TODO: update job / trigger
    }
}
