package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDtoMapper;
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
@DisplayName("Pre-departure remapping function")
class PreDepartureDelayRemappingImplTest {

    @Mock
    private PreDepartureDelayDtoMapper mapper;

    @InjectMocks
    private PreDepartureDelayRemappingImpl preDepartureDelayRemapping;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Nested
    @DisplayName("returns correct result when correctly remapped two dtos in one")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("by passes to mapper summed fields")
        void Apply_WhenGetValuesFromDtoFields_ThenPassThemToMapper() {
            // Given
            PreDepartureDelayDto firstDto = PreDepartureDelayDto.builder()
                    .numberOfDepartures(1.0d)
                    .delayInMinutes(5.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            PreDepartureDelayDto secondDto = PreDepartureDelayDto.builder()
                    .numberOfDepartures(1.0d)
                    .delayInMinutes(10.0d)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            // When
            preDepartureDelayRemapping.apply(firstDto, secondDto);

            // Then
            verify(mapper).mapFrom(any(LocalDate.class), doubleCaptor.capture(), doubleCaptor.capture());

            List<Double> actualValues = doubleCaptor.getAllValues();

            then(actualValues).isEqualTo(List.of(2.0d, 15.0d));
        }
    }
}