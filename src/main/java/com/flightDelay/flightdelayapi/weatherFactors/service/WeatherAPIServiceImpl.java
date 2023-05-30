package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.weatherFactors.exception.WeatherApiConnectionFailedException;
import com.flightDelay.flightdelayapi.weatherFactors.exception.WeatherApiDateOutOfBoundsException;
import com.flightDelay.flightdelayapi.weatherFactors.mapper.WeatherMapper;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    @Value("${api.openmeteo.base}")
    private String baseUrl;

    @Value("${api.openmeteo.weatherForecastLimitInDays}")
    private int weatherForecastLimitInDays;

    @Value("${date.openmeteo.datePattern}")
    private String openmeteoDatePattern;

    private final ObjectMapper objectMapper;

    private final AirportService airportService;

    private final WeatherMapper weatherMapper;

    @Override
    public Weather getWeather(String airportIdent, LocalDateTime date) {
        checkRangeOfTheDate(date);

        String populatedURL = populateBaseUrl(airportIdent, date, date);
        JsonNode jsonNode = getJsonNodeFromApi(populatedURL);

        int hour = date.getHour();

        return weatherMapper.mapSingleHour(jsonNode, hour);
    }

    @Override
    public List<Weather> getWeatherPeriods(String airportIdent, int amountOfDays) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endDate = currentTime.plusDays(amountOfDays);

        checkRangeOfTheDate(endDate);

        String populatedURL = populateBaseUrl(airportIdent, currentTime, endDate);
        JsonNode jsonNode = getJsonNodeFromApi(populatedURL);

        int startHour = currentTime.getHour();

        return weatherMapper.mapPeriods(jsonNode, startHour, amountOfDays);
    }

    private JsonNode getJsonNodeFromApi(String populatedUrl) {
        try {
            URL url = new URL(populatedUrl);
            JsonNode jsonNode = objectMapper.readTree(url);

            log.info("Weather data from api by url: {}", populatedUrl);

            return jsonNode;

        } catch (IOException error) {
           throw new WeatherApiConnectionFailedException();
        }
    }

    private String populateBaseUrl(String airportIdent, LocalDateTime startDate, LocalDateTime endDate) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(openmeteoDatePattern);

        return String.format(
                baseUrl,
                latitude,
                longitude,
                startDate.format(formatter),
                endDate.format(formatter));
    }

    private void checkRangeOfTheDate(LocalDateTime date) {
        LocalDate maxDate = LocalDate.now().plusDays(weatherForecastLimitInDays);

        if (date.isAfter(maxDate.atStartOfDay())) {
            throw new WeatherApiDateOutOfBoundsException(date.toString(), maxDate.minusDays(1).toString());
        }
    }
}
