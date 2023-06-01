package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreDepartureDelayServiceImpl implements PreDepartureDelayService {

    private final PreDepartureDelayRepository preDepartureDelayRepository;

    private final PreDepartureDelayDtoMapper mapper;

    private final AirportService airportService;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Value("${statistics.amountOfMonthsToCollectData}")
    private int amountOfMonthsToCollectData;

    @Override
    public List<PreDepartureDelayDto> findAllLatestByAirport(String airportIdent) {
        LocalDate startDate = LocalDate.now().minusMonths(amountOfMonthsToCollectData);

        List<PreDepartureDelay> preDepartureDelays = preDepartureDelayRepository
                .findAllByAirportWithDateAfter(airportIdent, startDate);

        if (preDepartureDelays.isEmpty()) throw new PreDepartureDelayDataNotFoundException();

        return mapper.mapFromList(preDepartureDelays);
    }

    @Override
    @Transactional
    public List<PreDepartureDelay> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, PreDepartureDelay.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(PreDepartureDelay preDepartureDelay) {
        String airportIdent = preDepartureDelay.getAirportCode();
        String preDepartureDelayId = preDepartureDelay.generateId();

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New PreDepartureDelay with id: {} has airport ident not matching to any airport in the database: {}",
                    preDepartureDelayId,
                    airportIdent);

            return false;
        }

        if (preDepartureDelayRepository.existsByGeneratedId(preDepartureDelayId)) {
            log.info("PreDepartureDelay with id: {} already exists", preDepartureDelayId);

            return false;
        }

        preDepartureDelayRepository.save(setAirportBidirectionalRelationshipByCode(airportIdent, preDepartureDelay));

        log.info("New PreDepartureDelay with id: {} has been created", preDepartureDelayId);

        return true;
    }

    @Override
    public PreDepartureDelay setAirportBidirectionalRelationshipByCode(String airportCode, PreDepartureDelay preDepartureDelay) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        preDepartureDelay.setAirport(airport);
        airport.getPreDepartureDelays().add(preDepartureDelay);

        return preDepartureDelay;
    }
}
