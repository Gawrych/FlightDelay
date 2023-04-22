package com.flightDelay.flightdelayapi.runway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunwayServiceImpl implements RunwayService {

    private final RunwaysRepository runwaysRepository;

    public void deleteAll() {
        runwaysRepository.deleteAll();
    }

    public List<Runway> findByAirportIdent(String airportIdent) {
        return runwaysRepository.findAllByAirportCode(airportIdent);
    }
}
