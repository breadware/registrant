package br.com.breadware.util;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateTimeUtil {

    private final Locale locale;

    @Inject
    public LocalDateTimeUtil(Locale locale) {
        this.locale = locale;
    }

    public LocalDateTime convertFromEpochTime(long epochTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneOffset.UTC);
    }

    public String toHumanReadableFormat(LocalDateTime localDateTime) {
        // TODO Create date time formatter and write parameter to a more user-friendly value.
        return localDateTime.toString();
    }
}
