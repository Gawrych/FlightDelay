package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDtoMapper;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeStage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("The additional time remapping function")
class AdditionalTimeRemappingImplTest {

    @Mock
    private AdditionalTimeDtoMapper mapper;

    @InjectMocks
    private AdditionalTimeRemappingImpl additionalTimeRemapping;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Nested
    @DisplayName("returns correct result when correctly remapped two dtos in one")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("when sums additional time")
        void Apply_WhenGetValuesFromDtoFields_ThenPassThemToMapper() {
            // Given
            AdditionalTimeDto firstDto = AdditionalTimeDto.builder()
                    .totalFlight(0)
                    .totalAdditionalTimeInMinutes(5.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            AdditionalTimeDto secondDto = AdditionalTimeDto.builder()
                    .totalFlight(0)
                    .totalAdditionalTimeInMinutes(5.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            // When
            additionalTimeRemapping.apply(firstDto, secondDto);

            // Then
            verify(mapper).mapFrom(any(
                    LocalDate.class),
                    any(AdditionalTimeStage.class),
                    doubleCaptor.capture(),
                    doubleCaptor.capture());

            List<Double> actualValues = doubleCaptor.getAllValues();

            then(actualValues).isEqualTo(List.of(0.0d, 10.0d));
        }

        @Test
        @DisplayName("when calculates total flight average")
        void Apply_WhenGetValuesFromDtoFields_ThenCalculateTotalFlightAverage() {
            // Given
            AdditionalTimeDto firstDto = AdditionalTimeDto.builder()
                    .totalFlight(10.0d)
                    .totalAdditionalTimeInMinutes(0.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            AdditionalTimeDto secondDto = AdditionalTimeDto.builder()
                    .totalFlight(10.0d)
                    .totalAdditionalTimeInMinutes(0.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            // When
            additionalTimeRemapping.apply(firstDto, secondDto);

            // Then
            verify(mapper).mapFrom(any(
                            LocalDate.class),
                    any(AdditionalTimeStage.class),
                    doubleCaptor.capture(),
                    doubleCaptor.capture());

            List<Double> actualValues = doubleCaptor.getAllValues();

            then(actualValues).isEqualTo(List.of(10.0d, 0.0d));
        }
    }
}