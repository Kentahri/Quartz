package vt.quartz.quartzJob.trigger;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloTrigger {
    @Bean
    public Trigger helloTrigger(JobDetail helloDetail){
        System.out.println("Executing Trigger");
        try{
            return TriggerBuilder.newTrigger().withIdentity("HELLO_TRIGGER","TRIGGER_GROUP")
                    .forJob(helloDetail).startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();
        }catch(Exception e){
            System.out.println("Error Executing Trigger: " + e);
            return null;
        }
    }

}
