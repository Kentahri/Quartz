package vt.quartz.quartzJob.trigger;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobTriggerConfig {
    @Bean(name="JobTrigger")
    public Trigger trigger(JobDetail jobDetail){
        System.out.println("Executing Trigger");
        try{
            return TriggerBuilder.newTrigger().withIdentity("HELLO_TRIGGER","JOB_GROUP").forJob(jobDetail)
                    .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever()
                            )
                    .build();
        }catch(Exception e){
            System.out.println("Error Executing Trigger: " + e);
            return null;
        }
    }

}
