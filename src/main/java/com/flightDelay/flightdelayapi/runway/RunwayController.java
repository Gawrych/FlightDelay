package com.flightDelay.flightdelayapi.runway;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/runways")
public class RunwayController {

    private final RunwayService runwayService;

    @PutMapping("/json")
    public ResponseEntity<List<?>> update(@RequestBody String dataInJson) {
        List<?> addedEntities = runwayService.updateFromJson(dataInJson);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }
}
