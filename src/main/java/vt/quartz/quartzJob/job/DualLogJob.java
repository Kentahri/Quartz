package vt.quartz.quartzJob.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.service.LogService;

@Component
@Slf4j
public class DualLogJob implements Job {

    @Autowired
    private LogService logService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String jobName = context.getJobDetail().getKey().getName();
        String status = context.getMergedJobDataMap()
                .getString("status");

        try {
            logService.logToBothDBs(
                    jobName,
                    "Job executed by Quartz",
                    status
            );
        } catch (Exception e) {
            throw new JobExecutionException(e, false);
        }
    }
}
