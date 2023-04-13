package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartureAdditionalTimeServiceImpl implements DepartureAdditionalTimeService {

    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;
    private final AirportServiceImpl airportService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public ResponseEntity<String> addNewDepartureAdditionalTimeRecords(String newDataInJsonString) {
        try {
            TypeReference<List<DepartureAdditionalTime>> typeReference = new TypeReference<>(){};
            List<DepartureAdditionalTime> departureAdditionalTimeRecords = objectMapper.readValue(newDataInJsonString, typeReference);
            departureAdditionalTimeRecords.forEach(this::save);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New data has been added to the database", HttpStatus.CREATED);
    }

    public void save(DepartureAdditionalTime departureAdditionalTime) {
        if (!departureAdditionalTimeRepository.existsByGeneratedId(departureAdditionalTime.generateId())) {
            departureAdditionalTime.setAirportBidirectionalRelationshipByCode(departureAdditionalTime.getAirportCode(), airportService);
            departureAdditionalTimeRepository.save(departureAdditionalTime);
        }
    }
}
