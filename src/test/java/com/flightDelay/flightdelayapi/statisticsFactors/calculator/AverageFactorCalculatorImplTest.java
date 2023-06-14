package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("AverageFactorCalculator Tests")
class AverageFactorCalculatorImplTest {

    private AverageFactorCalculatorImpl averageFactorCalculator;

    @BeforeEach
    void setUp() {
        averageFactorCalculator = new AverageFactorCalculatorImpl();
    }

    @Test
    @DisplayName("CalculateAverage - Correct result")
    void CalculateAverage_WhenPassValidListAsAParameter_ThenReturnCorrectResult() {
        // Given
        double numerator = 10;
        double denominator = 2;

        // When
        double average = averageFactorCalculator.calculateAverage(numerator, denominator);

        // Then
        then(average).isEqualTo(5.0d);
    }

    @Test
    @DisplayName("CalculateAverage - Zero as denominator")
    void CalculateAverage_WhenPassZeroAsDenominator_ThenThrowException() {
        // Given
        double numerator = 10;
        double denominator = 0;

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverage(numerator, denominator));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToIncorrectDataException.class)
                .hasMessage("error.message.unableToCalculateDueToIncorrectDataException");
    }

    @Test
    @DisplayName("CalculateAverageFromLists - Correct result")
    void CalculateAverageFromLists_WhenPassValidListsAsAParameters_ThenPassToCalculateAverageCorrectValues() {
        // Given
        List<Double> numerator = List.of(10.0d, 10.0d);
        List<Double> denominator = List.of(5.0d, 5.0d);

        double expectedValue = 2.0d;

        // When
        double actualValue = averageFactorCalculator.calculateAverageFromLists(numerator, denominator);

        // Then
        then(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateAverageFromLists - Empty numerator list parameter")
    void CalculateAverageFromLists_WhenPassEmptyNumeratorListAsAParameters_ThenThrowException() {
        // Given
        List<Double> numerator = List.of();
        List<Double> denominator = List.of(10.0d);

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageFromLists - Empty denominator list parameter")
    void CalculateAverageFromLists_WhenPassEmptyDenominatorListAsAParameters_ThenThrowException() {
        // Given
        List<Double> numerator = List.of(10.0d);
        List<Double> denominator = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageFromLists - Null numerator list parameter")
    void CalculateAverageFromLists_WhenPassNullNumeratorListAsAParameters_ThenThrowException() {
        // Given
        List<Double> numerator = null;
        List<Double> denominator = List.of(10.0d);

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageFromLists - Null denominator list parameter")
    void CalculateAverageFromLists_WhenPassNullDenominatorListAsAParameters_ThenThrowException() {
        // Given
        List<Double> numerator = List.of(10.0d);
        List<Double> denominator = null;

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }



    @Test
    @DisplayName("CalculateAverageByDtoList - Correct result")
    void CalculateAverageByDtoList_WhenPassEmptyDenominatorListAsAParameters_ThenThrowException() {
        // Given
        List<Double> list = List.of(5.0d, 10.0d);

        double expectedValue = 1.0;

        // When
        double actualValue = averageFactorCalculator.calculateAverageByDtoList(
                list,
                Double::doubleValue,
                Double::doubleValue);

        // Then
        then(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("CalculateAverageByDtoList - Null dtos list parameter")
    void CalculateAverageByDtoList_WhenPassNullDtosListAsAParameters_ThenThrowException() {
        // Given
        List<Double> nullList = null;

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageByDtoList(
                        nullList,
                        Double::doubleValue,
                        Double::doubleValue));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageByDtoList - Empty dtos list parameter")
    void CalculateAverageByDtoList_WhenPassEmptyDtosListAsAParameters_ThenThrowException() {
        // Given
        List<Double> emptyList = List.of();

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageByDtoList(
                        emptyList,
                        Double::doubleValue,
                        Double::doubleValue));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageByDtoList - Null numeratorImpl parameter")
    void CalculateAverageByDtoList_WhenPassNullNumeratorImplAsAParameters_ThenThrowException() {
        // Given
        Function<Double, Double> numeratorImpl = null;

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageByDtoList(
                        List.of(5.0d),
                        numeratorImpl,
                        Double::doubleValue));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }

    @Test
    @DisplayName("CalculateAverageByDtoList - Null denominatorImpl parameter")
    void CalculateAverageByDtoList_WhenPassNullDenominatorImplAsAParameters_ThenThrowException() {
        // Given
        Function<Double, Double> denominatorImpl = null;

        // When
        Throwable throwable = catchThrowable(() ->
                averageFactorCalculator.calculateAverageByDtoList(
                        List.of(5.0d),
                        Double::doubleValue,
                        denominatorImpl));

        // Then
        then(throwable)
                .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
    }
}