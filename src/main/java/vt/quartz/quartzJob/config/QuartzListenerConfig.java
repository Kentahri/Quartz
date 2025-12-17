package vt.quartz.quartzJob.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import vt.quartz.quartzJob.listener.QuartzJobListener;

@Configuration
public class QuartzListenerConfig {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobListener listener;

    public QuartzListenerConfig(
            Scheduler scheduler,
            QuartzJobListener listener) {
        this.scheduler = scheduler;
        this.listener = listener;
    }

    @EventListener
    public void onContextReady(
            ContextRefreshedEvent event)
            throws SchedulerException {

        scheduler.getListenerManager()
                .addJobListener(listener);

        System.out.println("Quartz JobListener registered");
    }
}

