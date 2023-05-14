package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.weatherFactors.exception.WeatherApiConnectionFailedException;
import com.flightDelay.flightdelayapi.weatherFactors.exception.WeatherApiDateOutOfBoundsException;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import com.flightDelay.flightdelayapi.weatherFactors.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    @Value("${api.openmeteo.base}")
    private String baseUrl;

    @Value("${weather.amountOfNextDaysToGetFromApiForecast}")
    private int amountOfNextDaysToGetFromApiForecast;

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

        String populatedURL = populateBaseUrl(airportIdent, date);
        JsonNode jsonNode = getJsonNodeFromApi(populatedURL);

        int hour = date.getHour();

        return weatherMapper.mapSingleHour(jsonNode, hour);

    }

    @Override
    public List<Weather> getAllNextDayWeatherInPeriods(String airportIdent) {
        LocalDateTime currentTime = LocalDateTime.now();
        String populatedURL = populateBaseUrl(airportIdent, currentTime);
        JsonNode jsonNode = getJsonNodeFromApi(populatedURL);

        int startHour = currentTime.getHour();

        return weatherMapper.mapAllNextDayInPeriods(jsonNode, startHour);
    }

    private JsonNode getJsonNodeFromApi(String populatedUrl) {
        try {
            URL url = new URL(populatedUrl);
            return objectMapper.readTree(url);

        } catch (IOException error) {
           throw new WeatherApiConnectionFailedException();
        }
    }

    private String populateBaseUrl(String airportIdent, LocalDateTime date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(openmeteoDatePattern);

        String startDate = date.format(formatter);
        String endDate = date.plusDays(amountOfNextDaysToGetFromApiForecast).format(formatter);

        return String.format(baseUrl, latitude, longitude, startDate, endDate);
    }

    private void checkRangeOfTheDate(LocalDateTime date) {
        LocalDate maxDate = LocalDate.now().plusDays(weatherForecastLimitInDays);

        if (date.isAfter(maxDate.atStartOfDay())) {
            throw new WeatherApiDateOutOfBoundsException(date.toString(), maxDate.toString());
        }
    }
}
