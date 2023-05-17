package com.flightDelay.flightdelayapi.traffic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayServiceImpl;
import com.flightDelay.flightdelayapi.shared.exception.importData.ProcessingJsonDataFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final TrafficRepository trafficRepository;
    private final AirportService airportService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<Traffic>> typeReference = new TypeReference<>(){};
            List<Traffic> trafficReports = objectMapper.readValue(newDataInJsonString, typeReference);
            trafficReports.forEach(this::save);

        } catch (JsonProcessingException e) {
            throw new ProcessingJsonDataFailedException(ArrivalDelayServiceImpl.class.getName());
        }

        return newDataInJsonString;
    }

    @Override
    public void save(Traffic traffic) {
        if (!trafficRepository.existsByGeneratedId(traffic.generateId())) {

            String airportIdent = traffic.getAirportCode();
            if (!airportService.existsByAirportIdent(airportIdent)) {
                traffic = setAirportBidirectionalRelationshipByCode(airportIdent, traffic);
                trafficRepository.save(traffic);
            }
        }
    }

    @Override
    public Traffic setAirportBidirectionalRelationshipByCode(String airportCode, Traffic traffic) {
        Airport airport = airportService.findByAirportIdent(airportCode);
        if (airport != null) {
            traffic.setAirport(airport);
            airport.getTrafficReports().add(traffic);
        }
        return traffic;
    }
}
