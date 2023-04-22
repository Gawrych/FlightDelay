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
        List<Runway> runways = runwayService.findByAirportIdent(flight.getAirportIdent());
        Weather weather = weatherAPIService.getWeather(flight.getAirportIdent(), flight.getDate().getTime());

        List<RunwayDto> runwayDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        AirportWeatherDto airportWeatherDto = modelMapper.map(weather, AirportWeatherDto.class);
        airportWeatherDto.setAirportIdent(flight.getAirportIdent());
        airportWeatherDto.setRunwaysDTO(runwayDto);
        return airportWeatherDto;
    }


}
