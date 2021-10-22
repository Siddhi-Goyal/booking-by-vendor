package com.gap.sourcing.smee.filters;


import com.gap.sourcing.smee.utils.RequestIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;

@Component
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put(REQUEST_ID_KEY, RequestIdGenerator.generateRequestId());
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
    }
}
