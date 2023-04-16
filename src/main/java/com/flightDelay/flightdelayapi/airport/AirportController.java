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
}
