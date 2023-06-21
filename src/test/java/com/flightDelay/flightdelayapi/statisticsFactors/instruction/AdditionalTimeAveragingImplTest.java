package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AverageFactorCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("The additional time averaging function")
class AdditionalTimeAveragingImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @InjectMocks
    private AdditionalTimeAveragingImpl additionalTimeAveraging;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Nested
    @DisplayName("returns average value from additional time dto")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("by passes values from dto fields to average calculator")
        void Apply_WhenGetValuesFromDtoFields_ThenPassThemToCalculator() {
            // Given
            given(averageFactorCalculator.calculateAverage(anyDouble(), anyDouble())).willReturn(10.0d);

            AdditionalTimeDto dto = AdditionalTimeDto.builder()
                    .totalAdditionalTimeInMinutes(10.0d)
                    .totalFlight(1.0d)
                    .build();

            // When
            additionalTimeAveraging.apply(dto);

            // Then
            verify(averageFactorCalculator).calculateAverage(doubleCaptor.capture(), doubleCaptor.capture());

            List<Double> actualValues = doubleCaptor.getAllValues();

            then(actualValues).isEqualTo(List.of(10.0d, 1.0d));
        }

        @Test
        @DisplayName("when returns result from average calculator")
        void Apply_WhenCalculatorCalculateAverage_ThenReturnItsResult() {
            // Given
            double expectedValue = 10.0d;
            given(averageFactorCalculator.calculateAverage(anyDouble(), anyDouble())).willReturn(expectedValue);

            AdditionalTimeDto dto = AdditionalTimeDto.builder()
                    .totalAdditionalTimeInMinutes(10.0d)
                    .totalFlight(1.0d)
                    .build();

            // When
            Double actualResult = additionalTimeAveraging.apply(dto);

            // Then
            then(actualResult).isEqualTo(expectedValue);
        }
    }
}