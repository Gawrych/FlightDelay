package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
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
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pre-departure delay factors calculator")
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

    @Nested
    @DisplayName("returns correct result")
    class ReturnsCorrectResult {

        @Nested
        @DisplayName("when value with date holder is correctly created based on input in")
        class CorrectValue {

            @Test
            @DisplayName("top day delay calculating method")
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
            @DisplayName("top month delay calculating method")
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
        }

        @Nested
        @DisplayName("when parameter list was passed to")
        class PassInputList {

            @Test
            @DisplayName("average calculator for average delay time")
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
            @DisplayName("top dto calculator for top dto of collection")
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
            @DisplayName("top dto calculator for top month from grouped dtos on months")
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
        }
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

            @ParameterizedTest(name = "{index} : {0} list")
            @NullAndEmptySource
            @DisplayName("when average delay time calculating method gets")
            void CalculateAverageDelayTime_WhenPassNullList_ThenThrowException(List<PreDepartureDelayDto> notValidList) {
                // When
                Throwable throwable = catchThrowable(() ->
                        preDepartureDelayFactorsCalculator.calculateAverageDelayTime(notValidList));

                // Then
                then(throwable)
                        .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                        .hasMessage("error.message.preDepartureDelayDataNotFound");
            }


            @ParameterizedTest(name = "{index} : {0} list")
            @NullAndEmptySource
            @DisplayName("when top day delay calculating method gets")
            void CalculateTopDayDelay_WhenPassNullList_ThenThrowException(List<PreDepartureDelayDto> notValidList) {
                // When
                Throwable throwable = catchThrowable(() ->
                        preDepartureDelayFactorsCalculator.calculateTopDayDelay(notValidList));

                // Then
                then(throwable)
                        .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                        .hasMessage("error.message.preDepartureDelayDataNotFound");
            }

            @ParameterizedTest(name = "{index} : {0} list")
            @NullAndEmptySource
            @DisplayName("when top month delay calculating method gets")
            void CalculateTopMonthDelay_WhenPassNullList_ThenThrowException(List<PreDepartureDelayDto> notValidList) {
                // When
                Throwable throwable = catchThrowable(() ->
                        preDepartureDelayFactorsCalculator.calculateTopMonthDelay(notValidList));

                // Then
                then(throwable)
                        .isInstanceOf(PreDepartureDelayDataNotFoundException.class)
                        .hasMessage("error.message.preDepartureDelayDataNotFound");
            }
    }

    @Nested
    @DisplayName("not throws a data not found exception")
    class NotThrowsAnException {

        @Nested
        @DisplayName("when input parameter is valid in")
        class ValidInput {

            @Test
            @DisplayName("average delay time calculating method")
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
            @DisplayName("top day delay calculating method")
            void CalculateTopDayDelay_WhenPassValidList_ThenNotThrowException() {
                // Given
                List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

                // When
                Throwable throwable = catchThrowable(() ->
                        preDepartureDelayFactorsCalculator.calculateTopDayDelay(list));

                // Then
                then(throwable).isNotInstanceOf(PreDepartureDelayDataNotFoundException.class);
            }

            @Test
            @DisplayName("top month delay calculating method")
            void CalculateTopMonthDelay_WhenPassValidList_ThenNotThrowException() {
                // Given
                List<PreDepartureDelayDto> list = List.of(new PreDepartureDelayDto());

                // When
                Throwable throwable = catchThrowable(() ->
                        preDepartureDelayFactorsCalculator.calculateTopMonthDelay(list));

                // Then
                then(throwable).isNotInstanceOf(PreDepartureDelayDataNotFoundException.class);
            }
        }
    }

    private static PreDepartureDelayDto getPreDepartureDelayDtoExample() {
        return PreDepartureDelayDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}