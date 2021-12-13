package com.example.demoquartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @author Simon
 */
@Slf4j
@Service
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void pauseTrigger(String triggerName, String group) throws SchedulerException {
        if (log.isDebugEnabled()) {
            log.debug("pause trigger name:  {}, group: {}", triggerName, group);
        }
        TriggerKey triggerKey = new TriggerKey(triggerName, group);
        Trigger trigger = schedulerFactoryBean.getScheduler().getTrigger(triggerKey);
        if (trigger != null) {
            schedulerFactoryBean.getScheduler().pauseTrigger(triggerKey);
            if (log.isDebugEnabled()) {
                log.debug("trigger paused success, trigger name:  {}, group: {}", triggerName, group);
            }
        } else {
            throw new SchedulerException("Trigger not found, triggerKey:" + triggerKey + ", group: " + group);
        }
    }

    @Override
    public void resumeTrigger(String triggerName, String group) throws SchedulerException {
        if (log.isDebugEnabled()) {
            log.debug("resume trigger name:  {}, group: {}", triggerName, group);
        }
        TriggerKey triggerKey = new TriggerKey(triggerName, group);
        Trigger trigger = schedulerFactoryBean.getScheduler().getTrigger(triggerKey);
        if (trigger != null) {
            schedulerFactoryBean.getScheduler().resumeTrigger(triggerKey);
            if (log.isDebugEnabled()) {
                log.debug("trigger resumed success, trigger name:  {}, group: {}", triggerName, group);
            }
        } else {
            throw new SchedulerException("Trigger not found, triggerKey:" + triggerKey + ", group: " + group);
        }
    }
}
