package com.flightDelay.flightdelayapi.shared;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateProcessorImpl implements DateProcessor {

    public static final String WEATHER_API_PATTERN = "yyyy-MM-dd";
    public static final String DATE_WITH_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

    public String parse(long dateInMillis, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        return dateTimeFormatter.format(date);
    }

    public int getHour(long dateInMillis) {
        return Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).getHour();
    }
}
