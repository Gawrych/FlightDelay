package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartureAdditionalTimeServiceImpl implements DepartureAdditionalTimeService {

    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;

    private final AirportService airportService;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public List<DepartureAdditionalTime> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, DepartureAdditionalTime.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(DepartureAdditionalTime departureAdditionalTime) {
        String airportIdent = departureAdditionalTime.getAirportCode();
        String departureAdditionalTimeId = departureAdditionalTime.generateId();

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New DepartureAdditionalTime with id: {} have airport ident not matching to any airport in the database: {}",
                    departureAdditionalTimeId,
                    airportIdent);
            
            return false;
        }

        if (departureAdditionalTimeRepository.existsByGeneratedId(departureAdditionalTimeId)) {
            log.info("DepartureAdditionalTime with id: {} already exists", departureAdditionalTimeId);

            return false;
        }

        departureAdditionalTimeRepository.save(setAirportBidirectionalRelationshipByCode(
                airportIdent,
                departureAdditionalTime));

        log.info("New DepartureAdditionalTime with id: {} has been created", departureAdditionalTimeId);

        return true;
    }

    @Override
    public DepartureAdditionalTime setAirportBidirectionalRelationshipByCode(String airportCode, DepartureAdditionalTime departureAdditionalTime) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        departureAdditionalTime.setAirport(airport);
        airport.getDepartureAdditionalTimes().add(departureAdditionalTime);

        return departureAdditionalTime;
    }
}
