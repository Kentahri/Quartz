package vt.quartz.quartzJob.jobDetail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vt.quartz.quartzJob.job.JobConfig;

@Configuration
public class ByeJobDetail {
    @Bean(name="byeDetail")
    public JobDetail byeJobDetail() {
        return JobBuilder.newJob(JobConfig.class)
                .withIdentity("BYE_Quartz","JOB_GROUP")
                .usingJobData("type","byeJob")
                .storeDurably(true)
                .build();
    }
}
