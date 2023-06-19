package com.flightDelay.flightdelayapi.weatherFactors;

import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.InstrumentLandingSystemCalculatorImpl;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.RunwayWeatherCalculatorImpl;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.exception.InstrumentLandingSystemCalculationFailedException;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("Instrument landing system calculator")
class InstrumentLandingSystemCalculatorImplTest {

    @Mock
    private RunwayWeatherCalculatorImpl runwayWeatherCalculator;

    private AirportWeatherDto airportWeatherDto;

    private InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculatorImpl;

    @BeforeEach
    void setUp() {
        instrumentLandingSystemCalculatorImpl = new InstrumentLandingSystemCalculatorImpl(runwayWeatherCalculator);

        airportWeatherDto = new AirportWeatherDto();
        airportWeatherDto.setRunwaysDTO(List.of(mock(RunwayWeatherDto.class)));
        airportWeatherDto.setWeather(mock(Weather.class));
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

        @Test
        @DisplayName("when not valid conditions are passed")
        void GetMinRequiredCategory_WhenConditionsAreNotValid_thenThrowException() {
            // Given
            int cloudBase = -1;
            int rvr = -1;

            given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase);
            given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr);

            // When
            Throwable throwable = catchThrowable(() ->
                    instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto));

            // Then
            then(throwable)
                    .isInstanceOf(InstrumentLandingSystemCalculationFailedException.class)
                    .hasMessage("error.message.failedToCalculateInstrumentLandingSystem");
        }
    }

    @Nested
    @DisplayName("returns correct ils category")
    class ReturnsCorrectResult {

        @ParameterizedTest(name = "{index} : Matching {0} limits to ils category")
        @EnumSource(IlsCategory.class)
        @DisplayName("when conditions match to ils category limits")
        void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory_thenReturnIlsCategory(
                IlsCategory expectedIlsCategory) {

            // Given
            int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
            int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

            given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
            given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

            // When
            IlsCategory actualIlsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

            // Then
            then(actualIlsCategory).isEqualTo(expectedIlsCategory);
        }
    }
}
