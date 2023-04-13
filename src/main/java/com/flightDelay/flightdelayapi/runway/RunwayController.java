package com.flightDelay.flightdelayapi.runway;

import lombok.RequiredArgsConstructor;
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
