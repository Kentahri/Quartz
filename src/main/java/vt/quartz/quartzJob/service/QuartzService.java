//package vt.quartz.quartzJob.service;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vt.quartz.quartzJob.job.LogJob;
//
//@Service
//public class QuartzService {
//
//    @Autowired
//    private Scheduler scheduler;
//
//    //Create Job
//    public void createJob(
//            String jobName,
//            String jobGroup,
//            String triggerName,
//            String triggerGroup,
//            String cronExpression
//    ) throws SchedulerException {
//
//        JobDetail jobDetail = JobBuilder.newJob(LogJob.class)
//                .withIdentity(jobName, jobGroup)
//                .usingJobData("jobName", jobName)
//                .storeDurably()
//                .build();
//
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity(triggerName, triggerGroup)
//                .forJob(jobDetail)
//                .withSchedule(
//                        CronScheduleBuilder.cronSchedule(cronExpression)
//                )
//                .build();
//
//        scheduler.scheduleJob(jobDetail, trigger);
//    }
//
//    //Delete Job
//    public void deleteJob(String jobName, String jobGroup)
//            throws SchedulerException {
//
//        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
//        scheduler.deleteJob(jobKey);
//    }
//
//    //Pause Job
//    public void pauseJob(String jobName, String jobGroup)
//            throws SchedulerException {
//
//        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
//        scheduler.pauseJob(jobKey);
//    }
//
//    //Resume Job
//    public void resumeJob(String jobName, String jobGroup)
//            throws SchedulerException {
//
//        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
//        scheduler.resumeJob(jobKey);
//    }
//
//    //Update Cron Expression
//    public void updateCron(
//            String triggerName,
//            String triggerGroup,
//            String cronExpression
//    ) throws SchedulerException {
//
//        TriggerKey triggerKey =
//                TriggerKey.triggerKey(triggerName, triggerGroup);
//
//        CronTrigger oldTrigger =
//                (CronTrigger) scheduler.getTrigger(triggerKey);
//
//        if(oldTrigger == null) {
//            throw new SchedulerException("Trigger not found!");
//        }
//
//        CronTrigger newTrigger =
//                oldTrigger.getTriggerBuilder()
//                        .withSchedule(
//                                CronScheduleBuilder
//                                        .cronSchedule(cronExpression)
//                        )
//                        .build();
//
//        scheduler.rescheduleJob(triggerKey, newTrigger);
//    }
//}
//
