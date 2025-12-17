package vt.quartz.quartzJob.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobListener implements JobListener {

    private static final String START_TIME  = "START_TIME";

    @Override
    public String getName() {
        return "quartzJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        jobExecutionContext.put(START_TIME, System.currentTimeMillis());

        String type = jobExecutionContext.getMergedJobDataMap().getString("type");

        System.out.print("Start ");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        long start = (long) jobExecutionContext.get(START_TIME);
        long duration = System.currentTimeMillis() - start;

        String type = jobExecutionContext.getMergedJobDataMap().getString("type");

        if(e != null){
            System.out.println("Task "+ type + " Failed At " + duration + " ms!");
        }
        else{
            System.out.println("Task "+ type + " Succeeded!");
        }
    }
}


