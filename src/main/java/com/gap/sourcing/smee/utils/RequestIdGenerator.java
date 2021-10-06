package com.gap.sourcing.smee.utils;

import java.util.UUID;

public final class RequestIdGenerator {

    private RequestIdGenerator() {
        
    }

    public static final String REQUEST_ID_KEY = "requestId";

    public static String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "").substring(16);
    }
}
