package com.flightDelay.flightdelayapi.shared.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flightDelay.flightdelayapi.shared.exception.request.InvalidDatePatternException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateInPatternDeserializer extends JsonDeserializer<LocalDateTime> {

    @Value("${date.defaultDateWithTimePattern}")
    private String defaultDateWithTimePattern;

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDateWithTimePattern);

        String dateInJson = jsonParser.getValueAsString();

        try {
            return LocalDateTime.parse(dateInJson, formatter);

        } catch (DateTimeParseException e) {
            throw new InvalidDatePatternException(defaultDateWithTimePattern.replace("'", "").toUpperCase(), dateInJson);
        }
    }
}
