package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.flightDelay.flightdelayapi.shared.DateProcessor.WEATHER_API_PATTERN;

@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    private static final String baseUrl = "https://api.open-meteo.com/v1/forecast?latitude=%.2f&" +
            "longitude=%.2f&" +
            "hourly=temperature_2m,windspeed_10m,winddirection_10m&" +
            "forecast_days=1&" +
            "start_date=%3$s&end_date=%3$s";
    private final ObjectMapper objectMapper;
    private final AirportServiceImpl airportService;

    public Weather getWeather(String airportIdent, long date) {
        String populatedURL = populateBaseUrlWithVariables(airportIdent, date);
        try {
            URL url = new URL(populatedURL);
            return objectMapper.readValue(url, Weather.class);
        } catch (IOException e) {
            // TODO: Create log
            throw new RuntimeException(e);
        }
    }

    private String populateBaseUrlWithVariables(String airportIdent, long date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);
        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();
        String dateInPattern = DateProcessor.parse(date, WEATHER_API_PATTERN);
        return String.format(baseUrl, latitude, longitude, dateInPattern);
    }
}
