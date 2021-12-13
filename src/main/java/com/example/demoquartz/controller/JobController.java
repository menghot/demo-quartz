package com.example.demoquartz.controller;

import com.example.demoquartz.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Job Controller")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(summary = "Get job's triggers")
    @GetMapping("/jobs/{jobKey}/{group}/triggers")
    ResponseEntity<List<Trigger>> triggers(@PathVariable String jobKey,@PathVariable String group)
            throws SchedulerException {
        return ResponseEntity.ok(jobService.getJobTriggers(jobKey, group));
    }
}
