package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportWeatherServiceImpl implements AirportWeatherService {

    private final WeatherAPIService weatherAPIService;
    private final RunwayService runwayService;
    private final ModelMapper modelMapper;

    public AirportWeatherDto getAirportWeather(Flight flight) {
        List<Runway> runways = runwayService.findByAirportIdent(flight.airportIdent());
        Weather weather = weatherAPIService.getWeather(flight.airportIdent(), flight.date().getTime());

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        AirportWeatherDto airportWeatherDto = modelMapper.map(weather, AirportWeatherDto.class);
        airportWeatherDto.setAirportIdent(flight.airportIdent());
        airportWeatherDto.setRunwaysDTO(runwaysDto);
        return airportWeatherDto;
    }


}
