package com.flightDelay.flightdelayapi.controller;

import com.flightDelay.flightdelayapi.repository.RunwaysRepository;
import com.flightDelay.flightdelayapi.service.RunwayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/runways")
public class RunwayController {

    private final RunwayServiceImpl runwayService;

    @DeleteMapping
    public String removeAll() {
        runwayService.deleteAll();
        return "DELETED";
    }
}
