package com.flightDelay.flightdelayapi.delay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactorServiceImpl;
import com.flightDelay.flightdelayapi.flight.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delay")
public class DelayController {

    private final ObjectMapper objectMapper;

    private final DelayFactorServiceImpl delayFactorService;

    @PostMapping
    public ResponseEntity<?> getFlightDelayProbability(@RequestBody String flightData) {
        try {
            Flight flightToCheck = objectMapper.readValue(flightData, Flight.class);
            List<DelayFactor> factors = delayFactorService.calculateFactors(flightToCheck);
            return new ResponseEntity<>(factors, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.BAD_REQUEST);
        }
    }
}
