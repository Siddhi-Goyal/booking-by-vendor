package com.gap.sourcing.smee.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSSS xxx";

    @Override
    public String convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(DATE_FORMAT).format(zonedDateTime);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dateAsString) {
        if (dateAsString == null) {
            return null;
        }
        return ZonedDateTime.parse(dateAsString, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
