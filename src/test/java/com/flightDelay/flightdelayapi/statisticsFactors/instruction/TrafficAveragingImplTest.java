package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("The traffic averaging function")
class TrafficAveragingImplTest {

    @InjectMocks
    private TrafficAveragingImpl trafficAveraging;

    @Test
    @DisplayName("return total of traffic")
    void Apply_WhenGetValuesFromDtoFields_ThenPassThemToCalculator() {
        // Given
        TrafficDto dto = TrafficDto.builder()
                .total(10)
                .build();

        // When
        Double actualTotalTraffic = trafficAveraging.apply(dto);

        // Then
        then(actualTotalTraffic).isEqualTo(10.0d);
    }
}