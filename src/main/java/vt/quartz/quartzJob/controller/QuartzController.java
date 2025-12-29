//package vt.quartz.quartzJob.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.quartz.SchedulerException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vt.quartz.quartzJob.service.QuartzService;
//
//@RestController
//@RequestMapping("/api/quartz")
//@RequiredArgsConstructor
//public class QuartzController {
//
//    private final QuartzService quartzService;
//
//    // ================= CREATE JOB =================
//    @PostMapping("/jobs")
//    public ResponseEntity<?> createJob(
//            @RequestParam String jobName,
//            @RequestParam(defaultValue = "DEFAULT") String jobGroup,
//            @RequestParam String triggerName,
//            @RequestParam(defaultValue = "DEFAULT") String triggerGroup,
//            @RequestParam String cronExpression
//    ) throws SchedulerException {
//
//        quartzService.createJob(
//                jobName,
//                jobGroup,
//                triggerName,
//                triggerGroup,
//                cronExpression
//        );
//
//        return ResponseEntity.ok("Job created successfully");
//    }
//
//    // ================= DELETE JOB =================
//    @DeleteMapping("/jobs")
//    public ResponseEntity<?> deleteJob(
//            @RequestParam String jobName,
//            @RequestParam(defaultValue = "DEFAULT") String jobGroup
//    ) throws SchedulerException {
//
//        quartzService.deleteJob(jobName, jobGroup);
//        return ResponseEntity.ok("Job deleted successfully");
//    }
//
//    // ===================== PAUSE JOB =====================
//    @PostMapping("/jobs/pause")
//    public ResponseEntity<?> pauseJob(
//            @RequestParam String jobName,
//            @RequestParam(defaultValue = "DEFAULT") String jobGroup
//    ) throws SchedulerException {
//
//        quartzService.pauseJob(jobName, jobGroup);
//        return ResponseEntity.ok("Job paused successfully");
//    }
//
//    // ===================== RESUME JOB =====================
//    @PostMapping("/jobs/resume")
//    public ResponseEntity<?> resumeJob(
//            @RequestParam String jobName,
//            @RequestParam(defaultValue = "DEFAULT") String jobGroup
//    ) throws SchedulerException {
//
//        quartzService.resumeJob(jobName, jobGroup);
//        return ResponseEntity.ok("Job resumed successfully");
//    }
//
//    // ===================== UPDATE CRON =====================
//    @PutMapping("/triggers/cron")
//    public ResponseEntity<?> updateCron(
//            @RequestParam String triggerName,
//            @RequestParam(defaultValue = "DEFAULT") String triggerGroup,
//            @RequestParam String cronExpression
//    ) throws SchedulerException {
//
//        quartzService.updateCron(
//                triggerName,
//                triggerGroup,
//                cronExpression
//        );
//
//        return ResponseEntity.ok("Cron updated successfully");
//    }
//}
