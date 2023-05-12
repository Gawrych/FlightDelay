package com.flightDelay.flightdelayapi.airport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public void save(Airport airport) {
        airportRepository.save(airport);
    }

    @Override
    public Airport findByAirportIdent(String airportIdent) {
        return airportRepository.findByAirportIdent(airportIdent);
    }

    @Override
    public boolean existsByAirportIdent(String airportIdent) {
        return airportRepository.existsByAirportIdent(airportIdent);
    }
}
