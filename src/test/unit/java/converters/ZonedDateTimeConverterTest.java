package converters;

import com.gap.sourcing.smee.converters.ZonedDateTimeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZonedDateTimeConverterTest {

    private ZonedDateTimeConverter converter;
    private ZonedDateTime zonedDateTime;
    private String zoneDateTimeString;

    @BeforeEach
    void init() {
        converter = new ZonedDateTimeConverter();
        zonedDateTime = ZonedDateTime.of(2021, 3, 31, 12, 0, 0, 0, ZoneId.of("Etc/UTC"));
        zoneDateTimeString = "2021-03-31 12:00:00.0000000 +00:00";
    }

    @Test
    void convertToDatabaseColumn_shouldConvertZonedDateTimeToString() {
        final String zoneDateTimeStringConverted = converter.convertToDatabaseColumn(zonedDateTime);
        assertThat(zoneDateTimeString, is(zoneDateTimeStringConverted));
    }

    @Test
    void convertToDatabaseColumn_shouldConvertStringToZonedDateTime() {
        final ZonedDateTime zoneDateTimeConverted = converter.convertToEntityAttribute(zoneDateTimeString);
        assertTrue(zonedDateTime.isEqual(zoneDateTimeConverted));
    }
}
