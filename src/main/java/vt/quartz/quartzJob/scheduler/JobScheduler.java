package vt.quartz.quartzJob.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.job.DualLogTestJob;

/**
 * Scheduler để tạo và schedule các jobs
 */
@Component
@Slf4j
public class JobScheduler implements CommandLineRunner {

    private final Scheduler scheduler;

    public JobScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("🚀 Initializing Quartz Jobs...");

        // Schedule DualLogTestJob
        scheduleDualLogTestJob();

        log.info("✅ All jobs scheduled successfully!");
    }

    /**
     * Schedule DualLogTestJob - chạy mỗi 30 giây
     */
    private void scheduleDualLogTestJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DualLogTestJob.class)
                .withIdentity("DualLogTestJob", "TEST_GROUP")
                .withDescription("Job to test logging to both QuartzDB and AppDB")
                .storeDurably()
                .build();

        // Trigger chạy mỗi 30 giây
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("DualLogTestJobTrigger", "TEST_GROUP")
                .withDescription("Trigger for DualLogTestJob - runs every 30 seconds")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();

        // Check if job exists
        if (scheduler.checkExists(jobDetail.getKey())) {
            log.info("Job already exists, deleting old version...");
            scheduler.deleteJob(jobDetail.getKey());
        }

        scheduler.scheduleJob(jobDetail, trigger);
        log.info("✅ DualLogTestJob scheduled - will run every 30 seconds");
    }

    /**
     * Schedule một job chạy 1 lần ngay lập tức (for testing)
     */
    public void scheduleImmediateJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DualLogTestJob.class)
                .withIdentity("DualLogTestJob_Immediate", "TEST_GROUP")
                .withDescription("Immediate test job")
                .storeDurably(false)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("DualLogTestJobTrigger_Immediate", "TEST_GROUP")
                .startNow()
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        log.info("✅ Immediate job scheduled");
    }
}
