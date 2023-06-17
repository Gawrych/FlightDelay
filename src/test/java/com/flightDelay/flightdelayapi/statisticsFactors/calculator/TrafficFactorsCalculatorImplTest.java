package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.exception.resource.TrafficDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Traffic factors calculator")
class TrafficFactorsCalculatorImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @Mock
    private TopDtoFactorCalculator<TrafficDto> topDtoFactorCalculator;

    @Mock
    private Function<TrafficDto, Double> trafficAveraging;

    @Mock
    private BinaryOperator<TrafficDto> trafficRemapping;

    @Captor
    private ArgumentCaptor<List<TrafficDto>> listCaptor;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    private TrafficFactorsCalculatorImpl trafficFactorsCalculator;

    @BeforeEach
    void setUp() {
        this.trafficFactorsCalculator = new TrafficFactorsCalculatorImpl(
                averageFactorCalculator,
                topDtoFactorCalculator,
                trafficAveraging,
                trafficRemapping);
    }

    @Nested
    @DisplayName("returns value with data holder")
    class ReturnsCorrectResult {

        @Test
        @DisplayName("created based on result from top dto calculator ")
        void CalculateTopMonth_WhenPassValidList_ThenReturnCorrectResult() {
            // Given
            List<TrafficDto> expectedList = List.of(new TrafficDto());

            TrafficDto exampleDto = getTrafficDtoExample();

            given(trafficAveraging.apply(any(TrafficDto.class))).willReturn(10.0d);
            given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

            ValueWithDateHolder expectedValue = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 10.0d);

            // When
            ValueWithDateHolder actualValue = trafficFactorsCalculator.calculateTopMonth(expectedList);

            // Then
            then(actualValue).usingRecursiveComparison().isEqualTo(expectedValue);
        }
    }

    @Nested
    @DisplayName("pass input list to external")
    class PassInputListToExternal {

        @Test
        @DisplayName("top dto calculator to sum dtos in the same months")
        void CalculateAverageMonthly_WhenPassValidList_ThenPassThisListToTopDtoFactorCalculator() {
            // Given
            List<TrafficDto> expectedList = List.of(new TrafficDto());

            // When
            trafficFactorsCalculator.calculateAverageMonthly(expectedList);

            // Then
            verify(topDtoFactorCalculator).sumDtosInTheSameMonths(listCaptor.capture(), any());

            List<TrafficDto> capturedList = listCaptor.getValue();

            then(capturedList).isEqualTo(expectedList);
        }

        @Test
        @DisplayName("top dto calculator to find top month")
        void CalculateTopMonth_WhenPassValidList_ThenPassThisListToTopDtoFactorCalculator() {
            // Given
            List<TrafficDto> expectedList = List.of(new TrafficDto());

            TrafficDto exampleDto = getTrafficDtoExample();

            given(trafficAveraging.apply(any(TrafficDto.class))).willReturn(10.0d);
            given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

            // When
            trafficFactorsCalculator.calculateTopMonth(expectedList);

            // Then
            verify(topDtoFactorCalculator).getTopMonthDto(listCaptor.capture(), any(), any());

            List<TrafficDto> capturedList = listCaptor.getValue();

            then(capturedList).isEqualTo(expectedList);
        }
    }

    @Nested
    @DisplayName("pass calculated values to external")
    class PassCalculatedValuesToExternal {

        @Test
        @DisplayName("average calculator for calculate average monthly traffic")
        void CalculateAverageMonthly_WhenCalculateDataCorrectly_ThenPassCalculatedValuesToAverageCalculator() {
            // Given
            List<TrafficDto> expectedList = List.of(new TrafficDto());

            Map<Month, TrafficDto> mergedValues = Map.of(
                    Month.of(1), TrafficDto.builder().total(10).build(),
                    Month.of(2), TrafficDto.builder().total(20).build());

            given(topDtoFactorCalculator.sumDtosInTheSameMonths(anyList(), any())).willReturn(mergedValues);

            // When
            trafficFactorsCalculator.calculateAverageMonthly(expectedList);

            // Then
            verify(averageFactorCalculator).calculateAverage(doubleCaptor.capture(), doubleCaptor.capture());

            double actualSumOfAllTotalField = doubleCaptor.getAllValues().get(0);
            double actualAmountOfMonths = doubleCaptor.getAllValues().get(1);

            then(actualSumOfAllTotalField).isEqualTo(30);
            then(actualAmountOfMonths).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

        @ParameterizedTest(name = "{index} : is {0}")
        @NullAndEmptySource
        @DisplayName("when input list in method calculating top month")
        void CalculateTopMonth_WhenPassNullOrEmptyList_ThenThrowException(List<TrafficDto> nullOrEmptyList) {
            // When
            Throwable throwable = catchThrowable(() ->
                    trafficFactorsCalculator.calculateTopMonth(nullOrEmptyList));

            // Then
            then(throwable)
                    .isInstanceOf(TrafficDataNotFoundException.class)
                    .hasMessage("error.message.trafficDataNotFound");
        }

        @ParameterizedTest(name = "{index} : is {0}")
        @NullAndEmptySource
        @DisplayName("when input list in method calculating monthly average")
        void CalculateAverageMonthly_WhenPassNullOrEmptyList_ThenThrowException(List<TrafficDto> nullOrEmptyList) {
            // When
            Throwable throwable = catchThrowable(() ->
                    trafficFactorsCalculator.calculateAverageMonthly(nullOrEmptyList));

            // Then
            then(throwable)
                    .isInstanceOf(TrafficDataNotFoundException.class)
                    .hasMessage("error.message.trafficDataNotFound");
        }
    }

    private static TrafficDto getTrafficDtoExample() {
        return TrafficDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}