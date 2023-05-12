package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.flightDelay.flightdelayapi.shared.DateProcessorImpl.WEATHER_API_PATTERN;

@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    @Value("${api.openmeteo.base}")
    private String baseUrl;

    @Value("${weather.amountOfNextDaysToGetFromApiForecast}")
    private int amountOfNextDaysToGetFromApiForecast;

    private final ObjectMapper objectMapper;

    private final AirportService airportService;

    private final WeatherMapper weatherMapper;

    @Override
    public Weather getWeather(String airportIdent, Date date) {
        String populatedURL = populateBaseUrl(airportIdent, date);
        int hour = date.toInstant().atZone(ZoneId.systemDefault()).getHour();

        try {
            URL url = new URL(populatedURL);
            JsonNode rootNode = objectMapper.readTree(url);
            return weatherMapper.mapSingleHour(rootNode, hour);

        } catch (IOException e) {
            // TODO: Create custom exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Weather> getAllNextDayWeatherInPeriods(String airportIdent) {
        String populatedURL = populateBaseUrl(airportIdent, new Date());
        int startHour = LocalDateTime.now().getHour();

        try {
            URL url = new URL(populatedURL);
            JsonNode rootNode = objectMapper.readTree(url);
            return weatherMapper.mapAllNextDayInPeriods(rootNode, startHour);

        } catch (IOException e) {
            // TODO: Create custom exception
            throw new RuntimeException(e);
        }
    }

    private String populateBaseUrl(String airportIdent, Date date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WEATHER_API_PATTERN);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String startDate = localDate.format(formatter);
        String endDate = localDate.plusDays(amountOfNextDaysToGetFromApiForecast).format(formatter);

        return String.format(baseUrl, latitude, longitude, startDate, endDate);
    }
}
