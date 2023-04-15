package com.flightDelay.flightdelayapi.traffic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService, UpdateFromJson {

    private final TrafficRepository trafficRepository;
    private final AirportServiceImpl airportService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<String> updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<Traffic>> typeReference = new TypeReference<>(){};
            List<Traffic> trafficReports = objectMapper.readValue(newDataInJsonString, typeReference);
            trafficReports.forEach(this::save);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New data has been added to the database", HttpStatus.CREATED);
    }

    public void save(Traffic traffic) {
        if (!trafficRepository.existsByGeneratedId(traffic.generateId())) {
            traffic.setAirportBidirectionalRelationshipByCode(traffic.getAirportCode(), airportService);
            trafficRepository.save(traffic);
        }
    }
}
