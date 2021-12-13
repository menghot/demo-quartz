package com.example.demoquartz.service;

import java.util.List;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface JobService {

    List<Trigger> getJobTriggers(String jobKey, String group) throws SchedulerException;
}
