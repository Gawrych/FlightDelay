package com.flightDelay.flightdelayapi.runway;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/runways")
public class RunwayController {

    private final RunwayService runwayService;

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Runway> update(@RequestBody String dataInJson) {
        return runwayService.updateFromJson(dataInJson);
    }
}
