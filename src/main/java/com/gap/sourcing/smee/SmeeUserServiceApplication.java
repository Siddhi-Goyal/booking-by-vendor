package com.gap.sourcing.smee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SmeeUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmeeUserServiceApplication.class, args);
    }
}
