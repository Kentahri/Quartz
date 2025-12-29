//package vt.quartz.quartzJob.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.quartz.SchedulerException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vt.quartz.quartzJob.service.EmailService;
//
//@RestController
//@RequestMapping("/api/quartz")
//@RequiredArgsConstructor
//public class EmailController {
//
//    private final EmailService emailService;
//
//    @PostMapping("/jobs/email")
//    public ResponseEntity<?> createEmailJob(
//            @RequestParam String jobName,
//            @RequestParam(defaultValue = "DEFAULT") String jobGroup,
//            @RequestParam String triggerName,
//            @RequestParam(defaultValue = "DEFAULT") String triggerGroup,
//            @RequestParam String cronExpression,
//            @RequestParam String to,
//            @RequestParam String subject,
//            @RequestParam String bodyTemplate
//    ) throws SchedulerException {
//
//        emailService.createTemplateEmailJob(
//                jobName,
//                jobGroup,
//                triggerName,
//                triggerGroup,
//                cronExpression,
//                to,
//                subject,
//                bodyTemplate
//        );
//
//        return ResponseEntity.ok("Email job created");
//    }
//
//}
