//package vt.quartz.quartzJob.service;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vt.quartz.quartzJob.entity.EmailJobState;
//import vt.quartz.quartzJob.job.SendEmailJob;
//import vt.quartz.quartzJob.repository.EmailJobStateRepository;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    private EmailJobStateRepository emailJobStateRepo;
//
//    public void createTemplateEmailJob(
//            String jobName,
//            String jobGroup,
//            String triggerName,
//            String triggerGroup,
//            String cronExpression,
//            String to,
//            String subject,
//            String bodyTemplate
//    ) throws SchedulerException {
//
//        String quartzJobKey = jobGroup + "." + jobName;
//
//        if (emailJobStateRepo.existsByJobKey(quartzJobKey)) {
//            throw new IllegalStateException("Job already exists");
//        }
//
//        EmailJobState state = new EmailJobState();
//        state.setJobKey(quartzJobKey);
//        state.setToEmails(to);
//        state.setSubject(subject);
//        state.setBodyTemplate(bodyTemplate);
//        state.setRunCount(0);
//        state.setStatus("READY");
//
//        emailJobStateRepo.save(state);
//
//        JobDetail jobDetail = JobBuilder.newJob(SendEmailJob.class)
//                .withIdentity(jobName, jobGroup)
//                .usingJobData("jobKey", quartzJobKey)
//                .storeDurably()
//                .build();
//
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity(triggerName, triggerGroup)
//                .forJob(jobDetail)
//                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)
//                        .withMisfireHandlingInstructionFireAndProceed())
//                .build();
//
//        try {
//            scheduler.scheduleJob(jobDetail, trigger);
//        } catch (SchedulerException e) {
//            emailJobStateRepo.delete(state);
//            throw e;
//        }
//    }
//
//}
