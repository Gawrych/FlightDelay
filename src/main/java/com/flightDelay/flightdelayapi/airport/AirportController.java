package com.flightDelay.flightdelayapi.airport;


import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayRepository;
import com.flightDelay.flightdelayapi.departureAdditionalTime.DepartureAdditionalTimeRepository;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayRepository;
import com.flightDelay.flightdelayapi.region.RegionRepository;
import com.flightDelay.flightdelayapi.runway.RunwaysRepository;
import com.flightDelay.flightdelayapi.traffic.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airports")
public class AirportController {

    private final AirportRepository airportRepository;
    private final TrafficRepository trafficRepository;
    private final ArrivalDelayRepository arrivalDelayRepository;
    private final PreDepartureDelayRepository preDepartureDelayRepository;
    private final DepartureAdditionalTimeRepository departureAdditionalTimeRepository;
    private final RunwaysRepository runwaysRepository;
    private final RegionRepository regionRepository;
    private final AirportServiceImpl airportService;

    @GetMapping(path = "/all")
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @GetMapping
    public Airport getAllAirports(@RequestParam String airportCode) {
        return airportRepository.findByAirportIdent(airportCode);
    }


    @PatchMapping
    public ResponseEntity<?> updateAirports(@RequestBody String newAirportInJson) {

        return ResponseEntity.ok("resource address updated");
    }

    @DeleteMapping
    public String removeAllAirports() {
        airportRepository.deleteAll();
        return "DELETED";
    }
}
