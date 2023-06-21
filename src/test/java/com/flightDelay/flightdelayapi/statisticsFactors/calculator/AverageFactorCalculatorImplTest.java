package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Average factor calculator")
class AverageFactorCalculatorImplTest {

    @InjectMocks
    private AverageFactorCalculatorImpl averageFactorCalculator;

    @Nested
    @DisplayName("returns correct result")
    class ReturnsCorrectResult {

        @ParameterizedTest(name = "{index} : {0} divided by {1} returned {2}")
        @CsvSource(value = {"10:2:5", "20:5:4", "30:5:6"}, delimiter = ':')
        @DisplayName("when first number by second are correctly divided")
        void CalculateAverage_WhenCorrectlyDividedFirstNumberBySecond_ThenReturnCorrectResult(double numerator,
                                                                                    double denominator,
                                                                                    double expectedValue) {
            // When
            double average = averageFactorCalculator.calculateAverage(numerator, denominator);

            // Then
            then(average).isEqualTo(expectedValue);
        }

        @ParameterizedTest(name = "{index} : summed numerator numbers {0} and {1} divided by summed denominator numbers {2} and {3} equals {4}")
        @CsvSource(value = {"10:10:2:2:5", "20:20:5:5:4"}, delimiter = ':')
        @DisplayName("when summed numerator numbers and summed denominator numbers are correctly divided")
        void CalculateAverageFromLists_WhenSummedCorrectlyNumeratorAndDenominator_ThenPassToCalculateAverageCorrectValues(
                double firstNumerator,
                double secondNumerator,
                double firstDenominator,
                double secondDenominator,
                double expectedValue) {

            // Given
            List<Double> numerator = List.of(firstNumerator, secondNumerator);
            List<Double> denominator = List.of(firstDenominator, secondDenominator);

            // When
            double actualValue = averageFactorCalculator.calculateAverageFromLists(numerator, denominator);

            // Then
            then(actualValue).isEqualTo(expectedValue);
        }

        @ParameterizedTest(name = "{index} : {0} in list return {1}")
        @CsvSource(value = {"10:1", "20:1"}, delimiter = ':')
        @DisplayName("when values are in list")
        void CalculateAverageByDtoList_WhenValuesAreInList_ThenThrowException(
                double value,
                double expectedValue) {

            // Given
            List<Double> list = List.of(value);

            // When
            double actualValue = averageFactorCalculator.calculateAverageByDtoList(
                    list,
                    Double::doubleValue,
                    Double::doubleValue);

            // Then
            then(actualValue).isEqualTo(expectedValue);
        }
    }

    @Nested
    @DisplayName("throws an exception")
    class ThrowsAnException {
        @ParameterizedTest(name = "{index} : {0} divided by {1} returned exception")
        @CsvSource(value = {"10:0", "20:0", "30:0"}, delimiter = ':')
        @DisplayName("when denominator is zero")
        void CalculateAverage_WhenDenominatorIsZero_ThenThrowException(double numerator, double denominator) {
            // When
            Throwable throwable = catchThrowable(() ->
                    averageFactorCalculator.calculateAverage(numerator, denominator));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToIncorrectDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToIncorrectDataException");
        }

        @ParameterizedTest(name = "{index} : is {0}")
        @NullAndEmptySource
        @DisplayName("when numerator list")
        void CalculateAverageFromLists_WhenNumeratorIsEmptyOrNull_ThenThrowException(List<Double> numerator) {
            // Given
            List<Double> denominator = List.of(10.0d);

            // When
            Throwable throwable = catchThrowable(() ->
                    averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
        }

        @ParameterizedTest(name = "{index} : is {0}")
        @NullAndEmptySource
        @DisplayName("when denominator list")
        void CalculateAverageFromLists_WhenDenominatorIsEmptyOrNull_ThenThrowException(List<Double> denominator) {
            // Given
            List<Double> numerator = List.of(10.0d);

            // When
            Throwable throwable = catchThrowable(() ->
                    averageFactorCalculator.calculateAverageFromLists(numerator, denominator));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
        }

        @ParameterizedTest(name = "{index} : is {0}")
        @NullAndEmptySource
        @DisplayName("when list to averaging")
        void CalculateAverageByDtoList_WhenNotValidListToAveraging_ThenThrowException(List<Double> notValidList) {
            // When
            Throwable throwable = catchThrowable(() ->
                    averageFactorCalculator.calculateAverageByDtoList(
                            notValidList,
                            Double::doubleValue,
                            Double::doubleValue));

            // Then
            then(throwable)
                    .isInstanceOf(UnableToCalculateDueToLackOfDataException.class)
                    .hasMessage("error.message.unableToCalculateDueToLackOfDataException");
        }

        @ParameterizedTest(name = "{index} : is {0}")
        @NullSource
        @DisplayName("when numerator function")
        void CalculateAverageByDtoList_WhenNumeratorFunctionIsNull_ThenThrowException(
                Function<Double, Double> numeratorImpl) {

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

        @ParameterizedTest(name = "{index} : is {0}")
        @NullSource
        @DisplayName("when denominator function")
        void CalculateAverageByDtoList_WhenDenominatorFunctionIsNull_ThenThrowException(
                Function<Double, Double> denominatorImpl) {

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
}