package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("The wind calculator")
class WindCalculatorImplTest {

    @InjectMocks
    private WindCalculatorImpl windCalculator;

    @ParameterizedTest(name = "{index} : For windDirection:{0}, windSpeed:{1}, heHeadingDegT:{2} result is {3}")
    @CsvSource(value = {"50:50:100:39", "10:20:30:7"}, delimiter = ':')
    @DisplayName("returns correctly calculated cross wind")
    void GetCrossWind_WhenGetValidData_ThenCalculateRunwayVisualRange(int windDirection,
                                                                      float windSpeed,
                                                                      int heHeadingDegT,
                                                                      int expectedResult) {
        // Given
        Weather weather = Weather.builder()
                .windDirection(windDirection)
                .windSpeedKn(windSpeed)
                .build();

        AirportWeatherDto airportWeatherDto = AirportWeatherDto.builder()
                .weather(weather)
                .runwaysDTO(List.of(RunwayWeatherDto.builder().heHeadingDegT(heHeadingDegT).build()))
                .build();

        // When
        int actualResult = windCalculator.getCrossWind(airportWeatherDto);

        // Then
        then(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{index} : For windDirection:{0}, windSpeed:{1}, heHeadingDegT:{2} result is {3}")
    @CsvSource(value = {"50:50:100:33", "10:20:30:19"}, delimiter = ':')
    @DisplayName("returns correct calculated tailwind")
    void GetTailwind_WhenGetValidData_ThenCalculateRunwayVisualRange(int windDirection,
                                                                     float windSpeed,
                                                                     int heHeadingDegT,
                                                                     int expectedResult) {
        // Given
        Weather weather = Weather.builder()
                .windDirection(windDirection)
                .windSpeedKn(windSpeed)
                .build();

        AirportWeatherDto airportWeatherDto = AirportWeatherDto.builder()
                .weather(weather)
                .runwaysDTO(List.of(RunwayWeatherDto.builder().heHeadingDegT(heHeadingDegT).build()))
                .build();

        // When
        int actualResult = windCalculator.getTailwind(airportWeatherDto);

        // Then
        then(actualResult).isEqualTo(expectedResult);
    }
}