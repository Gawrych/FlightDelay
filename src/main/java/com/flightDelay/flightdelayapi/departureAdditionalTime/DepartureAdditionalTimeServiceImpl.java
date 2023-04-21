package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartureAdditionalTimeServiceImpl implements DepartureAdditionalTimeService {

    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;
    private final AirportService airportService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<String> updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<DepartureAdditionalTime>> typeReference = new TypeReference<>(){};
            List<DepartureAdditionalTime> departureAdditionalTimeRecords = objectMapper.readValue(newDataInJsonString, typeReference);
            departureAdditionalTimeRecords.forEach(this::save);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New data has been added to the database", HttpStatus.CREATED);
    }

    @Override
    public void save(DepartureAdditionalTime departureAdditionalTime) {
        if (!departureAdditionalTimeRepository.existsByGeneratedId(departureAdditionalTime.generateId())) {
            departureAdditionalTime.setAirportBidirectionalRelationshipByCode(departureAdditionalTime.getAirportCode(), airportService);
            departureAdditionalTimeRepository.save(departureAdditionalTime);
        }
    }
}
