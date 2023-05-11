package com.flightDelay.flightdelayapi.tafApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.weather.taf.TafWeather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class TafApiServiceImpl implements TafApiService {

    @Value("${api.checkwxapi.base.icao}")
    private String baseUrlIcao;

    @Value("${api.checkwxapi.base.coordinate}")
    private String baseUrlCoordinate;

    @Value("${weather.checkwxapi.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private final AirportService airportService;

    private final TafMapper tafMapper;


    public TafWeather getWeather(String airportIdent) {
        String populatedURL = populateBaseIcaoUrlWithVariables(airportIdent);

        try {
            URL url = new URL(populatedURL);
            JsonNode rootNode = objectMapper.readTree(url);
            return tafMapper.mapFromTafJson(rootNode);
        } catch (IOException e) {
            // TODO: Create custom exception
            throw new RuntimeException(e);
        }
    }

    private String populateBaseIcaoUrlWithVariables(String airportIdent) {
        return String.format(baseUrlIcao, airportIdent, apiKey);
    }

    // TODO: If result is 0 search by latitude and longitude, result 0 mean that taf api did not find that airport
    private String populateBaseCoordinateUrlWithVariables(String airportIdent) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();

        return String.format(baseUrlCoordinate, latitude, longitude, apiKey);
    }
}
