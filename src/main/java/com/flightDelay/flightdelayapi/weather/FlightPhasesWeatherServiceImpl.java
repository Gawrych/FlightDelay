package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class FlightPhasesWeatherServiceImpl implements FlightPhasesWeatherService {

    private Weather weather;
    private int elevation;
    private final WindService windService;


    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public abstract DelayFactor calculateWindFactor();
    public abstract DelayFactor calculateRainFactor();
    public abstract DelayFactor calculateCloudHeightFactor();
    public abstract DelayFactor calculateVisibilityFactor();
}
