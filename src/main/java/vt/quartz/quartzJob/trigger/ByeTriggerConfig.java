package vt.quartz.quartzJob.trigger;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ByeTrigger {
    @Bean
    public Trigger byeTrigger(JobDetail byeDetail){
        System.out.println("Executing Trigger");
        try{
            return TriggerBuilder.newTrigger().withIdentity("BYE_TRIGGER","TRIGGER_GROUP")
                    .forJob(byeDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule("2/5 * * * * ?"))
                    .build();
        }catch(Exception e){
            System.out.println("Error Executing Trigger: " + e);
            return null;
        }
    }
}
