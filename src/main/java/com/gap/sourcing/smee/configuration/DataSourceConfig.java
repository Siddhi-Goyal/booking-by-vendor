package com.gap.sourcing.smee.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Value("${jdbc.primary.url}")
    private String primaryJdbcUrl;

    @Value("${jdbc.readOnly.url}")
    private String readOnlyJdbcUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        RoutingDataSource msDataSource = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnvironment.UPDATABLE, primaryDataSource());
        targetDataSources.put(DatabaseEnvironment.READONLY, readOnlyDataSource());
        msDataSource.setTargetDataSources(targetDataSources);
        msDataSource.setDefaultTargetDataSource(primaryDataSource());
        return msDataSource;
    }

    private DataSource primaryDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(primaryJdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }

    private DataSource readOnlyDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(readOnlyJdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }

    @Bean
    public DataSourceHealthIndicator db() {
        return new DataSourceHealthIndicator(primaryDataSource());
    }

    @Bean
    public DataSourceHealthIndicator readOnlyDB() {
        return new DataSourceHealthIndicator(readOnlyDataSource());
    }

}