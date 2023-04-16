package com.flightDelay.flightdelayapi.airport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public void save(Airport airport) {
        airportRepository.save(airport);
    }

    public Airport findByAirportIdent(String airportIdent) {
        return airportRepository.findByAirportIdent(airportIdent);
    }
}
