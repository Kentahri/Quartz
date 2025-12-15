package vt.quartz.quartzJob.quartzManagement;

import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuartzManager {

    private final Scheduler scheduler;

    public void pauseJob(String jobName, String group) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, group));
    }

    public void resumeJob(String jobName, String group) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, group));
    }

    public void deleteJob(String jobName, String group) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobName, group));
    }

    public void pauseTrigger(String triggerName, String group) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, group));
    }

    public void resumeTrigger(String triggerName, String group) throws SchedulerException {
        scheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, group));
    }

    public void shutdown() throws SchedulerException {
        scheduler.shutdown(true);
    }
}
