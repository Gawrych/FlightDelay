package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayServiceImpl;
import com.flightDelay.flightdelayapi.shared.exception.importData.ProcessingJsonDataFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public String updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<DepartureAdditionalTime>> typeReference = new TypeReference<>(){};
            List<DepartureAdditionalTime> departureAdditionalTimeRecords = objectMapper.readValue(newDataInJsonString, typeReference);
            departureAdditionalTimeRecords.forEach(this::save);

        } catch (JsonProcessingException e) {
            throw new ProcessingJsonDataFailedException(ArrivalDelayServiceImpl.class.getName());
        }

        return newDataInJsonString;
    }

    @Override
    public void save(DepartureAdditionalTime departureAdditionalTime) {
        if (!departureAdditionalTimeRepository.existsByGeneratedId(departureAdditionalTime.generateId())) {

            String airportIdent = departureAdditionalTime.getAirportCode();
            if (airportService.existsByAirportIdent(airportIdent)) {
                departureAdditionalTime = setAirportBidirectionalRelationshipByCode(airportIdent, departureAdditionalTime);
                departureAdditionalTimeRepository.save(departureAdditionalTime);
            }
        }
    }

    @Override
    public DepartureAdditionalTime setAirportBidirectionalRelationshipByCode(String airportCode, DepartureAdditionalTime departureAdditionalTime) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        departureAdditionalTime.setAirport(airport);
        airport.getDepartureAdditionalTimes().add(departureAdditionalTime);

        return departureAdditionalTime;
    }
}
