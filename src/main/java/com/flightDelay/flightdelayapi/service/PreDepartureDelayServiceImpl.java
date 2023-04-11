package com.flightDelay.flightdelayapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.model.PreDepartureDelay;
import com.flightDelay.flightdelayapi.model.Traffic;
import com.flightDelay.flightdelayapi.repository.PreDepartureDelayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreDepartureDelayServiceImpl implements PreDepartureDelayService {

    private final PreDepartureDelayRepository preDepartureDelayRepository;

    private final AirportServiceImpl airportService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void addToDatabase(String newDataInJsonString) {
        TypeReference<List<PreDepartureDelay>> typeReference = new TypeReference<>(){};
        try {
            List<PreDepartureDelay> preDepartureDelays = objectMapper.readValue(newDataInJsonString, typeReference);
            preDepartureDelays.forEach(this::save);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON data", e);
        }
    }

    public void save(PreDepartureDelay preDepartureDelay) {
        if (!preDepartureDelayRepository.existsByGeneratedId(preDepartureDelay.generateId())) {
            preDepartureDelay.setAirportBidirectionalRelationshipByCode(preDepartureDelay.getAirportCode(), airportService);
            preDepartureDelayRepository.save(preDepartureDelay);
        }
    }
}
