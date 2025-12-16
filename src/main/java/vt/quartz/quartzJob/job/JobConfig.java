package vt.quartz.quartzJob.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.service.QuartzTask;


@Component
@DisallowConcurrentExecution
public class JobConfig implements Job {

    @Autowired
    private ApplicationContext context;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String type = jobExecutionContext.getMergedJobDataMap().getString("type");

        QuartzTask task = context.getBean(type,QuartzTask.class);
        task.run();
    }
}
