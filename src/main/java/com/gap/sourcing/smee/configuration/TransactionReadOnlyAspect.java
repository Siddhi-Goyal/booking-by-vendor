package com.gap.sourcing.smee.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(0)
@Slf4j
public class TransactionReadOnlyAspect {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
        log.info("Database Context Set To READ_ONLY");
        try {
            if (transactional.readOnly()) {
                DatabaseContextHolder.set(DatabaseEnvironment.READONLY);
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DatabaseContextHolder.reset();
        }
    }
    
}
