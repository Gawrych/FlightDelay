package com.flightDelay.flightdelayapi.weather;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherRestServiceImpl implements WeatherRestService {
    private static final String url = "https://api.open-meteo.com/v1/forecast?latitude=52.23&longitude=21.01&hourly=temperature_2m,windspeed_10m,winddirection_10m&forecast_days=1&start_date=2023-03-15&end_date=2023-03-15";
    private final RestTemplate restTemplate;

    public WeatherRestServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public WeatherDetails getWeatherDetails() {
        return this.restTemplate.getForObject(url, WeatherDetails.class);
    }
}
