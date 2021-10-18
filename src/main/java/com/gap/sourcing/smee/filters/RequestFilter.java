package com.gap.sourcing.smee.filters;


import com.gap.sourcing.smee.utils.RequestIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String requestId = RequestIdGenerator.generateRequestId();
            System.out.println("requestId = " + requestId);
            MDC.put("requestId", requestId);
            return chain.filter(exchange);
        } finally {
            MDC.clear();
        }
    }
}
