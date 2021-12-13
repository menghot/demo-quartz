package com.example.demoquartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Simon
 */
@Slf4j
public class SimpleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleJob Start................, " + context.getTrigger().getKey().getName());

        for (int i = 0; i < 5; i++) {
            log.info("Counting - {}", i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("SimpleJob End................");
    }
}
