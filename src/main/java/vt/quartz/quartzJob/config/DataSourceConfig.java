package vt.quartz.quartzJob.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * Quartz XA DataSource - Dùng cho Quartz với XA transaction
     */
    @Bean(name = "quartzDataSource")
    @Primary
    public DataSource quartzDataSource() {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("quartzDS");
        dataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");

        Properties xaProperties = new Properties();
        xaProperties.setProperty("url", "jdbc:mysql://localhost:3306/quartz?useSSL=false&serverTimezone=UTC");
        xaProperties.setProperty("user", "quan");
        xaProperties.setProperty("password", "123456");
        xaProperties.setProperty("pinGlobalTxToPhysicalConnection", "true");

        dataSource.setXaProperties(xaProperties);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setBorrowConnectionTimeout(30);
        dataSource.setTestQuery("SELECT 1");

        return dataSource;
    }

    /**
     * Quartz Non-XA DataSource - Cho operations không cần XA
     */
    @Bean(name = "quartzNonXADataSource")
    public DataSource quartzNonXADataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/quartz?useSSL=false&serverTimezone=UTC");
        config.setUsername("quan");
        config.setPassword("123456");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setPoolName("QuartzNonXAPool");

        return new HikariDataSource(config);
    }

    /**
     * Application XA DataSource
     */
    @Bean(name = "appDataSource")
    public DataSource appDataSource() {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("appDS");
        dataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");

        Properties xaProperties = new Properties();
        xaProperties.setProperty("url", "jdbc:mysql://localhost:3306/appdb?useSSL=false&serverTimezone=UTC");
        xaProperties.setProperty("user", "quan");
        xaProperties.setProperty("password", "123456");
        xaProperties.setProperty("pinGlobalTxToPhysicalConnection", "true");

        dataSource.setXaProperties(xaProperties);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setBorrowConnectionTimeout(30);
        dataSource.setTestQuery("SELECT 1");

        return dataSource;
    }

    /**
     * JdbcTemplate cho Quartz DB
     */
    @Bean(name = "quartzJdbcTemplate")
    public JdbcTemplate quartzJdbcTemplate(@Qualifier("quartzDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * JdbcTemplate cho App DB
     */
    @Bean(name = "appJdbcTemplate")
    public JdbcTemplate appJdbcTemplate(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}