package vt.quartz.quartzJob.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.quartz.quartzJob.entity.JobLog;
import vt.quartz.quartzJob.repository.JobLogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobLogService {

    @Autowired
    private JobLogRepository jobLogRepository;

    @Transactional
    public void writeLog(String message) {
        JobLog log = new JobLog();
        log.setMessage(message);
        log.setCreatedAt(LocalDateTime.now());
        jobLogRepository.save(log);
    }
}
