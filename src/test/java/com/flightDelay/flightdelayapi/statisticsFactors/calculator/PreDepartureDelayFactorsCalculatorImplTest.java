package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("PreDepartureDelayFactorsCalculator Tests")
class PreDepartureDelayFactorsCalculatorImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @Mock
    private TopDtoFactorCalculator<PreDepartureDelayDto> topDtoFactorCalculator;

    @Mock
    private Function<PreDepartureDelayDto, Double> preDepartureDelayAveraging;

    @Mock
    private BinaryOperator<PreDepartureDelayDto> preDepartureDelayRemapping;

    @Captor
    private ArgumentCaptor<List<PreDepartureDelayDto>> captor;

    private PreDepartureDelayFactorsCalculatorImpl preDepartureDelayFactorsCalculator;

    @BeforeEach
    void setUp() {
        this.preDepartureDelayFactorsCalculator = new PreDepartureDelayFactorsCalculatorImpl(
                averageFactorCalculator,
                topDtoFactorCalculator,
                preDepartureDelayAveraging,
                preDepartureDelayRemapping);
    }

    @Test
    @DisplayName("CalculateAverageDelayTime - Pass input list to average calculator")
    void CalculateAverageDelayTime_WhenPassValidList_ThenPassThisListToAverageFactorCalculator() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        // When
        preDepartureDelayFactorsCalculator.calculateAverageDelayTime(list);

        // Then
        verify(averageFactorCalculator).calculateAverageByDtoList(captor.capture(), any(), any());

        List<PreDepartureDelayDto> capturedList = captor.getValue();

        then(capturedList).isEqualTo(list);
    }

    @Test
    @DisplayName("CalculateAverageDelayTime - Valid input")
    void CalculateAverageDelayTime_WhenPassValidList_ThenNotThrowException() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateAverageDelayTime(list));

        // Then
        then(throwable).isNull();
    }

    @Test
    @DisplayName("CalculateAverageDelayTime - Empty list")
    void CalculateAverageDelayTime_WhenPassEmptyList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateAverageDelayTime(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateAverageDelayTime - Null list")
    void CalculateAverageDelayTime_WhenPassNullList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> nullList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateAverageDelayTime(nullList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateTopDayDelay - Input list in top dto calculator")
    void CalculateTopDayDelay_WhenPassValidList_ThenPassThisListToTopDtoFactorCalculator() {
        // Given
        List<PreDepartureDelayDto> expectedList = List.of(new PreDepartureDelayDto());

        PreDepartureDelayDto exampleDto = getPreDepartureDelayDtoExample();

        given(preDepartureDelayAveraging.apply(any(PreDepartureDelayDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.findTopDto(anyCollection(), any())).willReturn(exampleDto);

        // When
        preDepartureDelayFactorsCalculator.calculateTopDayDelay(expectedList);

        // Then
        verify(topDtoFactorCalculator).findTopDto(captor.capture(), any());

        List<PreDepartureDelayDto> capturedList = captor.getValue();

        then(capturedList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("CalculateTopDayDelay - Correct result")
    void CalculateTopDayDelay_WhenPassValidList_ThenReturnCorrectResult() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        PreDepartureDelayDto exampleDto = getPreDepartureDelayDtoExample();

        given(preDepartureDelayAveraging.apply(any(PreDepartureDelayDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.findTopDto(anyCollection(), any())).willReturn(exampleDto);

        ValueWithDateHolder expectedValue = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 10.0d);

        // When
        ValueWithDateHolder actualValue = preDepartureDelayFactorsCalculator.calculateTopDayDelay(list);

        // Then
        then(actualValue).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateTopDayDelay - Valid input")
    void CalculateTopDayDelay_WhenPassValidList_ThenNotThrowException() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopDayDelay(list));

        // Then
        then(throwable)
                .isNotInstanceOf(PreDepartureDelayDataNotFoundException.class);
    }

    @Test
    @DisplayName("CalculateTopDayDelay - Empty list")
    void CalculateTopDayDelay_WhenPassEmptyList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopDayDelay(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateTopDayDelay - Null list")
    void CalculateTopDayDelay_WhenPassNullList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> nullList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopDayDelay(nullList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateTopMonthDelay - Input list in top dto calculator")
    void CalculateTopMonthDelay_WhenPassValidList_ThenPassThisListToAverageFactorCalculator() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        PreDepartureDelayDto exampleDto = getPreDepartureDelayDtoExample();

        given(preDepartureDelayAveraging.apply(any(PreDepartureDelayDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

        // When
        preDepartureDelayFactorsCalculator.calculateTopMonthDelay(list);

        // Then
        verify(topDtoFactorCalculator).getTopMonthDto(captor.capture(), any(), any());

        List<PreDepartureDelayDto> capturedList = captor.getValue();

        then(capturedList).isEqualTo(list);
    }

    @Test
    @DisplayName("CalculateTopMonthDelay - Correct result")
    void CalculateTopMonthDelay_WhenPassValidList_ThenReturnCorrectResult() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        PreDepartureDelayDto exampleDto = getPreDepartureDelayDtoExample();

        given(preDepartureDelayAveraging.apply(any(PreDepartureDelayDto.class))).willReturn(10.0d);
        given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

        ValueWithDateHolder expectedValue = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 10.0d);

        // When
        ValueWithDateHolder actualValue = preDepartureDelayFactorsCalculator.calculateTopMonthDelay(list);

        // Then
        then(actualValue).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateTopMonthDelay - Valid input")
    void CalculateTopMonthDelay_WhenPassValidList_ThenNotThrowException() {
        // Given
        List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopMonthDelay(list));

        // Then
        then(throwable).isNotInstanceOf(PreDepartureDelayDataNotFoundException.class);
    }

    @Test
    @DisplayName("CalculateTopMonthDelay - Empty list")
    void CalculateTopMonthDelay_WhenPassEmptyList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopMonthDelay(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateTopMonthDelay - Null list")
    void CalculateTopMonthDelay_WhenPassNullList_ThenThrowException() {
        // Given
        List<PreDepartureDelayDto> nullList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                preDepartureDelayFactorsCalculator.calculateTopMonthDelay(nullList));

        // Then
        then(throwable)
                .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                .hasMessage("error.message.preDepartureDelayDataNotFound");
    }

    private static PreDepartureDelayDto getPreDepartureDelayDtoExample() {
        return PreDepartureDelayDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}