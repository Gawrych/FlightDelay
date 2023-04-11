package com.flightDelay.flightdelayapi.controller;


import com.flightDelay.flightdelayapi.model.Traffic;
import com.flightDelay.flightdelayapi.repository.TrafficRepository;
import com.flightDelay.flightdelayapi.service.TrafficServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficRepository trafficRepository;
    private final TrafficServiceImpl trafficService;

    @GetMapping(path = "/airport")
    @ResponseBody
    public List<Traffic> getTrafficInJSON(@RequestParam String airportCode) {
        return trafficRepository.findAllByAirportCode(airportCode);
    }

    @PostMapping
    public ResponseEntity<?> addNewTrafficReport(@RequestBody String newDataInJsonString) {
        trafficService.addToDatabase(newDataInJsonString);
        return ResponseEntity.ok("Resource traffic updated");
    }

    @DeleteMapping
    @ResponseBody
    public String removeAllRowsInTrafficTable() {
        // Move deleteAll to traffic service
        trafficRepository.deleteAll();
        return "DELETED";
    }
}
