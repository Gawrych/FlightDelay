package com.flightDelay.flightdelayapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.model.DepartureAdditionalTime;
import com.flightDelay.flightdelayapi.repository.DepartureAdditionalTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartureAdditionalTimeServiceImpl implements DepartureAdditionalTimeService {

    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;
    private final AirportServiceImpl airportService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void addToDatabase(String newDataInJsonString) {
        try {
            TypeReference<List<DepartureAdditionalTime>> typeReference = new TypeReference<>(){};
            List<DepartureAdditionalTime> departureAdditionalTimeRecords = objectMapper.readValue(newDataInJsonString, typeReference);

            boolean update = checkIfDatabaseNeedsUpdate(departureAdditionalTimeRecords);
            System.out.println("Need update: " + update);
            if (update) {
//                departureAdditionalTimeRecords.forEach(this::save);
            }
            // return request http status code
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON data", e);
        }
    }

    private boolean checkIfDatabaseNeedsUpdate(List<DepartureAdditionalTime> departureAdditionalTimeRecords) {
        Optional<DepartureAdditionalTime> departureList = departureAdditionalTimeRecords
                .stream().max(Comparator.comparing(DepartureAdditionalTime::getFlightDate));

        if (departureList.isPresent()) {
            DepartureAdditionalTime record = departureList.get();
            return record.getFlightDate() > findNewestRecordByStage(record.getStage()).getFlightDate();
        }
        return true;
    }

    public void save(DepartureAdditionalTime departureAdditionalTime) {
        if (!departureAdditionalTimeRepository.existsByGeneratedId(departureAdditionalTime.generateId())) {
            departureAdditionalTime.setAirportBidirectionalRelationshipByCode(departureAdditionalTime.getAirportCode(), airportService);
            departureAdditionalTimeRepository.save(departureAdditionalTime);
        }
    }

    public DepartureAdditionalTime findNewestRecordByStage(String stage) {
        return departureAdditionalTimeRepository.findNewestRecordByStage(stage).get(0);
    }

}
