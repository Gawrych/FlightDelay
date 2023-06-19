package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.ArrivalDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.ArrivalDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("The arrival delay factor collector")
class ArrivalDelayFactorCollectorImplTest {

    @Mock
    private ArrivalDelayService arrivalDelayService;

    @Mock
    private ArrivalDelayFactorsCalculator arrivalDelayFactorsCalculator;

    @Mock
    private StatisticReportCreator statisticReportCreator;

    @Captor
    private ArgumentCaptor<List<ArrivalDelayDto>> dtoCaptor;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor[]> entityArrayCaptor;

    @Captor
    private ArgumentCaptor<ArrivalDelayFactor> arrivalFactorCaptor;

    @Captor
    private ArgumentCaptor<List<ValueWithTextHolder>> valueWithTextHolderCaptor;

    private ArrivalDelayFactorCollectorImpl arrivalDelayFactorCollector;

    @BeforeEach
    void setUp() {
        this.arrivalDelayFactorCollector = spy(new ArrivalDelayFactorCollectorImpl(
                arrivalDelayService,
                arrivalDelayFactorsCalculator,
                statisticReportCreator
        ));
    }

    @Nested
    @DisplayName("correctly collects factors")
    class ReturnFilledList {

        @Nested
        @DisplayName("when match")
        class MatchFactor {

            @Test
            @DisplayName("factor MOST_COMMON_DELAY_CAUSE with external calculator")
            void CollectFactors_WhenMostCommonDelayCause_ThenMatchWithExternalCalculator() {
                // Given
                ArrivalDelayFactor factorName = ArrivalDelayFactor.MOST_COMMON_DELAY_CAUSE;

                // When
                arrivalDelayFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(arrivalDelayFactorsCalculator).calculateMostCommonDelayCause(anyList());
                verifyNoMoreInteractions(arrivalDelayFactorsCalculator);
            }

            @Test
            @DisplayName("factor AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE with external calculator")
            void CollectFactors_WhenAverageTimeToParticularDelayCause_ThenMatchWithExternalCalculator() {
                // Given
                ArrivalDelayFactor factorName = ArrivalDelayFactor.AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE;

                // When
                arrivalDelayFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(arrivalDelayFactorsCalculator).calculateAverageTimeToParticularDelayCause(anyList());
                verifyNoMoreInteractions(arrivalDelayFactorsCalculator);
            }
        }

        @Nested
        @DisplayName("when pass")
        class PassFactor {

            @Test
            @DisplayName("dto list to superclass method")
            void CollectFactors_WhenPassDtoListFromDatabase_ThenReturnListOfFactors() {
                // Given
                List<ArrivalDelayDto> expectedList = List.of(new ArrivalDelayDto());

                given(arrivalDelayService.findAllLatestByAirport(anyString())).willReturn(expectedList);

                doReturn(List.of()).when(arrivalDelayFactorCollector)
                        .collectFactors(anyString(), anyList(), any(ArrivalDelayFactor[].class));

                // When
                arrivalDelayFactorCollector.collect("AAAA");

                // Then
                verify(arrivalDelayFactorCollector).collectFactors(
                        anyString(),
                        dtoCaptor.capture(),
                        any(ArrivalDelayFactor[].class));

                List<ArrivalDelayDto> actualList = dtoCaptor.getValue();

                then(actualList).isEqualTo(expectedList);
            }

            @Test
            @DisplayName("arrival delay factors array to superclass method")
            void CollectFactors_WhenPassArrivalDelayFactorArray_ThenReturnListOfFactors() {
                // Given
                List<ArrivalDelayDto> trafficDtos = List.of(new ArrivalDelayDto());

                given(arrivalDelayService.findAllLatestByAirport(anyString())).willReturn(trafficDtos);

                doReturn(List.of()).when(arrivalDelayFactorCollector)
                        .collectFactors(anyString(), anyList(), any(ArrivalDelayFactor[].class));

                // When
                arrivalDelayFactorCollector.collect("AAAA");

                // Then
                EntityStatisticFactor[] expectedEntity = ArrivalDelayFactor.values();

                verify(arrivalDelayFactorCollector).collectFactors(
                        anyString(),
                        anyList(),
                        entityArrayCaptor.capture());

                EntityStatisticFactor[] actualEntity = entityArrayCaptor.getValue();

                then(actualEntity).isEqualTo(expectedEntity);
            }

            @Test
            @DisplayName("factor MOST_COMMON_DELAY_CAUSE and calculated value to report creator")
            void CollectFactors_WhenMostCommonDelayCauseAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                ArrivalDelayFactor expectedFactor = ArrivalDelayFactor.MOST_COMMON_DELAY_CAUSE;

                List<ValueWithTextHolder> expectedValue = List.of(mock(ValueWithTextHolder.class));

                given(arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(anyList())).willReturn(expectedValue);

                // When
                arrivalDelayFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(
                        arrivalFactorCaptor.capture(),
                        valueWithTextHolderCaptor.capture());

                ArrivalDelayFactor actualFactor = arrivalFactorCaptor.getValue();
                List<ValueWithTextHolder> actualValue = valueWithTextHolderCaptor.getValue();

                then(actualFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }

            @Test
            @DisplayName("factor AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE and calculated value to report creator")
            void CollectFactors_WhenAverageTimeToParticularDelayCauseAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                ArrivalDelayFactor expectedFactor = ArrivalDelayFactor.AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE;

                List<ValueWithTextHolder> expectedValue = List.of(mock(ValueWithTextHolder.class));

                given(arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(anyList()))
                        .willReturn(expectedValue);

                // When
                arrivalDelayFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(
                        arrivalFactorCaptor.capture(),
                        valueWithTextHolderCaptor.capture());

                ArrivalDelayFactor actualTrafficFactor = arrivalFactorCaptor.getValue();
                List<ValueWithTextHolder> actualValue = valueWithTextHolderCaptor.getValue();

                then(actualTrafficFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }
        }

        @ParameterizedTest(name = "{index} : {0} factor name")
        @EnumSource(ArrivalDelayFactor.class)
        @DisplayName("when no data factor method pass to report creator")
        void CollectFactors_WhenNoDataFactorGetsDataFromSuperclass_ThenPassToPrecisionFactorCreator(
                ArrivalDelayFactor arrivalDelayFactor) {

            // When
            arrivalDelayFactorCollector.getNoDataFactor(arrivalDelayFactor);

            // Then
            verify(statisticReportCreator).create(arrivalFactorCaptor.capture());

            ArrivalDelayFactor actualTrafficFactor = arrivalFactorCaptor.getValue();

            then(actualTrafficFactor).isEqualTo(arrivalDelayFactor);
        }
    }
}