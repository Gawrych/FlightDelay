package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import com.flightDelay.flightdelayapi.weather.meteo.MeteoMapper;
import com.flightDelay.flightdelayapi.weather.meteo.MeteoWeather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.flightDelay.flightdelayapi.shared.DateProcessorImpl.WEATHER_API_PATTERN;

@Service("meteoApiServiceImpl")
@RequiredArgsConstructor
public class MeteoAPIServiceImpl implements MeteoAPIService {

    @Value("${api.openmeteo.base}")
    private String baseUrl;

    private final ObjectMapper objectMapper;

    private final AirportService airportService;

    private final MeteoMapper meteoMapper;

    private final DateProcessor dateProcessor;


    public MeteoWeather getWeather(String airportIdent, long date) {
        String populatedURL = populateBaseUrlWithVariables(airportIdent, date);
        int hour = dateProcessor.getHour(date);

        try {
            URL url = new URL(populatedURL);
            JsonNode rootNode = objectMapper.readTree(url);
            return meteoMapper.mapFromMeteoJson(rootNode, hour);

        } catch (IOException e) {
            // TODO: Create custom exception
            throw new RuntimeException(e);
        }
    }

    private String populateBaseUrlWithVariables(String airportIdent, long date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();
        String dateInPattern = dateProcessor.parse(date, WEATHER_API_PATTERN);

        return String.format(baseUrl, latitude, longitude, dateInPattern);
    }
}
