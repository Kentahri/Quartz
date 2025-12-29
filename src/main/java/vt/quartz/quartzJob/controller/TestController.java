package vt.quartz.quartzJob.controller;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.quartz.quartzJob.service.LogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private LogService logService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Trigger job ngay lập tức
     */
    @PostMapping("/trigger-job")
    public ResponseEntity<Map<String, Object>> triggerJob() {
        Map<String, Object> response = new HashMap<>();

        try {
            log.info("🎯 Manual job trigger requested");

            JobKey jobKey = JobKey.jobKey("DualLogTestJob", "TEST_GROUP");

            if (scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey);

                response.put("status", "success");
                response.put("message", "Job triggered successfully");
                response.put("jobName", "DualLogTestJob");
                response.put("triggeredAt", LocalDateTime.now().format(formatter));

                log.info("✅ Job triggered successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Job not found");
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            log.error("❌ Error triggering job: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Test ghi log trực tiếp vào Quartz DB
     */
    @PostMapping("/log/quartz")
    public ResponseEntity<Map<String, Object>> testLogQuartz(@RequestParam(defaultValue = "Manual Test") String message) {
        Map<String, Object> response = new HashMap<>();

        try {
            String timestamp = LocalDateTime.now().format(formatter);
            logService.logToQuartzDB("ManualTest", message + " - " + timestamp, "MANUAL");

            response.put("status", "success");
            response.put("message", "Logged to Quartz DB successfully");
            response.put("database", "QuartzDB");
            response.put("timestamp", timestamp);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Error: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Test ghi log trực tiếp vào App DB
     */
    @PostMapping("/log/app")
    public ResponseEntity<Map<String, Object>> testLogApp(@RequestParam(defaultValue = "Manual Test") String message) {
        Map<String, Object> response = new HashMap<>();

        try {
            String timestamp = LocalDateTime.now().format(formatter);
            logService.logToAppDB("ManualTest", message + " - " + timestamp, "MANUAL");

            response.put("status", "success");
            response.put("message", "Logged to App DB successfully");
            response.put("database", "AppDB");
            response.put("timestamp", timestamp);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Error: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Test XA transaction - ghi vào cả 2 DB cùng lúc
     */
    @PostMapping("/log/xa")
    public ResponseEntity<Map<String, Object>> testXATransaction(@RequestParam(defaultValue = "XA Test") String message) {
        Map<String, Object> response = new HashMap<>();

        try {
            String timestamp = LocalDateTime.now().format(formatter);
            logService.logToBothDBs("ManualXATest", message + " - " + timestamp, "MANUAL_XA");

            response.put("status", "success");
            response.put("message", "Logged to both databases in XA transaction");
            response.put("databases", new String[]{"QuartzDB", "AppDB"});
            response.put("timestamp", timestamp);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Error: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Test XA rollback
     */
    @PostMapping("/log/rollback")
    public ResponseEntity<Map<String, Object>> testRollback() {
        Map<String, Object> response = new HashMap<>();

        try {
            String timestamp = LocalDateTime.now().format(formatter);
            logService.testRollback("ManualRollbackTest");

            // Không nên đến đây vì testRollback luôn throw exception
            response.put("status", "unexpected");
            response.put("message", "Rollback test should have thrown exception");
            return ResponseEntity.internalServerError().body(response);

        } catch (Exception e) {
            log.info("✅ Rollback test completed - exception caught as expected");
            response.put("status", "success");
            response.put("message", "Rollback test successful - both inserts were rolled back");
            response.put("exceptionMessage", e.getMessage());
            response.put("note", "Check both databases - no new records should exist");

            return ResponseEntity.ok(response);
        }
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();

        try {
            response.put("status", "healthy");
            response.put("scheduler", scheduler.isStarted() ? "running" : "stopped");
            response.put("timestamp", LocalDateTime.now().format(formatter));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "unhealthy");
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
