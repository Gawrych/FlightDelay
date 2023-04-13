package com.flightDelay.flightdelayapi.controller;

import com.flightDelay.flightdelayapi.model.DepartureAdditionalTime;
import com.flightDelay.flightdelayapi.repository.DepartureAdditionalTimeRepository;
import com.flightDelay.flightdelayapi.service.DepartureAdditionalTimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/departure")
public class DepartureAdditionalTimeController {

    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;

    private final DepartureAdditionalTimeServiceImpl departureAdditionalTimeService;

    @GetMapping(path = "/all")
    public List<DepartureAdditionalTime> getDelaysInJSON() {
//        return arrivalDelayRepository.findByFlightArrivalDelay(minFlightArrivalDelayNumber);
        return departureAdditionalTimeRepository.findAll();
    }

    @PutMapping
    public ResponseEntity<?> addNewRecords(@RequestBody String newDepartureAdditionalTimeRecords) {
        return departureAdditionalTimeService.addNewDepartureAdditionalTimeRecords(newDepartureAdditionalTimeRecords);
    }

    @DeleteMapping
    public String removeAllDepartureAddTimes() {
        departureAdditionalTimeRepository.deleteAll();
        return "DELETED";
    }
}
