package com.flightDelay.flightdelayapi.preDepartureDelay;

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
public class PreDepartureDelayServiceImpl implements PreDepartureDelayService, UpdateFromJson {

    private final PreDepartureDelayRepository preDepartureDelayRepository;

    private final AirportServiceImpl airportService;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<String> updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<PreDepartureDelay>> typeReference = new TypeReference<>(){};
            List<PreDepartureDelay> preDepartureDelays = objectMapper.readValue(newDataInJsonString, typeReference);
            preDepartureDelays.forEach(this::save);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New data has been added to the database", HttpStatus.CREATED);
    }

    public void save(PreDepartureDelay preDepartureDelay) {
        if (!preDepartureDelayRepository.existsByGeneratedId(preDepartureDelay.generateId())) {
            preDepartureDelay.setAirportBidirectionalRelationshipByCode(preDepartureDelay.getAirportCode(), airportService);
            preDepartureDelayRepository.save(preDepartureDelay);
        }
    }
}
