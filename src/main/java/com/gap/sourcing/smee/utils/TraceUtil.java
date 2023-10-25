package com.gap.sourcing.smee.utils;

import brave.Tracer;

public class TraceUtil {

    private TraceUtil(){

    }

    public static final String TRACE_ID = "traceId";

    public static String getTraceId(Tracer tracer) {
        if(tracer.currentSpan()!=null) {
            return tracer.currentSpan().context().traceIdString();
        }
        return "";
    }
}
