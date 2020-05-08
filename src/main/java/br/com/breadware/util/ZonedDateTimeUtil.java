package br.com.breadware.util;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Component
public class ZonedDateTimeUtil {

    @SuppressWarnings("SpellCheckingInspection")
    private static final DateTimeFormatter ENGLISH_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss '['XXX']' '('zzzz')'");

    @SuppressWarnings("SpellCheckingInspection")
    private static final DateTimeFormatter PORTUGUESE_BRAZIL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss '['XXX']' '('zzzz')'");

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = ENGLISH_DATE_TIME_FORMATTER;

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTERS_BY_LOCALE_LANGUAGE_TAG;

    private static final String PORTUGUESE_BRAZIL_LOCALE_LANGUAGE_TAG = "pt-BR";

    static {
        DATE_TIME_FORMATTERS_BY_LOCALE_LANGUAGE_TAG = Map.of(Locale.ENGLISH.toLanguageTag(), ENGLISH_DATE_TIME_FORMATTER, PORTUGUESE_BRAZIL_LOCALE_LANGUAGE_TAG, PORTUGUESE_BRAZIL_DATE_TIME_FORMATTER);
    }

    private final ZoneId zoneId;

    private final Locale locale;

    @Inject
    public ZonedDateTimeUtil(ZoneId zoneId, Locale locale) {
        this.zoneId = zoneId;
        this.locale = locale;
    }

    public String writeAsHumanReadableFormat(ZonedDateTime zonedDateTime) {
        return retrieveDateTimeFormatter().format(zonedDateTime);
    }

    private DateTimeFormatter retrieveDateTimeFormatter() {
        return DATE_TIME_FORMATTERS_BY_LOCALE_LANGUAGE_TAG.getOrDefault(locale.toLanguageTag(), DEFAULT_DATE_TIME_FORMATTER);
    }

    public ZonedDateTime convertFromUtcInstant(Instant utcInstant) {
        return ZonedDateTime.ofInstant(utcInstant, zoneId);
    }
}
