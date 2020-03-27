package br.com.breadware.util;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class ZonedDateTimeUtil {

    private static final DateTimeFormatter EN_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss '['XXX']' '('zzzz')'");

    private static final DateTimeFormatter PT_BR_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss '['XXX']' '('zzzz')'");

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = EN_DATE_TIME_FORMATTER;

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTERS_BY_LOCALE;

    static {
        Map<String, DateTimeFormatter> dateTimeFormattersByLocale = new HashMap<>();

        dateTimeFormattersByLocale.put(Locale.ENGLISH.getDisplayName(), EN_DATE_TIME_FORMATTER);

        dateTimeFormattersByLocale.put("pt-BR", PT_BR_DATE_TIME_FORMATTER);

        DATE_TIME_FORMATTERS_BY_LOCALE = Collections.unmodifiableMap(dateTimeFormattersByLocale);
    }

    private final ZoneId zoneId;

    private final Locale locale;

    @Inject
    public ZonedDateTimeUtil(ZoneId zoneId, Locale locale) {
        this.zoneId = zoneId;
        this.locale = locale;
    }

    public ZonedDateTime convertFromEpochSecond(long epochSecond) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), zoneId);
    }

    public ZonedDateTime convertFromEpochMillis(long epochMillis) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zoneId);
    }

    public String writeAsHumanReadableFormat(ZonedDateTime zonedDateTime) {
        return retrieveDateTimeFormatter().format(zonedDateTime);
    }

    private DateTimeFormatter retrieveDateTimeFormatter() {
        return DATE_TIME_FORMATTERS_BY_LOCALE.getOrDefault(locale, DEFAULT_DATE_TIME_FORMATTER);
    }

    public ZonedDateTime convertFromUtcInstant(Instant utcInstant) {
        return ZonedDateTime.ofInstant(utcInstant, zoneId);
    }
}
