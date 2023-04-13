package com.flightDelay.flightdelayapi.preDepartureDelay;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pre_departure")
public class PreDepartureDelayController {

    private final PreDepartureDelayRepository preDepartureDelayRepository;

    private final PreDepartureDelayServiceImpl preDepartureDelayService;

    @GetMapping(path = "/all")
    public List<PreDepartureDelay> getDelaysInJSON() {
//        return arrivalDelayRepository.findByFlightArrivalDelay(minFlightArrivalDelayNumber);
        return preDepartureDelayRepository.findAll();
    }

    @PatchMapping
    public ResponseEntity<?> updateDelays(@RequestBody String newPreDepartureDelayRecords) {
        preDepartureDelayService.addToDatabase(newPreDepartureDelayRecords);
        return ResponseEntity.ok("resource pre departure delays updated");
    }

    @DeleteMapping
    public String removeAllPreDepartureDelays() {
        preDepartureDelayRepository.deleteAll();
        return "DELETED";
    }
}
