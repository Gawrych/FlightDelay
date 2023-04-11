package com.flightDelay.flightdelayapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.model.ArrivalDelay;
import com.flightDelay.flightdelayapi.model.Traffic;
import com.flightDelay.flightdelayapi.repository.ArrivalDelayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArrivalDelayServiceImpl implements ArrivalDelayService {

    private final ArrivalDelayRepository arrivalDelayRepository;

    private final AirportServiceImpl airportService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void addToDatabase(String newDataInJsonString) {
        TypeReference<List<ArrivalDelay>> typeReference = new TypeReference<>() {};
        try {
            List<ArrivalDelay> arrivalDelayReports = objectMapper.readValue(newDataInJsonString, typeReference);
            arrivalDelayReports.forEach(this::save);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON data", e);
        }
    }

    public void save(ArrivalDelay arrivalDelay) {
        if (!arrivalDelayRepository.existsByGeneratedId(arrivalDelay.generateId())) {
            arrivalDelay.setAirportBidirectionalRelationshipByCode(arrivalDelay.getAirportCode(), airportService);
            arrivalDelayRepository.save(arrivalDelay);
        }
    }
}
