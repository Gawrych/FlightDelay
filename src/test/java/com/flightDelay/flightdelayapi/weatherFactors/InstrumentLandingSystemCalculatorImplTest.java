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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("InstrumentLandingSystemCalculator Tests")
class InstrumentLandingSystemCalculatorImplTest {

    private AirportWeatherDto airportWeatherDto;

    @Mock
    private RunwayWeatherCalculatorImpl runwayWeatherCalculator;

    private InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculatorImpl;

    @BeforeEach
    void setUp() {
        instrumentLandingSystemCalculatorImpl = new InstrumentLandingSystemCalculatorImpl(runwayWeatherCalculator);

        airportWeatherDto = new AirportWeatherDto();
        airportWeatherDto.setRunwaysDTO(List.of(mock(RunwayWeatherDto.class)));
        airportWeatherDto.setWeather(mock(Weather.class));
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Not valid conditions")
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

    @Test
    @DisplayName("GetMinRequiredCategory - Nonprecision Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory0_thenReturnIlsCategory0() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.NONPRECISION;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Category 1 Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory1_thenReturnIlsCategory1() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.CATEGORY_1;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Category 2 Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory2_thenReturnIlsCategory2() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.CATEGORY_2;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Category 3A Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory3A_thenReturnIlsCategory3A() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.CATEGORY_3A;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Category 3B Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory3B_thenReturnIlsCategory3B() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.CATEGORY_3B;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }

    @Test
    @DisplayName("GetMinRequiredCategory - Category 3C Ils")
    void GetMinRequiredCategory_WhenConditionsAreEnoughForIlsCategory3C_thenReturnIlsCategory3C() {
        // Given
        IlsCategory expectedIlsCategory = IlsCategory.CATEGORY_3C;

        int cloudBase = UnitConverter.feetToMeters(expectedIlsCategory.getCloudBaseThresholdFt());
        int rvr = UnitConverter.feetToMeters(expectedIlsCategory.getRunwayVisualRangeThresholdFt());

        given(runwayWeatherCalculator.calculateCloudBase(anyFloat(), anyFloat(), anyInt())).willReturn(cloudBase + 1);
        given(runwayWeatherCalculator.calculateRunwayVisualRange(anyFloat(), anyBoolean())).willReturn(rvr + 1);

        // When
        IlsCategory ilsCategory = instrumentLandingSystemCalculatorImpl.getMinRequiredCategory(airportWeatherDto);

        // Then
        then(ilsCategory).isEqualTo(expectedIlsCategory);
    }
}
