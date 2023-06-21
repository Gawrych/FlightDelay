package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.AdditionalTimeDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
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


@ExtendWith(MockitoExtension.class)
@DisplayName("The additional time factors calculator")
class AdditionalTimeFactorsCalculatorImplTest {

    @Mock
    private AverageFactorCalculator averageFactorCalculator;

    @Mock
    private TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    @Mock
    private Function<AdditionalTimeDto, Double> additionalTimeAveraging;

    @Mock
    private BinaryOperator<AdditionalTimeDto> additionalTimeRemapping;

    @InjectMocks
    private AdditionalTimeFactorsCalculatorImpl additionalTimeFactorsCalculator;

    @Captor
    private ArgumentCaptor<List<AdditionalTimeDto>> captor;

    @Nested
    @DisplayName("returns correct result")
    class ReturnsCorrectResult {

        @Nested
        @DisplayName("when result is correctly created based on input in")
        class CorrectValue {

            @Test
            @DisplayName("top month delay calculating method")
            void CalculateTopMonthDelay_WhenPassValidListAsAParameter_ThenReturnCorrectResult() {
                // Given
                List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

                AdditionalTimeDto exampleDto = getAdditionalTimeDtoExample();

                given(additionalTimeAveraging.apply(any(AdditionalTimeDto.class))).willReturn(10.0d);
                given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

                ValueWithDateHolder expectedValue = new ValueWithDateHolder(LocalDate.ofEpochDay(1), 10.0d);

                // When
                ValueWithDateHolder actualValue = additionalTimeFactorsCalculator.calculateTopMonthDelay(list);

                // Then
                then(actualValue).usingRecursiveComparison().isEqualTo(expectedValue);
            }

            @Test
            @DisplayName("average from list calculating method")
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
        }

        @Nested
        @DisplayName("when parameter list was passed to")
        class PassInputList {

            @Test
            @DisplayName("top dto calculator for top month from grouped dtos on months")
            void CalculateTopMonthDelay_WhenPassValidListAsAParameter_ThenPassThisListToTopDtoFactorCalculator() {
                // Given
                List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

                AdditionalTimeDto exampleDto = getAdditionalTimeDtoExample();

                given(additionalTimeAveraging.apply(any(AdditionalTimeDto.class))).willReturn(10.0d);
                given(topDtoFactorCalculator.getTopMonthDto(anyList(), any(), any())).willReturn(exampleDto);

                // When
                additionalTimeFactorsCalculator.calculateTopMonthDelay(list);

                // Then
                verify(topDtoFactorCalculator).getTopMonthDto(captor.capture(), any(), any());

                List<AdditionalTimeDto> capturedList = captor.getValue();

                then(capturedList).isEqualTo(list);
            }

            @Test
            @DisplayName("average calculator for average delay time")
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
        }

    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

        @ParameterizedTest(name = "{index} : {0} list")
        @NullAndEmptySource
        @DisplayName("when average delay time calculating method gets")
        void CalculateAverageFromList_WhenPassNullList_ThenThrowException(List<AdditionalTimeDto> notValidList) {
            // When
            final Throwable throwable = catchThrowable(() ->
                    additionalTimeFactorsCalculator.calculateAverageFromList(notValidList));

            // Then
            then(throwable)
                    .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                    .hasMessage("error.message.additionalTimeDataNotFound");
        }

        @ParameterizedTest(name = "{index} : {0} list")
        @NullAndEmptySource
        @DisplayName("when top month delay calculating method gets")
        void CalculateTopMonthDelay_WhenPassNullList_ThenThrowException(List<AdditionalTimeDto> notValidList) {
            // When
            final Throwable throwable = catchThrowable(() ->
                    additionalTimeFactorsCalculator.calculateTopMonthDelay(notValidList));

            // Then
            then(throwable)
                    .isInstanceOf(AdditionalTimeDataNotFoundException.class)
                    .hasMessage("error.message.additionalTimeDataNotFound");
        }
    }

    @Nested
    @DisplayName("not throws a data not found exception")
    class NotThrowsAnException {

        @Nested
        @DisplayName("when input parameter is valid in")
        class ValidInput {

            @Test
            @DisplayName("average from list calculating method")
            void CalculateAverageFromList_WhenPassValidList_ThenNotThrowException() {
                // Given
                List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

                // When
                Throwable throwable = catchThrowable(() ->
                        additionalTimeFactorsCalculator.calculateAverageFromList(list));

                // Then
                then(throwable).isNull();
            }

            @Test
            @DisplayName("top month delay calculating method")
            void CalculateTopMonthDelay_WhenPassValidList_ThenNotThrowException() {
                // Given
                List<AdditionalTimeDto> list = List.of(new AdditionalTimeDto());

                // When
                Throwable throwable = catchThrowable(() ->
                        additionalTimeFactorsCalculator.calculateTopMonthDelay(list));

                // Then
                then(throwable).isNotInstanceOf(AdditionalTimeDataNotFoundException.class);
            }
        }
    }

    private static AdditionalTimeDto getAdditionalTimeDtoExample() {
        return AdditionalTimeDto
                .builder()
                .date(LocalDate.ofEpochDay(1))
                .build();
    }
}