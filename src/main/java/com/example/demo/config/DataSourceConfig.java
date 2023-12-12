package com.example.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int dbMaxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int dbMinIdle;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private long dbConnectionTimeout;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private long dbIdleTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private long dbMaxLifetime;

    @Value("${spring.datasource.hikari.leak-detection-threshold}")
    private long dbLeakDetectionThreshold;

    @Value("${spring.datasource.hikari.validation-timeout}")
    private long dbValidationTimeout;

    @Value("${spring.datasource.hikari.connection-test-query}")
    private String dbConnectionTestQuery;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        config.setDriverClassName(dbDriverClassName);
        config.setMaximumPoolSize(dbMaxPoolSize);
        config.setMinimumIdle(dbMinIdle);
        config.setConnectionTimeout(dbConnectionTimeout);
        config.setIdleTimeout(dbIdleTimeout);
        config.setMaxLifetime(dbMaxLifetime);
        config.setLeakDetectionThreshold(dbLeakDetectionThreshold);
        config.setValidationTimeout(dbValidationTimeout);
        config.setConnectionTestQuery(dbConnectionTestQuery);
        return new HikariDataSource(config);
    }
}
