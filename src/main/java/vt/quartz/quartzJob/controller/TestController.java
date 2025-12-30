package vt.quartz.quartzJob.controller;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.quartz.quartzJob.job.DualLogJob;


@RestController
@RequestMapping("/api/quartz")
@Slf4j
public class TestController {

    @Autowired
    private Scheduler scheduler;

    private static final String JOB_GROUP = "dynamic-jobs";
    private static final String TRIGGER_GROUP = "dynamic-triggers";

    /**
     * CREATE JOB + CRON TRIGGER
     */
    @PostMapping("/jobs/create")
    public ResponseEntity<?> createJob(
            @RequestParam String jobName,
            @RequestParam String cron) {

        try {
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP);

            if (scheduler.checkExists(jobKey)) {
                return ResponseEntity.badRequest()
                        .body("Job already exists");
            }

            JobDetail jobDetail = JobBuilder.newJob(DualLogJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("status", "SUCCESS")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + "_trigger", TRIGGER_GROUP)
                    .forJob(jobDetail)
                    .withSchedule(
                            CronScheduleBuilder
                                    .cronSchedule(cron)
                    )
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

            return ResponseEntity.ok("Job created successfully");

        } catch (Exception e) {
            log.error("Create job failed", e);
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }

    /**
     * PAUSE JOB (pause trigger)
     */
    @PostMapping("/jobs/pause")
    public ResponseEntity<?> pauseJob(
            @RequestParam String jobName) throws Exception {

        TriggerKey triggerKey =
                TriggerKey.triggerKey(jobName + "_trigger", TRIGGER_GROUP);

        if (!scheduler.checkExists(triggerKey)) {
            return ResponseEntity.badRequest()
                    .body("Trigger not found");
        }

        scheduler.pauseTrigger(triggerKey);
        return ResponseEntity.ok("Paused");
    }

    /**
     * RESUME JOB
     */
    @PostMapping("/jobs/resume")
    public ResponseEntity<?> resumeJob(
            @RequestParam String jobName) throws Exception {

        TriggerKey triggerKey =
                TriggerKey.triggerKey(jobName + "_trigger", TRIGGER_GROUP);

        if (!scheduler.checkExists(triggerKey)) {
            return ResponseEntity.badRequest()
                    .body("Trigger not found");
        }

        scheduler.resumeTrigger(triggerKey);
        return ResponseEntity.ok("Resumed");
    }

    /**
     * DELETE JOB (chuẩn Quartz)
     */
    @PostMapping("/jobs/delete")
    public ResponseEntity<?> deleteJob(
            @RequestParam String jobName) throws Exception {

        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP);
        TriggerKey triggerKey =
                TriggerKey.triggerKey(jobName + "_trigger", TRIGGER_GROUP);

        if (!scheduler.checkExists(jobKey)) {
            return ResponseEntity.badRequest()
                    .body("Job not found");
        }

        // 1. unschedule trigger
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
        }

        // 2. delete job
        scheduler.deleteJob(jobKey);

        return ResponseEntity.ok("Deleted");
    }
}
