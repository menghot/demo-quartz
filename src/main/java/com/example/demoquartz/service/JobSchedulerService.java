package com.example.demoquartz.service;

import com.example.demoquartz.enitiy.JobInfo;

/**
 * @author Chamith
 */
public interface JobSchedulerService {

    void startAllSchedulers();

    void scheduleNewJob(JobInfo jobInfo);

    void updateScheduleJob(JobInfo jobInfo);

    boolean unScheduleJob(String jobName);

    boolean deleteJob(JobInfo jobInfo);

    boolean pauseJob(JobInfo jobInfo);

    boolean resumeJob(JobInfo jobInfo);

    boolean startJobNow(JobInfo jobInfo);
}
