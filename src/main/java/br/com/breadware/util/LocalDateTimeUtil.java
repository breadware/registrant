package br.com.breadware.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class LocalDateTimeUtil {

    public LocalDateTime convertFromEpochTime(long epochTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneOffset.UTC);
    }

    public String toHumanReadableFormat(LocalDateTime localDateTime) {
        // TODO Create date time formatter and write parameter to a more user-friendly value.
        return localDateTime.toString();
    }
}
