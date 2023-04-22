package com.flightDelay.flightdelayapi.shared;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateProcessor {

    public static final String WEATHER_API_PATTERN = "yyyy-MM-dd";
    public static final String DATE_WITH_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

    public static String parse(long dateInMillis, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        return dateTimeFormatter.format(date);
    }

    public static int getHour(long dateInMillis) {
        return Instant.ofEpochMilli(dateInMillis).atZone(ZoneId.systemDefault()).getHour();
    }
}
