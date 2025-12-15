package vt.quartz.quartzJob.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HelloJob {
    @Transactional
    public void run()
    {
        System.out.println("Hello Quartz");
    }
}
