package vt.quartz.quartzJob.job;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vt.quartz.quartzJob.service.LogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Job test ghi log vào 2 database khác nhau
 * - QuartzDB: Database của Quartz Scheduler
 * - AppDB: Database của application
 */
@Component
@Slf4j
public class DualLogTestJob implements Job {

    @Autowired
    private LogService logService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        String timestamp = LocalDateTime.now().format(formatter);

        log.info("════════════════════════════════════════════════════════");
        log.info("🚀 Starting DualLogTestJob: {}", jobName);
        log.info("⏰ Execution Time: {}", timestamp);
        log.info("════════════════════════════════════════════════════════");

        try {
            // Test 1: Ghi log riêng biệt vào từng DB
            testSeparateLogs(jobName, timestamp);

            Thread.sleep(1000); // Delay để dễ quan sát

            // Test 2: Ghi log vào cả 2 DB trong cùng 1 XA transaction
            testXATransaction(jobName, timestamp);

            Thread.sleep(1000);

            // Test 3: Test rollback (optional - comment out nếu không muốn test)
            // testRollbackScenario(jobName, timestamp);

            log.info("✅ Job completed successfully!");
            log.info("════════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            log.error("❌ Job execution failed: {}", e.getMessage(), e);
            throw new JobExecutionException(e);
        }
    }

    /**
     * Test ghi log riêng biệt vào từng database
     */
    private void testSeparateLogs(String jobName, String timestamp) {
        log.info("\n📝 TEST 1: Separate Logs to Each Database");
        log.info("─────────────────────────────────────────");

        try {
            // Ghi vào Quartz DB
            String quartzMessage = String.format("Job executed at %s - Separate transaction", timestamp);
            logService.logToQuartzDB(jobName, quartzMessage, "SUCCESS");

            // Ghi vào App DB
            String appMessage = String.format("Job executed at %s - Separate transaction", timestamp);
            logService.logToAppDB(jobName, appMessage, "SUCCESS");

            log.info("✅ Separate logs written successfully");
        } catch (Exception e) {
            log.error("❌ Failed to write separate logs: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Test ghi log vào cả 2 DB trong cùng 1 XA transaction
     */
    private void testXATransaction(String jobName, String timestamp) {
        log.info("\n🔄 TEST 2: XA Transaction - Both Databases");
        log.info("─────────────────────────────────────────");

        try {
            String message = String.format("Job executed at %s", timestamp);
            logService.logToBothDBs(jobName, message, "SUCCESS_XA");

            log.info("✅ XA transaction completed - logs written to both databases atomically");
        } catch (Exception e) {
            log.error("❌ XA transaction failed: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Test rollback scenario - cả 2 insert sẽ bị rollback
     */
    private void testRollbackScenario(String jobName, String timestamp) {
        log.info("\n🧪 TEST 3: XA Rollback Test");
        log.info("─────────────────────────────────────────");
        log.info("⚠️  This will intentionally fail to test rollback");

        try {
            logService.testRollback(jobName);
        } catch (Exception e) {
            log.info("✅ Rollback test completed - exception caught as expected");
            log.info("💡 Check databases - both inserts should have been rolled back");
            // Don't rethrow - this is expected behavior for rollback test
        }
    }
}
