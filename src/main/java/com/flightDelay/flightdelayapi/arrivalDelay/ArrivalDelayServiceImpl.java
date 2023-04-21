package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArrivalDelayServiceImpl implements ArrivalDelayService {

    private final ArrivalDelayRepository arrivalDelayRepository;

    private final AirportService airportService;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<String> updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<ArrivalDelay>> typeReference = new TypeReference<>() {};
            List<ArrivalDelay> arrivalDelayReports = objectMapper.readValue(newDataInJsonString, typeReference);
            arrivalDelayReports.forEach(this::save);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New data has been added to the database", HttpStatus.CREATED);
    }

    @Override
    public void save(ArrivalDelay arrivalDelay) {
        if (!arrivalDelayRepository.existsByGeneratedId(arrivalDelay.generateId())) {
            arrivalDelay.setAirportBidirectionalRelationshipByCode(arrivalDelay.getAirportCode(), airportService);
            arrivalDelayRepository.save(arrivalDelay);
        }
    }
}
