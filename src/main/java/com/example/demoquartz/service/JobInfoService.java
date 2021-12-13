package com.example.demoquartz.service;

import com.example.demoquartz.enitiy.JobInfo;
import java.util.List;

public interface JobInfoService {

    List<JobInfo> getCurrentSchedulerJobInfos();
}
