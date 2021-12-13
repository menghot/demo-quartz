package com.example.demoquartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @author Simon
 */
@Slf4j
@Service
public class TriggerServiceImpl implements TriggerService {

    private Logger logger = LoggerFactory.getLogger(TriggerServiceImpl.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void pauseTrigger(String triggerName, String group) throws SchedulerException {
        if (logger.isDebugEnabled()) {
            logger.debug("pause trigger name:  {}, group: {}", triggerName, group);
        }
        TriggerKey triggerKey = new TriggerKey(triggerName, group);
        Trigger trigger = schedulerFactoryBean.getScheduler().getTrigger(triggerKey);
        if (trigger != null) {
            schedulerFactoryBean.getScheduler().pauseTrigger(triggerKey);
            if (logger.isDebugEnabled()) {
                logger.debug("trigger paused success, trigger name:  {}, group: {}", triggerName, group);
            }
        } else {
            throw new SchedulerException("Trigger not found, triggerKey:" + triggerKey + ", group: " + group);
        }
    }

    @Override
    public void resumeTrigger(String triggerName, String group) throws SchedulerException {
        if (logger.isDebugEnabled()) {
            logger.debug("resume trigger name:  {}, group: {}", triggerName, group);
        }
        TriggerKey triggerKey = new TriggerKey(triggerName, group);
        Trigger trigger = schedulerFactoryBean.getScheduler().getTrigger(triggerKey);
        if (trigger != null) {
            schedulerFactoryBean.getScheduler().resumeTrigger(triggerKey);
            if (logger.isDebugEnabled()) {
                logger.debug("trigger resumed success, trigger name:  {}, group: {}", triggerName, group);
            }
        } else {
            throw new SchedulerException("Trigger not found, triggerKey:" + triggerKey + ", group: " + group);
        }
    }
}
