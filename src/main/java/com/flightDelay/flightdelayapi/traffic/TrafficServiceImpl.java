package com.flightDelay.flightdelayapi.traffic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.exception.resource.TrafficDataNotFoundException;
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
public class TrafficServiceImpl implements TrafficService {

    @Value("${statistics.amountOfMonthsToCollectData}")
    private int amountOfMonthsToCollectData;

    private final TrafficRepository trafficRepository;

    private final AirportService airportService;

    private final TrafficDtoMapper mapper;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Override
    public List<TrafficDto> findAllLatestByAirport(String airportIdent) {
        LocalDate startDate = LocalDate.now().minusMonths(amountOfMonthsToCollectData);

        List<Traffic> traffic = trafficRepository
                .findAllByAirportWithDateAfter(airportIdent, startDate);

        if (traffic.isEmpty()) throw new TrafficDataNotFoundException();

        return mapper.mapFromList(traffic);
    }

    @Override
    @Transactional
    public List<Traffic> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, Traffic.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(Traffic traffic) {
        String airportIdent = traffic.getAirportCode();
        String trafficId = traffic.generateId();

        if (!airportService.existsByAirportIdent(airportIdent)) {
            log.warn("New Traffic with id: {} has airport ident not matching to any airport in the database: {}",
                    trafficId,
                    airportIdent);


            return false;
        }

        if (trafficRepository.existsByGeneratedId(trafficId)) {
            log.info("Traffic with id: {} already exists", trafficId);

            return false;
        }

        trafficRepository.save(setAirportBidirectionalRelationshipByCode(airportIdent, traffic));

        log.info("New Traffic with id: {} has been created", trafficId);

        return true;
    }

    @Override
    public Traffic setAirportBidirectionalRelationshipByCode(String airportCode, Traffic traffic) {
        Airport airport = airportService.findByAirportIdent(airportCode);

        traffic.setAirport(airport);
        airport.getTrafficReports().add(traffic);

        return traffic;
    }
}
