package com.flightDelay.flightdelayapi.airport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.region.RegionService;
import com.flightDelay.flightdelayapi.shared.exception.resource.AirportNotFoundException;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    private final RegionService regionService;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Override
    public Airport findByAirportIdent(String airportIdent) {
        return airportRepository.findByAirportIdent(airportIdent)
                .orElseThrow(() -> new AirportNotFoundException(airportIdent));
    }

    @Override
    public boolean existsByAirportIdent(String airportIdent) {
        return airportRepository.existsByAirportIdent(airportIdent);
    }

    @Override
    @Transactional
    public List<Airport> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, Airport.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(Airport airport) {
        String airportIdent = airport.getAirportIdent();

        if (airportRepository.existsByAirportIdent(airportIdent)) {
            log.info("Airport with ident: {} already exists", airportIdent);

            return false;
        }

        String regionIso = airport.getIsoRegion();

        if (regionService.existsByIsoCode(regionIso)) {
            airport.setRegion(regionService.findByIsoCode(regionIso));

            log.info("New Airport with id: {} has been set relation with region with iso: {}",
                    airportIdent,
                    regionIso);
        }

        airportRepository.save(airport);

        log.info("New Airport with ident: {} has been created", airportIdent);

        return true;
    }
}
