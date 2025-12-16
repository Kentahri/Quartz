package vt.quartz.quartzJob.jobDetail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vt.quartz.quartzJob.job.JobConfig;

//job detail return ra ở đây
@Configuration
public class HelloJobDetail {
    @Bean(name = "helloDetail")
    public JobDetail helloJobDetail(){
        return JobBuilder.newJob(JobConfig.class)
                .withIdentity("HELLO_Quartz","JOB_GROUP")
                .usingJobData("type","helloJob")
                .storeDurably(true)
                .build();
    }
}
