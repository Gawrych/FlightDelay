package com.flightDelay.flightdelayapi.shared;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface DateProcessor {

    String parse(long dateInMillis, String pattern);

    int getHour(long dateInMillis);
}
