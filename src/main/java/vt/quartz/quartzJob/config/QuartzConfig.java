package vt.quartz.quartzJob.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        // Wiring bắt buộc bằng Java
        factory.setDataSource(quartzDataSource);
        factory.setNonTransactionalDataSource(quartzNonXADataSource);
        factory.setTransactionManager(transactionManager);
        factory.setApplicationContext(applicationContext);

        // Các flag Spring hỗ trợ
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);

        // LẤY TOÀN BỘ Quartz properties TỪ application.yml
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        factory.setQuartzProperties(properties);

        // JobFactory để @Autowired trong Job
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);

        return factory;
    }
}
