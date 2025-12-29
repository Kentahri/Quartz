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
     */
    @Transactional("transactionManager")
    public void logToQuartzDB(String jobName, String message, String status) {
        try {
            // ✅ Chỉ dùng cột message (gộp tất cả thông tin vào message)
            String fullMessage = String.format("[QUARTZ_DB] Job: %s | Status: %s | Message: %s",
                    jobName, status, message);

            String sql = "INSERT INTO job_log (message, created_at) VALUES (?, NOW())";

            int rows = quartzJdbcTemplate.update(sql, fullMessage);
            log.info("✅ Logged to QUARTZ_DB: {} - {} rows affected", jobName, rows);

        } catch (Exception e) {
            log.error("❌ Error logging to QUARTZ_DB: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to log to Quartz DB", e);
        }
    }

    /**
     * Ghi log vào App DB
     */
    @Transactional("transactionManager")
    public void logToAppDB(String jobName, String message, String status) {
        try {
            // ✅ Chỉ dùng cột message (gộp tất cả thông tin vào message)
            String fullMessage = String.format("[APP_DB] Job: %s | Status: %s | Message: %s",
                    jobName, status, message);

            String sql = "INSERT INTO job_log (message, created_at) VALUES (?, NOW())";

            int rows = appJdbcTemplate.update(sql, fullMessage);
            log.info("✅ Logged to APP_DB: {} - {} rows affected", jobName, rows);

        } catch (Exception e) {
            log.error("❌ Error logging to APP_DB: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to log to App DB", e);
        }
    }

    /**
     * Ghi log vào cả 2 DB trong cùng 1 transaction (XA)
     */
    @Transactional("transactionManager")
    public void logToBothDBs(String jobName, String message, String status) {
        log.info("🔄 Starting XA transaction to log to both databases...");

        // Log to Quartz DB
        logToQuartzDB(jobName, message + " [XA Transaction]", status);

        // Log to App DB
        logToAppDB(jobName, message + " [XA Transaction]", status);

        log.info("✅ XA transaction completed successfully for both databases");
    }

    /**
     * Test rollback - sẽ throw exception sau khi ghi vào Quartz DB
     */
    @Transactional("transactionManager")
    public void testRollback(String jobName) {
        log.info("🧪 Testing XA rollback scenario...");

        // Ghi vào Quartz DB
        logToQuartzDB(jobName, "This should be rolled back", "ROLLBACK_TEST");

        // Ghi vào App DB
        logToAppDB(jobName, "This should also be rolled back", "ROLLBACK_TEST");

        // Throw exception để trigger rollback
        log.error("💥 Throwing exception to trigger XA rollback");
        throw new RuntimeException("Intentional rollback test - both inserts should be rolled back!");
    }
}