//package vt.quartz.quartzJob.job;
//
//import org.quartz.JobExecutionContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//import vt.quartz.quartzJob.service.JobLogService;
//
//import java.time.LocalDateTime;
//
//@Component
//public class DemoJob extends QuartzJobBean {
//
//    @Autowired
//    private JobLogService jobLogService;
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) {
//
//        String name = context.getJobDetail().getJobDataMap().getString("demoName");
//        jobLogService.writeLog(name + " - " + LocalDateTime.now());
//    }
//}
//
