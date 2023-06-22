package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import com.flightDelay.flightdelayapi.traffic.TrafficDtoMapper;
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
@DisplayName("The traffic averaging function")
class TrafficRemappingImplTest {

    @Mock
    private TrafficDtoMapper mapper;

    @InjectMocks
    private TrafficRemappingImpl trafficRemapping;

    @Captor
    private ArgumentCaptor<Integer> integerCaptor;

    @Nested
    @DisplayName("returns correct result when correctly remapped two dtos in one")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("by sums traffic fields")
        void Apply_WhenGetsTrafficFieldsValues_ThenSumThem() {
            // Given
            TrafficDto firstDto = TrafficDto.builder()
                    .departures(1)
                    .arrivals(2)
                    .total(3)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            TrafficDto secondDto = TrafficDto.builder()
                    .departures(1)
                    .arrivals(2)
                    .total(3)
                    .date(LocalDate.ofEpochDay(1L))
                    .build();

            // When
            trafficRemapping.apply(firstDto, secondDto);

            // Then
            verify(mapper).mapFrom(
                    any(LocalDate.class),
                    integerCaptor.capture(),
                    integerCaptor.capture(),
                    integerCaptor.capture());

            List<Integer> actualValues = integerCaptor.getAllValues();

            then(actualValues).isEqualTo(List.of(2, 4, 6));
        }
    }
}