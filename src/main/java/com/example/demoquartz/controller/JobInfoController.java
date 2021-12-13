package com.example.demoquartz.controller;

import com.example.demoquartz.enitiy.JobInfo;
import com.example.demoquartz.service.JobInfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "JobInfo Controller")
@RestController
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;

    @Operation(summary = "Get current scheduler job infos")
    @GetMapping("/job-infos")
    public ResponseEntity<List<JobInfo>> getCurrentSchedulerJobInfos() {
        return ResponseEntity.ok(jobInfoService.getCurrentSchedulerJobInfos());
    }
}
