package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pre-departure remapping function")
class PreDepartureDelayRemappingImplTest {

    @Mock
    private PreDepartureDelayDtoMapper mapper;

    private PreDepartureDelayRemappingImpl preDepartureDelayRemapping;

    @BeforeEach
    void setUp() {
        this.preDepartureDelayRemapping = new PreDepartureDelayRemappingImpl(mapper);
    }

    @Nested
    @DisplayName("returns correct result when correctly remapped two dtos in one")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("summing the same fields")
        void CalculateTopDayDelay_WhenPassValidList_ThenReturnCorrectResult() {
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

            // TODO: change it to captor
            // Then
            verify(mapper).mapFrom(secondDto.getDate(), 2.0d, 15.0d);
        }
    }
}