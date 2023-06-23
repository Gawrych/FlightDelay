package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("The runway weather calculator")
class RunwayWeatherCalculatorImplTest {

    @InjectMocks
    private RunwayWeatherCalculatorImpl runwayWeatherCalculator;

    @ParameterizedTest(name = "{index} : is Day?: {1} For visibility: {0} should return {2}")
    @CsvSource(value = {"10:true:13", "10:false:16", "30:true:39", "30:false:48"}, delimiter = ':')
    @DisplayName("returns correctly calculated runway visual range")
    void CalculateRunwayVisualRange_WhenGetValidData_ThenCalculateRunwayVisualRange(float visibility,
                                                                                    boolean isDay,
                                                                                    int expectedResult) {
        // When
        int actualResult = runwayWeatherCalculator.calculateRunwayVisualRange(visibility, isDay);

        // Then
        then(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{index} : For visibility: {0} should throw an exception")
    @ValueSource(ints = {0, -1})
    @DisplayName("throws an exception when visibility is lower than 1")
    void CalculateRunwayVisualRange_WhenVisibilityIsLowerThan1_ThenTrowAnException(float visibility) {
        // When
        final Throwable throwable = catchThrowable(() ->
                runwayWeatherCalculator.calculateRunwayVisualRange(visibility, true));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToIncorrectDataException.class)
                .hasMessage("error.message.unableToCalculateDueToIncorrectDataException");
    }

    @ParameterizedTest(name = "{index} : for temperature:{0} and dew point:{1} and elevation:{2} result is {3}")
    @CsvSource(value = {"20:10:753:2000", "30:10:506:3000"}, delimiter = ':')
    @DisplayName("returns correct calculated cloud base")
    void CalculateCloudBase_WhenGetValidData_ThenCalculateCalculateCloudBase(float temperature,
                                                                             float dewPoint,
                                                                             int elevation,
                                                                             int expectedResult) {
        // When
        int actualResult = runwayWeatherCalculator.calculateCloudBase(temperature, dewPoint, elevation);

        // Then
        then(actualResult).isEqualTo(expectedResult);
    }
}