package com.example.demoquartz.tasks;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerStatusChecker {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void check() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        log.debug("----->" + scheduler.getMetaData());
    }
}
