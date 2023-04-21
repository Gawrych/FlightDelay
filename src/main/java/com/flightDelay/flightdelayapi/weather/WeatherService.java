package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.flight.Flight;

import java.util.Date;
import java.util.List;

public interface WeatherService {

    void setWeather(String airportIdent, long date);

    void setAirport(Airport airport);

    int getIlsCategory();

    int getCrossWind();

    int getHeadWind();
}
