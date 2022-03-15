package com.gap.sourcing.smee.utils;

import java.util.UUID;

public final class RequestIdGenerator {

    private RequestIdGenerator() {
        
    }

    public static final String REQUEST_ID_KEY = "requestId";
    public static final int KEY_LENGTH = 16;

    public static String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "").substring(KEY_LENGTH);
    }
}
