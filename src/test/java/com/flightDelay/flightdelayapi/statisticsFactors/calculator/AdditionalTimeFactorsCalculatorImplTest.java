package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.AdditionalTimeDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;


@ExtendWith(MockitoExtension.class)
@DisplayName("AdditionalTimeFactorsCalculator Tests")
class AdditionalTimeFactorsCalculatorImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @Mock
    private TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    @Mock
    private Function<AdditionalTimeDto, Double> functionAveraging;

    @Mock
    private BinaryOperator<AdditionalTimeDto> binaryOperatorRemapping;

    @Captor
    private ArgumentCaptor<List<AdditionalTimeDto>> captor;

    private AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    @BeforeEach
    void setUp() {
        additionalTimeFactorsCalculator = new AdditionalTimeFactorsCalculatorImpl(
                averageFactorCalculator,
                topDtoFactorCalculator,
                functionAveraging,
                binaryOperatorRemapping);
    }

    @Test
    @DisplayName("CalculateAverageFromList - Pass input list to average calculator")
    void CalculateAverageFromList_WhenPassValidList_ThenPassThisListToAverageFactorCalculator() {
        // Given
        List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

        // When
        additionalTimeFactorsCalculator.calculateAverageFromList(list);

        // Then
        verify(averageFactorCalculator).calculateAverageByDtoList(captor.capture(), any(), any());

        List<AdditionalTimeDto> capturedList = captor.getValue();

        then(capturedList).isEqualTo(list);
    }

    @Test
    @DisplayName("CalculateAverageFromList - Valid list parameter")
    void CalculateAverageFromList_WhenPassValidListAsAParameter_ThenReturnResultFromAverageCalculator() {
        // Given
        List<AdditionalTimeDto> validList = List.of(new AdditionalTimeDto());

        double expectedValue = 1.0d;
        given(averageFactorCalculator.calculateAverageByDtoList(anyList(), any(), any())).willReturn(expectedValue);

        // When
        double actualValue = additionalTimeFactorsCalculator.calculateAverageFromList(validList);

        // Then
        then(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateAverageFromList - Valid list parameter")
    void CalculateAverageFromList_WhenPassValidListAsAParameter_ThenNotThrowException() {
        // Given
        List<AdditionalTimeDto> notEmptyList = List.of(new AdditionalTimeDto());

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateAverageFromList(notEmptyList));

        // Then
        then(throwable).isNull();
        verify(averageFactorCalculator).calculateAverageByDtoList(anyList(), any(), any());
    }

    @Test
    @DisplayName("CalculateAverageFromList - Empty list parameter")
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
    @DisplayName("CalculateAverageFromList - Null list parameter")
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
    @DisplayName("CalculateTopDelayMonth - Input list in Top Dto calculator")
    void CalculateTopDelayMonth_WhenPassValidListAsAParameter_ThenPassThisListToTopDtoFactorCalculator() {
        // Given
        List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

        AdditionalTimeDto exampleDto = getAdditionalTimeDtoExample();

        given(functionAveraging.apply(any(AdditionalTimeDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

        // When
        additionalTimeFactorsCalculator.calculateTopDelayMonth(list);

        // Then
        verify(topDtoFactorCalculator).getTopMonthDto(captor.capture(), any(), any());

        List<AdditionalTimeDto> capturedList = captor.getValue();

        then(capturedList).isEqualTo(list);
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Correct result")
    void CalculateTopDelayMonth_WhenPassValidListAsAParameter_ThenReturnCorrectResult() {
        // Given
        List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

        AdditionalTimeDto exampleDto = getAdditionalTimeDtoExample();

        given(functionAveraging.apply(any(AdditionalTimeDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

        ValueWithDateHolder expectedValue = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 10.0d);

        // When
        ValueWithDateHolder actualValue = additionalTimeFactorsCalculator.calculateTopDelayMonth(list);

        // Then
        then(actualValue).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Valid list parameter")
    void CalculateTopDelayMonth_WhenPassValidListAsAParameter_ThenNotThrowException() {
        // Given
        List<AdditionalTimeDto> notEmptyList = List.of(new AdditionalTimeDto());

        // When
        final Throwable throwable = catchThrowable(() ->
                additionalTimeFactorsCalculator.calculateTopDelayMonth(notEmptyList));

        // Then
        then(throwable).isNotInstanceOf(AdditionalTimeDataNotFoundException.class);
        verify(topDtoFactorCalculator).getTopMonthDto(anyList(), any(), any());
    }

    @Test
    @DisplayName("CalculateTopDelayMonth - Empty list parameter")
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
    @DisplayName("CalculateTopDelayMonth - Null list parameter")
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

    private static AdditionalTimeDto getAdditionalTimeDtoExample() {
        return AdditionalTimeDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}