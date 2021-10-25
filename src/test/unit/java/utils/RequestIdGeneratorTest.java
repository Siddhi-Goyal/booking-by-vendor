package utils;

import com.gap.sourcing.smee.utils.RequestIdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestIdGeneratorTest {

    @Test
    void generateRequestId_shouldReturnRandomString() {
        String requestId = RequestIdGenerator.generateRequestId();
        assertNotNull(requestId);
        assertEquals(16, requestId.length());
    }
}