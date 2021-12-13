package com.example.demoquartz.controller;

import com.example.demoquartz.service.TriggerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Trigger Controller")
public class TriggerController {

    @Autowired
    private TriggerService triggerService;

    @GetMapping("/triggers")
    public ResponseEntity<Trigger> listTriggers(@RequestParam  String triggerName, @RequestParam String group) {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Pause trigger")
    @PostMapping("/triggers/{triggerName}/{group}/pause")
    public ResponseEntity<String> pauseTrigger(@PathVariable String triggerName, @PathVariable String group)
            throws SchedulerException {
        triggerService.pauseTrigger(triggerName, group);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "Resume trigger")
    @PostMapping("/triggers/{triggerName}/{group}/resume")
    public ResponseEntity<String> resumeTrigger(@PathVariable String triggerName, @PathVariable String group)
            throws SchedulerException {
        triggerService.resumeTrigger(triggerName, group);
        return ResponseEntity.ok("success");
    }
}
