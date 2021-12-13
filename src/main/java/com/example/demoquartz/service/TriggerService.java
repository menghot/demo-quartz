package com.example.demoquartz.service;

import org.quartz.SchedulerException;

/**
 * @author Simon
 */
public interface TriggerService {

    void pauseTrigger(String jobKey, String group) throws SchedulerException;

    void resumeTrigger(String jobKey, String group) throws SchedulerException;
}
