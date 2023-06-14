package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.ArrivalDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdditionalTimeFactorsCalculator Tests")
class ArrivalDelayFactorsCalculatorImplTest {

    private ArrivalDelayFactorsCalculatorImpl arrivalDelayFactorsCalculator;

    @BeforeEach
    void setUp() {
        arrivalDelayFactorsCalculator = new ArrivalDelayFactorsCalculatorImpl();
    }

    @Test
    @DisplayName("CalculateMostCommonDelayCause - Correct result")
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
                    .containsSequence(2.0, 1.0);
        });
    }

    @Test
    @DisplayName("CalculateMostCommonDelayCause - Correct list size")
    void CalculateMostCommonDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectListSize() {
        // Given
        List<ArrivalDelayDto> list = new ArrayList<>();

        list.add(ArrivalDelayDto.builder()
                .delays(Map.of(DelayCause.ACCIDENT, 10, DelayCause.CAPACITY, 10))
                .build());

        list.add(ArrivalDelayDto.builder()
                .delays(Map.of(DelayCause.ACCIDENT, 10))
                .build());

        int listLimit = 1;

        ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", listLimit);

        // When
        List<ValueWithTextHolder> valueWithTextHolders =
                arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(list);

        // Then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(valueWithTextHolders)
                    .hasSize(1);

            softly.assertThat(valueWithTextHolders)
                    .extracting("text")
                    .containsSequence(DelayCause.ACCIDENT.name());

            softly.assertThat(valueWithTextHolders)
                    .extracting("value")
                    .containsSequence(2.0);
        });
    }

    @Test
    @DisplayName("CalculateMostCommonDelayCause - Valid List")
    void CalculateMostCommonDelayCause_WhenPassValidListAsAParameter_ThenNotThrowException() {
        // Given
        List<ArrivalDelayDto> validList = List.of(new ArrivalDelayDto());

        // When
        final Throwable throwable = catchThrowable(() ->
                arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(validList));

        // Then
        then(throwable).isNotInstanceOf(ArrivalDelayDataNotFoundException.class);
    }

    @Test
    @DisplayName("CalculateMostCommonDelayCause - Empty List")
    void CalculateMostCommonDelayCause_WhenPassEmptyListAsAParameter_ThenThrowException() {
        // Given
        List<ArrivalDelayDto> emptyList = List.of();

        // When
        final Throwable throwable = catchThrowable(() ->
                arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                .hasMessage("error.message.arrivalDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateMostCommonDelayCause - Null List")
    void CalculateMostCommonDelayCause_WhenPassNullListAsAParameter_ThenThrowException() {
        // Given
        List<ArrivalDelayDto> nullList = null;

        // When
        final Throwable throwable = catchThrowable(() ->
                arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(nullList));

        // Then
        then(throwable)
                .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                .hasMessage("error.message.arrivalDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateAverageTimeToParticularDelayCause - Correct result")
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
                    .containsSequence(15.0, 5.0);
        });
    }

    @Test
    @DisplayName("CalculateAverageTimeToParticularDelayCause - Correct list size")
    void CalculateAverageTimeToParticularDelayCause_WhenPassValidListAsAParameter_ThenReturnCorrectListSize() {
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

        int listLimit = 1;

        ReflectionTestUtils.setField(arrivalDelayFactorsCalculator, "listLimit", listLimit);

        // When
        List<ValueWithTextHolder> valueWithTextHolders =
                arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(list);

        // Then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(valueWithTextHolders)
                    .hasSize(1);

            softly.assertThat(valueWithTextHolders)
                    .extracting("text")
                    .containsSequence(DelayCause.ACCIDENT.name());

            softly.assertThat(valueWithTextHolders)
                    .extracting("value")
                    .containsSequence(15.0);
        });
    }

    @Test
    @DisplayName("CalculateAverageTimeToParticularDelayCause - Valid List")
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
    @DisplayName("CalculateAverageTimeToParticularDelayCause - Empty List")
    void CalculateAverageTimeToParticularDelayCause_WhenPassEmptyListAsAParameter_ThenThrowException() {
        // Given
        List<ArrivalDelayDto> emptyList = List.of();

        // When
        final Throwable throwable = catchThrowable(() ->
                arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(emptyList));

        // Then
        then(throwable)
                .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                .hasMessage("error.message.arrivalDelayDataNotFound");
    }

    @Test
    @DisplayName("CalculateAverageTimeToParticularDelayCause - Null List")
    void CalculateAverageTimeToParticularDelayCause_WhenPassNullListAsAParameter_ThenThrowException() {
        // Given
        List<ArrivalDelayDto> nullList = null;

        // When
        final Throwable throwable = catchThrowable(() ->
                arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(nullList));

        // Then
        then(throwable)
                .isInstanceOf(ArrivalDelayDataNotFoundException.class)
                .hasMessage("error.message.arrivalDelayDataNotFound");
    }
}