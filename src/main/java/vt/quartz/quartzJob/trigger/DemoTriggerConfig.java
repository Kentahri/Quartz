//package vt.quartz.quartzJob.trigger;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DemoTriggerConfig {
//
//    @Autowired
//    @Qualifier("logJobDetail")
//    private JobDetail logJobDetail;
//
//    @Autowired
//    @Qualifier("demoJobDetail")
//    private JobDetail demoJobDetail;
//
//    @Bean
//    public Trigger logTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(logJobDetail)
//                .withIdentity("logTrigger", "DEFAULT")
//                .withSchedule(
//                        CronScheduleBuilder.cronSchedule("*/30 * * * * ?")
//                )
//                .build();
//    }
//
//    @Bean
//    public Trigger demoTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(demoJobDetail)
//                .withIdentity("demoTrigger", "DEFAULT")
//                .withSchedule(
//                        CronScheduleBuilder.cronSchedule("0/10 * * * * ? *")
//                )
//                .build();
//    }
//}
