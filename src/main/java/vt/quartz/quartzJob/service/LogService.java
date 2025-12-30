package vt.quartz.quartzJob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LogService {

    private final JdbcTemplate quartzJdbcTemplate;
    private final JdbcTemplate appJdbcTemplate;

    public LogService(
            @Qualifier("quartzJdbcTemplate") JdbcTemplate quartzJdbcTemplate,
            @Qualifier("appJdbcTemplate") JdbcTemplate appJdbcTemplate) {
        this.quartzJdbcTemplate = quartzJdbcTemplate;
        this.appJdbcTemplate = appJdbcTemplate;
    }

    /**
     * Ghi log vào Quartz DB
     * KHÔNG quản lý transaction
     */
    public void logToQuartzDB(String jobName, String message, String status) {
        try {
            String fullMessage = String.format(
                    "[QUARTZ_DB] Job: %s | Status: %s | Message: %s",
                    jobName, status, message
            );

            String sql = "INSERT INTO job_log (message, created_at) VALUES (?, NOW())";
            quartzJdbcTemplate.update(sql, fullMessage);

            log.info("Logged to Quartz DB: {}", jobName);
        } catch (Exception e) {
            log.error("Error logging to Quartz DB", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Ghi log vào App DB
     * KHÔNG quản lý transaction
     */
    public void logToAppDB(String jobName, String message, String status) {
        try {
            String fullMessage = String.format(
                    "[APP_DB] Job: %s | Status: %s | Message: %s",
                    jobName, status, message
            );

            String sql = "INSERT INTO job_log (message, created_at) VALUES (?, NOW())";
            appJdbcTemplate.update(sql, fullMessage);

            log.info("Logged to App DB: {}", jobName);
        } catch (Exception e) {
            log.error("Error logging to App DB", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Ghi log vào cả 2 DB trong cùng 1 XA transaction
     * ĐÂY là transaction boundary duy nhất
     */
    @Transactional("transactionManager")
    public void logToBothDBs(String jobName, String message, String status) {
        logToQuartzDB(jobName, message, status);
        logToAppDB(jobName, message, status);
    }
}
