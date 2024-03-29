package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
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
public class ArrivalDelayServiceImpl implements ArrivalDelayService {

    @Value("${statistics.amountOfMonthsToCollectData}")
    private int amountOfMonthsToCollectData;

    private final ArrivalDelayRepository arrivalDelayRepository;

    private final AirportService airportService;

    private final ArrivalDelayDtoMapper mapper;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    public List<ArrivalDelayDto> findAllLatestByAirport(String airportIdent) {
        LocalDate startDate = LocalDate.now().minusMonths(amountOfMonthsToCollectData);

        List<ArrivalDelay> arrivalDelays = arrivalDelayRepository
                .findAllByAirportWithDateAfter(airportIdent, startDate);

        log.info("{} arrival delay records have been found in the database for airport: {}",
                arrivalDelays.size(),
                airportIdent);

        return mapper.mapFromList(arrivalDelays);
    }

    @Override
    @Transactional
    public List<ArrivalDelay> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, ArrivalDelay.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }


    @Override
    @Transactional
    public List<ArrivalDelay> updateFromJson(List<ArrivalDelay> arrivalDelays) {
        return arrivalDelays
                .stream()
                .filter(this::save)
                .toList();
    }
    @Override
    public boolean save(ArrivalDelay arrivalDelay) {
        String airportIdent = arrivalDelay.getAirportCode();
        String arrivalDelayId = arrivalDelay.generateId();

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New ArrivalDelay with id: {} has airport ident not matching to any airport in the database: {}",
                    arrivalDelayId,
                    airportIdent);

            return false;
        }

        if (arrivalDelayRepository.existsByGeneratedId(arrivalDelayId)) {
            log.info("ArrivalDelay with id: {} already exists", arrivalDelayId);

            return false;
        }

        arrivalDelayRepository.save(setAirportBidirectionalRelationship(airportIdent, arrivalDelay));

        log.info("New ArrivalDelay with id: {} has been created", arrivalDelayId);

        return true;
    }

    private ArrivalDelay setAirportBidirectionalRelationship(String airportCode, ArrivalDelay arrivalDelay) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        arrivalDelay.setAirport(airport);
        airport.getArrivalDelays().add(arrivalDelay);

        return arrivalDelay;
    }
}
