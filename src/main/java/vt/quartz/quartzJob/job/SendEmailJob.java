package vt.quartz.quartzJob.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.entity.EmailJobState;
import vt.quartz.quartzJob.repository.EmailJobStateRepository;

import java.time.LocalDateTime;

@Component
@DisallowConcurrentExecution
public class SendEmailJob implements Job {

    @Autowired
    private EmailJobStateRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void execute(JobExecutionContext context) {

        String jobKey = context.getJobDetail().getKey().toString();

        EmailJobState state = repo.findByJobKey(jobKey)
                .orElseThrow();

        int nextCount = state.getRunCount() + 1;

        String body = state.getBodyTemplate()
                .replace("{n}", String.valueOf(nextCount));

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(state.getToEmails().split(","));
        msg.setSubject(state.getSubject());
        msg.setText(body);

        mailSender.send(msg);

        state.setRunCount(nextCount);
        state.setLastRunAt(LocalDateTime.now());
        state.setStatus("DONE");

        repo.save(state);
    }
}

