package utils;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.gap.sourcing.smee.utils.TraceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TraceUtilTest {

    @Mock
    private Tracer tracer;

    @BeforeEach
    void init() {
        Span spanMock = mock(Span.class);
        Mockito.when(tracer.currentSpan()).thenReturn(spanMock);
        TraceContext context = TraceContext.newBuilder().traceId(1).spanId(2).shared(true).build();
        Mockito.when(tracer.currentSpan().context()).thenReturn(context);
    }

    @Test
    void getTraceId_shouldNotBeNull() {
        String traceId = TraceUtil.getTraceId(tracer);
        assertNotNull(traceId);
    }

}
