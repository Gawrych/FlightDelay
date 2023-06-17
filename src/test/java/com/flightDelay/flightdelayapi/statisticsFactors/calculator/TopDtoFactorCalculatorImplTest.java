package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("Top dto factor calculator")
class TopDtoFactorCalculatorImplTest {

    @Mock
    private BinaryOperator<DelayEntityDto> remappingImpl;

    @Mock
    private Function<DelayEntityDto, Double> averageImpl;

    private TopDtoFactorCalculatorImpl<DelayEntityDto> topDtoFactorCalculator;

    @BeforeEach
    void setUp() {
        this.topDtoFactorCalculator = new TopDtoFactorCalculatorImpl<>();
    }

    @Nested
    @DisplayName("returns correct result")
    class ReturnCorrectResult {

        @Test
        @DisplayName("when top month dto calculating method returns find top dto from summed values on months")
        void GetTopMonthDto_WhenPassNullList_ThenThrowException() {
            // Given
            DelayEntityDto firstDto = mock(DelayEntityDto.class);
            DelayEntityDto secondDto = mock(DelayEntityDto.class);
            DelayEntityDto thirdDto = mock(DelayEntityDto.class);

            given(firstDto.getDate()).willReturn(LocalDate.ofEpochDay(1L));
            given(secondDto.getDate()).willReturn(LocalDate.ofEpochDay(1L));

            List<DelayEntityDto> expectedList = List.of(firstDto, secondDto);

            given(remappingImpl.apply(any(), any())).willReturn(thirdDto);

            // When
            Map<Month, DelayEntityDto> monthDelayEntityDtoMap =
                    topDtoFactorCalculator.sumDtosInTheSameMonths(expectedList, remappingImpl);

            // Then
            DelayEntityDto delayEntityDto = monthDelayEntityDtoMap.get(Month.JANUARY);
            then(delayEntityDto).isEqualTo(thirdDto);
        }

        @Test
        @DisplayName("when summed dtos")
        void SumDtosInTheSameMonths_WhenPassNullList_ThenThrowException() {
            // Given
            DelayEntityDto firstDto = mock(DelayEntityDto.class);
            DelayEntityDto secondDto = mock(DelayEntityDto.class);

            given(firstDto.getDate()).willReturn(LocalDate.ofEpochDay(1L));
            given(secondDto.getDate()).willReturn(LocalDate.ofEpochDay(32L));

            given(averageImpl.apply(firstDto)).willReturn(5.0d);
            given(averageImpl.apply(secondDto)).willReturn(10.0d);

            List<DelayEntityDto> expectedList = List.of(firstDto, secondDto);

            // When
            DelayEntityDto actualTopDto = topDtoFactorCalculator.getTopMonthDto(expectedList, remappingImpl, averageImpl);

            // Then
            then(actualTopDto).isEqualTo(secondDto);
        }

        @Test
        @DisplayName("when found top dto")
        void FindTopDto_WhenPassNullList_ThenThrowException() {
            // Given
            DelayEntityDto firstDto = mock(DelayEntityDto.class);
            DelayEntityDto secondDto = mock(DelayEntityDto.class);

            List<DelayEntityDto> expectedList = List.of(firstDto, secondDto);

            given(averageImpl.apply(firstDto)).willReturn(5.0d);
            given(averageImpl.apply(secondDto)).willReturn(10.0d);

            // When
            DelayEntityDto actualTopDto = topDtoFactorCalculator.findTopDto(expectedList, averageImpl);

            // Then
            then(actualTopDto).isEqualTo(secondDto);
        }
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

            @ParameterizedTest(name = "{index} : List = {0}")
            @NullAndEmptySource
            @DisplayName("when top month dto calculating method gets")
            void GetTopMonthDto_WhenPassNullList_ThenThrowException(List<DelayEntityDto> notValidList) {
                // When
                Throwable throwable = catchThrowable(() ->
                        topDtoFactorCalculator.getTopMonthDto(notValidList, remappingImpl, averageImpl));

                // Then
                then(throwable)
                        .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                        .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
            }

        @ParameterizedTest(name = "{index} : List = {0}")
        @NullAndEmptySource
        @DisplayName("when sum dtos in the same month calculating method gets")
        void SumDtosInTheSameMonths_WhenPassNullList_ThenThrowException(List<DelayEntityDto> notValidList) {
            // When
            Throwable throwable = catchThrowable(() ->
                    topDtoFactorCalculator.sumDtosInTheSameMonths(notValidList, remappingImpl));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
        }

        @ParameterizedTest(name = "{index} : List = {0}")
        @NullAndEmptySource
        @DisplayName("when top dto finder method gets")
        void FindTopDto_WhenPassNullList_ThenThrowException(List<DelayEntityDto> notValidList) {
            // When
            Throwable throwable = catchThrowable(() ->
                    topDtoFactorCalculator.findTopDto(notValidList, averageImpl));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
        }
    }
}