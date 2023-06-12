package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.AdditionalTimeDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("AdditionalTimeFactorsCalculator Tests")
class AdditionalTimeFactorsCalculatorImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @Mock
    private TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    @Mock
    private Function<AdditionalTimeDto, Double> additionalTimeAveraging;

    @Mock
    private BinaryOperator<AdditionalTimeDto> additionalTimeRemapping;

    private AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    @BeforeEach
    void setUp() {
        additionalTimeFactorsCalculator = new AdditionalTimeFactorsCalculatorImpl(
                averageFactorCalculator,
                topDtoFactorCalculator,
                additionalTimeAveraging,
                additionalTimeRemapping);
    }

    @Test
    @DisplayName("CalculateAverageFromList - Valid List")
    void CalculateAverageFromList_WhenPassValidListAsAParameter_ThenReturnResultFromAverageCalculator() {
        // Given
        List<AdditionalTimeDto> validList = List.of(new AdditionalTimeDto());

        given(averageFactorCalculator.calculateAverageByDtoList(any(), any(), any()))
                .willReturn(1.0d);

        // When
        double actualValue = additionalTimeFactorsCalculator.calculateAverageFromList(validList);

        // Then
        then(actualValue).isEqualTo(1.0d);
        verify(averageFactorCalculator).calculateAverageByDtoList(any(), any(), any());
    }

    @Test
    @DisplayName("CalculateAverageFromList - Valid List")
    void CalculateAverageFromList_WhenPassValidListAsAParameter_ThenNotThrowException() {
        // Given
        List<AdditionalTimeDto> notEmptyList = List.of(new AdditionalTimeDto());

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateAverageFromList(notEmptyList));

        // Then
        then(throwable).isNull();
        verify(averageFactorCalculator).calculateAverageByDtoList(any(), any(), any());
    }

    @Test
    @DisplayName("CalculateAverageFromList - Empty List")
    void CalculateAverageFromList_WhenPassEmptyListAsAParameter_ThenThrowException() {
        // Given
        List<AdditionalTimeDto> emptyList = List.of();

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateAverageFromList(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                .hasMessage("error.message.additionalTimeDataNotFound");

        verifyNoInteractions(topDtoFactorCalculator);
    }


    @Test
    @DisplayName("CalculateAverageFromList - Null List")
    void CalculateAverageFromList_WhenPassNullListAsAParameter_ThenThrowException() {
        // Given
        List<AdditionalTimeDto> nullList = null;

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateAverageFromList(nullList));

        // Then
        then(throwable)
                .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                .hasMessage("error.message.additionalTimeDataNotFound");

        verifyNoInteractions(topDtoFactorCalculator);
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Valid List")
    void CalculateTopDelayMonth_WhenPassValidListAsAParameter_ThenReturnResultFromTopDtoFactorCalculator() {
        // Given
        List<AdditionalTimeDto> validList = List.of(new AdditionalTimeDto());
        ValueWithDateHolder expectedObject = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 1.0d);

        given(topDtoFactorCalculator.getTopMonthDto(any(), any(), any()))
                .willReturn(expectedObject);

        // When
        ValueWithDateHolder actualObject = additionalTimeFactorsCalculator.calculateTopDelayMonth(validList);

        // Then
        then(actualObject).isEqualTo(expectedObject);
        verify(topDtoFactorCalculator).getTopMonthDto(any(), any(), any());
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Valid List")
    void CalculateTopDelayMonth_WhenPassValidListAsAParameter_ThenNotThrowException() {
        // Given
        List<AdditionalTimeDto> notEmptyList = List.of(new AdditionalTimeDto());

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateTopDelayMonth(notEmptyList));

        // Then
        then(throwable).isNull();
        verify(topDtoFactorCalculator).getTopMonthDto(any(), any(), any());
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Empty List")
    void CalculateTopDelayMonth_WhenPassEmptyListAsAParameter_ThenThrowException() {
        // Given
        List<AdditionalTimeDto> emptyList = List.of();

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateTopDelayMonth(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                .hasMessage("error.message.additionalTimeDataNotFound");

        verifyNoInteractions(topDtoFactorCalculator);
    }


    @Test
    @DisplayName("CalculateTopDelayMonth - Null List")
    void CalculateTopDelayMonth_WhenPassNullListAsAParameter_ThenThrowException() {
        // Given
        List<AdditionalTimeDto> nullList = null;

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateTopDelayMonth(nullList));

        // Then
        then(throwable)
                .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                .hasMessage("error.message.additionalTimeDataNotFound");

        verifyNoInteractions(topDtoFactorCalculator);
    }
}