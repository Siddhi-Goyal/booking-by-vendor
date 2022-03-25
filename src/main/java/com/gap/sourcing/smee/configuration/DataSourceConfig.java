package com.gap.sourcing.smee.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

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

    public DataSource readOnlyDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:sqlserver://g-sdb-2s-sourcing-smee-booking-01.database.windows.net:1433;database=smee_user_release;ApplicationIntent=ReadOnly");
        hikariDataSource.setUsername("smee_user_app");
        hikariDataSource.setPassword("");
        return hikariDataSource;
    }

    public DataSource primaryDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:sqlserver://g-sdb-2s-sourcing-smee-booking-01.database.windows.net:1433;database=smee_user_release");
        hikariDataSource.setUsername("smee_user_app");
        hikariDataSource.setPassword("");
        return hikariDataSource;
    }
    
}
