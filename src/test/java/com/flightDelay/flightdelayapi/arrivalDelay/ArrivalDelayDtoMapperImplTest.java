package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Arrival delay dto mapper")
class ArrivalDelayDtoMapperImplTest {

    private ArrivalDelayDtoMapperImpl arrivalDelayDtoMapper;

    @BeforeEach
    void setUp() {
        arrivalDelayDtoMapper = new ArrivalDelayDtoMapperImpl();
    }

    @Test
    @DisplayName("Map - Correct delays map result")
    void Map_WhenPassArrivalDelayWithDelaysCauses_ThenReturnCorrectDelaysMap() {
        // Given
        ArrivalDelay arrivalDelay = getArrivalDelayExample();

        arrivalDelay.setDelayInMinutesCausedByAccident(10);
        arrivalDelay.setDelayInMinutesCausedByDeicing(5);

        Map<DelayCause, Integer> expectedDelays = Map.of(DelayCause.ACCIDENT, 10, DelayCause.DEICING, 5);

        // When
        ArrivalDelayDto actualDto = arrivalDelayDtoMapper.map(arrivalDelay);

        // Then
        Map<DelayCause, Integer> actualDelays = actualDto.getDelays();

        then(actualDelays).isEqualTo(expectedDelays);
    }

    @Test
    @DisplayName("Map - Correct ArrivalDelayDto result")
    void Map_WhenPassArrivalDelay_ThenReturnMappedArrivalDelayDto() {
        // Given
        ArrivalDelay arrivalDelay = getArrivalDelayExample();

        ArrivalDelayDto expectedDto = getArrivalDelayDtoExample();

        // When
        ArrivalDelayDto actualDto = arrivalDelayDtoMapper.map(arrivalDelay);

        // Then
        then(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("MapFromList - Correct ArrivalDelayDto list result")
    void MapFromList_WhenPassArrivalDelaysInList_ThenReturnMappedArrivalDelayDtos() {
        // Given
        List<ArrivalDelay> arrivalDelayList = List.of(getArrivalDelayExample(), getArrivalDelayExample());

        List<ArrivalDelayDto> expectedArrivalDelayList = List.of(getArrivalDelayDtoExample(), getArrivalDelayDtoExample());

        // When
        List<ArrivalDelayDto> actualArrivalDelayList = arrivalDelayDtoMapper.mapFromList(arrivalDelayList);

        // Then
        then(actualArrivalDelayList).usingRecursiveComparison().isEqualTo(expectedArrivalDelayList);
    }

    private static ArrivalDelayDto getArrivalDelayDtoExample() {
        return ArrivalDelayDto.builder()
                .date(LocalDate.of(1970, 1, 1))
                .numberOfDelayedArrivals(5)
                .numberOfDelayedArrivals(10)
                .minutesOfAirportDelay(15)
                .delays(Map.of())
                .build();
    }

    private static ArrivalDelay getArrivalDelayExample() {
        return ArrivalDelay.builder()
                .generatedId("Id")
                .year(1)
                .month(1)
                .date(LocalDate.of(1970, 1, 1))
                .airportCode("AAAA")
                .numberOfDelayedArrivals(5)
                .numberOfDelayedArrivals(10)
                .minutesOfAirportDelay(15)
                .build();
    }
}