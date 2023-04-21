package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherAPIService weatherAPIService;
    private final InstrumentLandingSystemService instrumentLandingSystemService;
    private Weather weather;
    private Airport airport;

    public void setWeather(String airportIdent, long time) {
        this.weather = weatherAPIService.getWeather(airportIdent, time);
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public int getIlsCategory() {
//        TODO: Create assertion
        return instrumentLandingSystemService.getCategory(weather, airport.getElevationFt());
    }

    public int getCrossWind() {
        return 0;
    }

    public int getHeadWind() {
        return 0;
    }
}
