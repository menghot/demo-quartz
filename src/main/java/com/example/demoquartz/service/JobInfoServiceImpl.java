package com.example.demoquartz.service;

import com.example.demoquartz.enitiy.JobInfo;
import com.example.demoquartz.repository.JobInfoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Value("${spring.quartz.properties.org.quartz.scheduler.instanceName}")
    private String schedulerName;

    @Override
    public List<JobInfo> getCurrentSchedulerJobInfos() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setSchedulerName(schedulerName);
        return jobInfoRepository.findAll(Example.of(jobInfo));
    }
}
