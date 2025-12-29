package vt.quartz.quartzJob.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            @Qualifier("quartzDataSource") DataSource quartzDataSource,
            @Qualifier("quartzNonXADataSource") DataSource quartzNonXADataSource,
            PlatformTransactionManager transactionManager,
            ApplicationContext applicationContext,
            QuartzProperties quartzProperties) {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        // ✅ QUAN TRỌNG: Dùng Spring để inject DataSource
        factory.setDataSource(quartzDataSource);
        factory.setNonTransactionalDataSource(quartzNonXADataSource);

        factory.setTransactionManager(transactionManager);
        factory.setApplicationContext(applicationContext);
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);

        // Quartz properties
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

        // ✅ QUAN TRỌNG: Dùng LocalDataSourceJobStore thay vì JobStoreCMT
        // LocalDataSourceJobStore được thiết kế cho Spring integration
        properties.setProperty("org.quartz.jobStore.class",
                "org.springframework.scheduling.quartz.LocalDataSourceJobStore");

        properties.setProperty("org.quartz.jobStore.driverDelegateClass",
                "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");

        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");

        // ✅ KHÔNG cần set dataSource name vì Spring đã inject

        // Scheduler instance
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.scheduler.instanceName", "QuartzScheduler");
        properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");

        // Thread pool
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", "10");
        properties.setProperty("org.quartz.threadPool.threadPriority", "5");

        factory.setQuartzProperties(properties);

        // Job Factory
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);

        return factory;
    }
}