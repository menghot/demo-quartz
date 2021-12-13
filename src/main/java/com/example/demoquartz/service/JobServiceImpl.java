package com.example.demoquartz.service;

import java.util.List;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService{


    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public List<Trigger> getJobTriggers(String jobKey, String group) throws SchedulerException {

        List<Trigger> triggers = (List<Trigger>) schedulerFactoryBean.getScheduler().getTriggersOfJob(new JobKey(jobKey, group));
        return triggers;
    }

}
