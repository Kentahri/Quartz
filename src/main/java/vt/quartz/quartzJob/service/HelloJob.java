package vt.quartz.quartzJob.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HelloJob implements QuartzTask {

    @Override
    @Transactional
    public void run()
    {
        System.out.println("Hello Quartz");
    }
}
