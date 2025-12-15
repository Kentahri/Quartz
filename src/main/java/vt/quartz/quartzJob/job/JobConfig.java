package vt.quartz.quartzJob.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import vt.quartz.quartzJob.Service.HelloJob;
import vt.quartz.quartzJob.quartzManagement.SpringContext;


public class JobConfig implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HelloJob helloJob = SpringContext.getBean(HelloJob.class);
        helloJob.run();

    }
}
