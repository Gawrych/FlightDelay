package com.flightDelay.flightdelayapi.runway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunwayServiceImpl implements RunwayService {

    private final RunwaysRepository runwaysRepository;

    public void deleteAll() {
        runwaysRepository.deleteAll();
    }
}
