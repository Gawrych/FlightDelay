package com.flightDelay.flightdelayapi.region;

import lombok.RequiredArgsConstructor;
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
