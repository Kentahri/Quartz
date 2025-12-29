//package vt.quartz.quartzJob.job;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import vt.quartz.quartzJob.service.JobLogService;
//
//
//@Component
//public class LogJob implements Job {
//
//    @Autowired
//    private JobLogService jobLogService;
//
//    @Override
//    public void execute(JobExecutionContext context) {
//        jobLogService.writeLog("XA_JOB");
//    }
//}
//
