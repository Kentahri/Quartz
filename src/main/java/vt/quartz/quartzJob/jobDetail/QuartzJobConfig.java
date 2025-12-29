//package vt.quartz.quartzJob.jobDetail;
//
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import vt.quartz.quartzJob.job.DemoJob;
//import vt.quartz.quartzJob.job.LogJob;
//
//@Configuration
//public class QuartzJobConfig {
//    @Bean
//    public JobDetail logJobDetail() {
//        return JobBuilder.newJob(LogJob.class)
//                .withIdentity("logJob", "DEFAULT")
//                .usingJobData("jobName", "abc")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public JobDetail demoJobDetail() {
//        return JobBuilder.newJob(DemoJob.class)
//                .withIdentity("demoJob", "DEFAULT")
//                .usingJobData("demoName", "bcd")
//                .storeDurably()
//                .build();
//    }
//}
