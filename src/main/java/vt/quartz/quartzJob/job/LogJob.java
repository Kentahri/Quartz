package vt.quartz.quartzJob.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.service.JobLogService;

import java.time.LocalDateTime;

@Component
public class LogJob implements Job {

    @Autowired
    private JobLogService jobLogService;

    @Override
    public void execute(JobExecutionContext context) {
        String name = context.getJobDetail().getJobDataMap().getString("jobName");

        jobLogService.writeLog(name + " - " + LocalDateTime.now());
    }
}
