package com.flightDelay.flightdelayapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.model.Runway;
import com.flightDelay.flightdelayapi.repository.RunwaysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunwayServiceImpl implements RunwayService {

    private final RunwaysRepository runwaysRepository;

    public void deleteAll() {
        runwaysRepository.deleteAll();
    }
}
