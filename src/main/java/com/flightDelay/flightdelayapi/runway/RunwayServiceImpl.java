package com.flightDelay.flightdelayapi.runway;

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
public class RunwayServiceImpl implements RunwayService {

    private final RunwaysRepository runwaysRepository;

    private final AirportService airportService;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    public List<Runway> findByAirportIdent(String airportIdent) {
        return runwaysRepository.findAllByAirportCode(airportIdent);
    }

    @Override
    public boolean existsById(Long id) {
        return runwaysRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<Runway> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, Runway.class, objectMapper)
                .stream()
                .filter(this::save)
                .toList();
    }

    @Override
    public boolean save(Runway runway) {
        String airportIdent = runway.getAirportCode();
        Long runwayId = runway.getId();

        if (runwayId == null) {
            log.warn("New Runway has no id");

            return false;
        }

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New Runway with id: {} have airport ident not matching to any airport in the database: {}",
                    runwayId,
                    airportIdent);

            return false;
        }

        if (runwaysRepository.existsById(runwayId)) {
            log.info("Runway with id: {} already exists", runwayId);

            return false;
        }

        runwaysRepository.save(setAirportBidirectionalRelationshipByCode(airportIdent, runway));

        log.info("New Runway with id: {} has been created", runwayId);

        return true;
    }

    private Runway setAirportBidirectionalRelationshipByCode(String airportCode, Runway runway) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        runway.setAirport(airport);
        airport.getRunways().add(runway);

        return runway;
    }
}
