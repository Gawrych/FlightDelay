package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.ArrivalDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("The arrival delay factors calculator")
class ArrivalDelayFactorsCalculatorImplTest {

    private ArrivalDelayFactorsCalculatorImpl arrivalDelayFactorsCalculator;

    @BeforeEach
    void setUp() {
        arrivalDelayFactorsCalculator = new ArrivalDelayFactorsCalculatorImpl();
    }

    @Nested
    @DisplayName("returns correct result")
    class ReturnsCorrectResult {

        @Nested
        @DisplayName("when result is correctly created based on input in")
        class CorrectValue {

            @Test
            @DisplayName("most common delay cause calculating method")
            void CalculateMostCommonDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectResult() {
                // Given
                List<ArrivalDelayDto> list = new ArrayList<>();

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 10, DelayCause.CAPACITY, 10))
                        .build());

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 10))
                        .build());

                ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", 5);

                // When
                List<ValueWithTextHolder> valueWithTextHolders =
                        arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(list);

                // Then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(valueWithTextHolders)
                            .hasSize(2);

                    softly.assertThat(valueWithTextHolders)
                            .extracting("text")
                            .containsSequence(DelayCause.ACCIDENT.name(), DelayCause.CAPACITY.name());

                    softly.assertThat(valueWithTextHolders)
                            .extracting("value")
                            .containsSequence(2.0d, 1.0d);
                });
            }

            @Test
            @DisplayName("average time to particular delay cause calculating method")
            void CalculateAverageTimeToParticularDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectResult() {
                // Given
                List<ArrivalDelayDto> list = new ArrayList<>();

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 30))
                        .numberOfDelayedArrivals(2)
                        .build());

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.DEICING, 10))
                        .numberOfDelayedArrivals(2)
                        .build());

                ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", 5);

                // When
                List<ValueWithTextHolder> valueWithTextHolders =
                        arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(list);

                // Then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(valueWithTextHolders)
                            .hasSize(2);

                    softly.assertThat(valueWithTextHolders)
                            .extracting("text")
                            .containsSequence(DelayCause.ACCIDENT.name(), DelayCause.DEICING.name());

                    softly.assertThat(valueWithTextHolders)
                            .extracting("value")
                            .containsSequence(15.0d, 5.0d);
                });
            }
        }

        @Nested
        @DisplayName("when correct list size return")
        class ListSize {

            @ParameterizedTest(name = "{index} : {0} element list size")
            @ValueSource(ints = {1, 2})
            @DisplayName("average time to particular delay cause calculating method")
            void CalculateAverageTimeToParticularDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectListSize(
                    int listSizeLimit) {
                // Given
                List<ArrivalDelayDto> list = new ArrayList<>();

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 30))
                        .numberOfDelayedArrivals(2)
                        .build());

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.DEICING, 10))
                        .numberOfDelayedArrivals(2)
                        .build());

                ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", listSizeLimit);

                // When
                List<ValueWithTextHolder> valueWithTextHolders =
                        arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(list);

                // Then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(valueWithTextHolders)
                            .hasSize(listSizeLimit);

                    softly.assertThat(valueWithTextHolders)
                            .extracting("text")
                            .containsSequence(DelayCause.ACCIDENT.name());

                    softly.assertThat(valueWithTextHolders)
                            .extracting("value")
                            .containsSequence(15.0d);
                });
            }

            @ParameterizedTest(name = "{index} : {0} element list size")
            @ValueSource(ints = {1, 2})
            @DisplayName("most common delay cause calculating method  ")
            void CalculateMostCommonDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectListSize(
                    int listSizeLimit) {
                // Given
                List<ArrivalDelayDto> list = new ArrayList<>();

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 10, DelayCause.CAPACITY, 10))
                        .build());

                list.add(ArrivalDelayDto.builder()
                        .delays(Map.of(DelayCause.ACCIDENT, 10))
                        .build());

                ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", listSizeLimit);

                // When
                List<ValueWithTextHolder> valueWithTextHolders =
                        arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(list);

                // Then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(valueWithTextHolders)
                            .hasSize(listSizeLimit);

                    softly.assertThat(valueWithTextHolders)
                            .extracting("text")
                            .containsSequence(DelayCause.ACCIDENT.name());

                    softly.assertThat(valueWithTextHolders)
                            .extracting("value")
                            .containsSequence(2.0d);
                });
            }
        }
    }

    @Nested
    @DisplayName("not throws a data not found exception")
    class NotThrowsAnException {

        @Nested
        @DisplayName("when input parameter is valid in")
        class ValidInput {

            @Test
            @DisplayName("average time to particular delay cause calculating method")
            void CalculateAverageTimeToParticularDelayCause_WhenPassValidListAsAParameter_ThenNotThrowException() {
                // Given
                List<ArrivalDelayDto> validList = List.of(new ArrivalDelayDto());

                // When
                final Throwable throwable = catchThrowable(() ->
                        arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(validList));

                // Then
                then(throwable).isNotInstanceOf(ArrivalDelayDataNotFoundException.class);
            }

            @Test
            @DisplayName("most common delay cause calculating method")
            void CalculateMostCommonDelayCause_WhenPassValidListAsAParameter_ThenNotThrowException() {
                // Given
                List<ArrivalDelayDto> validList = List.of(new ArrivalDelayDto());

                // When
                final Throwable throwable = catchThrowable(() ->
                        arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(validList));

                // Then
                then(throwable).isNotInstanceOf(ArrivalDelayDataNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {

        @ParameterizedTest(name = "{index} : {0} list")
        @NullAndEmptySource
        @DisplayName("when average time to particular delay cause calculating method gets")
        void CalculateAverageTimeToParticularDelayCause_WhenPassNullList_ThenThrowException(List<ArrivalDelayDto> notValidList) {
            // When
            final Throwable throwable = catchThrowable(() ->
                    arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(notValidList));

            // Then
            then(throwable)
                    .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                    .hasMessage("error.message.arrivalDelayDataNotFound");
        }

        @ParameterizedTest(name = "{index} : {0} list")
        @NullAndEmptySource
        @DisplayName("when most common delay cause calculating method gets")
        void CalculateTopMonthDelay_WhenPassNullList_ThenThrowException(List<ArrivalDelayDto> notValidList) {
            // When
            final Throwable throwable = catchThrowable(() ->
                    arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(notValidList));

            // Then
            then(throwable)
                    .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                    .hasMessage("error.message.arrivalDelayDataNotFound");
        }
    }
}