package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayServiceImpl;
import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import com.flightDelay.flightdelayapi.shared.exception.importData.ProcessingJsonDataFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreDepartureDelayServiceImpl implements PreDepartureDelayService {

    private final PreDepartureDelayRepository preDepartureDelayRepository;

    private final AirportService airportService;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<PreDepartureDelay>> typeReference = new TypeReference<>(){};
            List<PreDepartureDelay> preDepartureDelays = objectMapper.readValue(newDataInJsonString, typeReference);
            preDepartureDelays.forEach(this::save);

        } catch (JsonProcessingException e) {
            throw new ProcessingJsonDataFailedException(ArrivalDelayServiceImpl.class.getName());
        }

        return newDataInJsonString;
    }

    @Override
    public void save(PreDepartureDelay preDepartureDelay) {
        if (!preDepartureDelayRepository.existsByGeneratedId(preDepartureDelay.generateId())) {
            preDepartureDelay.setAirportBidirectionalRelationshipByCode(preDepartureDelay.getAirportCode(), airportService);
            preDepartureDelayRepository.save(preDepartureDelay);
        }
    }
}
