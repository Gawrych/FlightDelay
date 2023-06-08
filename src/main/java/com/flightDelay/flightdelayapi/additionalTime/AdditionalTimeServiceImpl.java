package com.flightDelay.flightdelayapi.additionalTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdditionalTimeServiceImpl implements AdditionalTimeService {

    @Value("${statistics.amountOfMonthsToCollectData}")
    private int amountOfMonthsToCollectData;

    private final AdditionalTimeRepository additionalTimeRepository;

    private final AdditionalTimeDtoMapper mapper;

    private final AirportService airportService;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Override
    public List<AdditionalTimeDto> findAllLatestByAirport(String airportIdent) {
        LocalDate startDate = LocalDate.now().minusMonths(amountOfMonthsToCollectData);

        List<AdditionalTime> additionalTimes = additionalTimeRepository
                .findAllByAirportWithDateAfter(airportIdent, startDate);

        log.info("{} additional time records have been found in the database for airport: {}",
                additionalTimes.size(),
                airportIdent);

        return mapper.mapFromList(additionalTimes);
    }

    @Override
    // TODO: Add transactional
    public List<AdditionalTime> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, AdditionalTime.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(AdditionalTime additionalTime) {
        String airportIdent = additionalTime.getAirportCode();
        String departureAdditionalTimeId = additionalTime.generateId();

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New DepartureAdditionalTime with id: {} has airport ident not matching to any airport in the database: {}",
                    departureAdditionalTimeId,
                    airportIdent);
            
            return false;
        }

        if (additionalTimeRepository.existsByGeneratedId(departureAdditionalTimeId)) {
            log.info("DepartureAdditionalTime with id: {} already exists", departureAdditionalTimeId);

            return false;
        }

        additionalTimeRepository.save(setAirportBidirectionalRelationshipByCode(
                airportIdent,
                additionalTime));

        log.info("New DepartureAdditionalTime with id: {} has been created", departureAdditionalTimeId);

        return true;
    }

    @Override
    public AdditionalTime setAirportBidirectionalRelationshipByCode(String airportCode, AdditionalTime additionalTime) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        additionalTime.setAirport(airport);
        airport.getAdditionalTimes().add(additionalTime);

        return additionalTime;
    }
}
