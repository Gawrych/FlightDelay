package com.flightDelay.flightdelayapi.controller;

import com.flightDelay.flightdelayapi.repository.RegionRepository;
import com.flightDelay.flightdelayapi.service.RegionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final RegionServiceImpl regionService;

    @DeleteMapping
    public String removeAll() {
        regionService.deleteAll();
        return "DELETED";
    }
}
