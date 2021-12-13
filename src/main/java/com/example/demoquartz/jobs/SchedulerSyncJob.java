package com.example.demoquartz.jobs;

import com.example.demoquartz.service.JobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
public class SchedulerSyncJob extends QuartzJobBean {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.debug("");
        System.out.println(jobInfoService);
        jobInfoService.getCurrentSchedulerJobInfos();

        log.debug("");
    }
}
