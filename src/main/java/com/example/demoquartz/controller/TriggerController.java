package com.example.demoquartz.controller;

import com.example.demoquartz.service.TriggerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Trigger Manager")
public class TriggerController {

    @Autowired
    private TriggerService triggerService;

    @Operation(summary = "Pause trigger")
    @PostMapping("/trigger/{triggerName}/{group}/pause")
    public ResponseEntity<String> pauseTrigger(@PathVariable String triggerName, @PathVariable String group)
            throws SchedulerException {
        triggerService.pauseTrigger(triggerName, group);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "Resume trigger")
    @PostMapping("/trigger/{triggerName}/{group}/resume")
    public ResponseEntity<String> resumeTrigger(@PathVariable String triggerName, @PathVariable String group)
            throws SchedulerException {
        triggerService.resumeTrigger(triggerName, group);
        return ResponseEntity.ok("success");
    }
}
