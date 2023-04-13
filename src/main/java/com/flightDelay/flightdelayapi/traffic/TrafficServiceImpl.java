package com.flightDelay.flightdelayapi.traffic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final TrafficRepository trafficRepository;

    private final AirportServiceImpl airportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void addToDatabase(String newDataInJsonString) {
        TypeReference<List<Traffic>> typeReference = new TypeReference<>(){};
        try {
            List<Traffic> trafficReports = objectMapper.readValue(newDataInJsonString, typeReference);
            trafficReports.forEach(this::save);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON data", e);
        }
    }

    public void save(Traffic traffic) {
        if (!trafficRepository.existsByGeneratedId(traffic.generateId())) {
            traffic.setAirportBidirectionalRelationshipByCode(traffic.getAirportCode(), airportService);
            trafficRepository.save(traffic);
        }
    }
}
