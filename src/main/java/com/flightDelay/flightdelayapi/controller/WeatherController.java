package com.flightDelay.flightdelayapi.controller;

import com.flightDelay.flightdelayapi.model.WeatherDetails;
import com.flightDelay.flightdelayapi.service.WeatherRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherRestService weatherRestService;

    @GetMapping
    public String getWeather(ModelMap model) {
        return "weatherDetails";
    }
}
