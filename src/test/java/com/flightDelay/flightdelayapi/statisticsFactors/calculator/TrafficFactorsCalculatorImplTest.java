package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.exception.resource.TrafficDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("TrafficFactorsCalculator Tests")
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

    @Test
    @DisplayName("CalculateTopMonth - Correct result")
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

    @Test
    @DisplayName("CalculateTopMonth - Pass input list to top dto calculator")
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

    @Test
    @DisplayName("CalculateTopMonth - Valid input")
    void CalculateTopMonth_WhenPassValidList_ThenNotThrowException() {
        // Given
        List<TrafficDto> list = List.of(new TrafficDto());

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateTopMonth(list));

        // Then
        then(throwable)
                .isNotInstanceOf(TrafficDataNotFoundException.class);
    }

    @Test
    @DisplayName("CalculateTopMonth - Empty list")
    void CalculateTopMonth_WhenPassEmptyList_ThenThrowException() {
        // Given
        List<TrafficDto> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateTopMonth(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(TrafficDataNotFoundException.class)
                .hasMessage("error.message.trafficDataNotFound");
    }

    @Test
    @DisplayName("CalculateTopMonth - Null list")
    void CalculateTopMonth_WhenPassNullList_ThenThrowException() {
        // Given
        List<TrafficDto> emptyList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateTopMonth(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(TrafficDataNotFoundException.class)
                .hasMessage("error.message.trafficDataNotFound");
    }

    @Test
    @DisplayName("CalculateAverageMonthly - Calculated values pass to average calculator")
    void CalculateAverageMonthly_WhenPassValidList_ThenPassCalculatedValuedToAverageCalculator() {
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

    @Test
    @DisplayName("CalculateAverageMonthly - Input list pass to top dto calculator")
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
    @DisplayName("CalculateAverageMonthly - Valid input")
    void CalculateAverageMonthly_WhenPassValidList_ThenNotThrowException() {
        // Given
        List<TrafficDto> list = List.of(new TrafficDto());

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateAverageMonthly(list));

        // Then
        then(throwable).isNull();
    }

    @Test
    @DisplayName("CalculateAverageMonthly - Empty list")
    void CalculateAverageMonthly_WhenPassEmptyList_ThenThrowException() {
        // Given
        List<TrafficDto> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateAverageMonthly(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(TrafficDataNotFoundException.class)
                .hasMessage("error.message.trafficDataNotFound");
    }

    @Test
    @DisplayName("CalculateAverageMonthly - Null list")
    void CalculateAverageMonthly_WhenPassNullList_ThenThrowException() {
        // Given
        List<TrafficDto> emptyList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                trafficFactorsCalculator.calculateAverageMonthly(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(TrafficDataNotFoundException.class)
                .hasMessage("error.message.trafficDataNotFound");
    }

    private static TrafficDto getTrafficDtoExample() {
        return TrafficDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}