package vt.quartz.quartzJob.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class JobTrigger {
    String time = "0/10 * * * * ?";
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("QUARTZ","JOB_GROUP")
            .startNow().withSchedule(CronScheduleBuilder
                    .cronSchedule(time)).build();
}
