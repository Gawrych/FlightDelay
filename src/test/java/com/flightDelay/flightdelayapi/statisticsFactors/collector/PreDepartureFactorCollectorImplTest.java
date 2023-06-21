package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.PreDepartureDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.PreDepartureDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("The pre-departure factor collector")
class PreDepartureFactorCollectorImplTest {

    @Mock
    private PreDepartureDelayService preDepartureDelayService;

    @Mock
    private PreDepartureDelayFactorsCalculator preDepartureDelayFactorsCalculator;

    @Mock
    private StatisticReportCreator statisticReportCreator;

    @Captor
    private ArgumentCaptor<List<PreDepartureDelayDto>> dtoCaptor;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor[]> entityArrayCaptor;

    @Captor
    private ArgumentCaptor<PreDepartureDelayFactor> preDepartureFactorCaptor;

    @Captor
    private ArgumentCaptor<ValueWithDateHolder> valueWithDateHolderCaptor;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    private PreDepartureFactorCollectorImpl preDepartureFactorCollector;

    @BeforeEach
    void setUp() {
        this.preDepartureFactorCollector = spy(new PreDepartureFactorCollectorImpl(
                preDepartureDelayService,
                preDepartureDelayFactorsCalculator,
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
            @DisplayName("factor TOP_MONTH_OF_PRE_DEPARTURE_DELAY with external calculator")
            void CollectFactors_WhenPassTopMonthOfPreDepartureDelay_ThenMatchWithExternalCalculator() {
                // Given
                PreDepartureDelayFactor factorName = PreDepartureDelayFactor.TOP_MONTH_OF_PRE_DEPARTURE_DELAY;

                // When
                preDepartureFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(preDepartureDelayFactorsCalculator).calculateTopMonthDelay(anyList());
                verifyNoMoreInteractions(preDepartureDelayFactorsCalculator);
            }

            @Test
            @DisplayName("factor TOP_DAY_OF_PRE_DEPARTURE_DELAY with external calculator")
            void CollectFactors_WhenPassTopDayOfPreDepartureDelay_ThenMatchWithExternalCalculator() {
                // Given
                PreDepartureDelayFactor factorName = PreDepartureDelayFactor.TOP_DAY_OF_PRE_DEPARTURE_DELAY;

                // When
                preDepartureFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(preDepartureDelayFactorsCalculator).calculateTopDayDelay(anyList());
                verifyNoMoreInteractions(preDepartureDelayFactorsCalculator);
            }

            @Test
            @DisplayName("factor AVERAGE_PRE_DEPARTURE_DELAY with external calculator")
            void CollectFactors_WhenPassAveragePreDepartureDelay_ThenMatchWithExternalCalculator() {
                // Given
                PreDepartureDelayFactor factorName = PreDepartureDelayFactor.AVERAGE_PRE_DEPARTURE_DELAY;

                // When
                preDepartureFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(preDepartureDelayFactorsCalculator).calculateAverageDelayTime(anyList());
                verifyNoMoreInteractions(preDepartureDelayFactorsCalculator);
            }
        }

        @Nested
        @DisplayName("when pass")
        class PassFactor {

            @Test
            @DisplayName("dto list to superclass method")
            void CollectFactors_WhenPassDtoListFromDatabase_ThenReturnListOfFactors() {
                // Given
                List<PreDepartureDelayDto> expectedList = List.of(new PreDepartureDelayDto());

                given(preDepartureDelayService.findAllLatestByAirport(anyString())).willReturn(expectedList);

                willReturn(List.of()).given(preDepartureFactorCollector)
                        .collectFactors(anyString(), anyList(), any(PreDepartureDelayFactor[].class));

                // When
                preDepartureFactorCollector.collect("AAAA");

                // Then
                verify(preDepartureFactorCollector).collectFactors(
                        anyString(),
                        dtoCaptor.capture(),
                        any(PreDepartureDelayFactor[].class));

                List<PreDepartureDelayDto> actualList = dtoCaptor.getValue();

                then(actualList).isEqualTo(expectedList);
            }

            @Test
            @DisplayName("pre-departure factors array to superclass method")
            void CollectFactors_WhenPassPreDepartureFactorArray_ThenReturnListOfFactors() {
                // Given
                List<PreDepartureDelayDto> dtos = List.of(new PreDepartureDelayDto());

                given(preDepartureDelayService.findAllLatestByAirport(anyString())).willReturn(dtos);

                willReturn(List.of()).given(preDepartureFactorCollector)
                        .collectFactors(anyString(), anyList(), any(PreDepartureDelayFactor[].class));

                // When
                preDepartureFactorCollector.collect("AAAA");

                // Then
                EntityStatisticFactor[] expectedEntity = PreDepartureDelayFactor.values();

                verify(preDepartureFactorCollector).collectFactors(anyString(), anyList(), entityArrayCaptor.capture());

                EntityStatisticFactor[] actualEntity = entityArrayCaptor.getValue();

                then(actualEntity).isEqualTo(expectedEntity);
            }

            @Test
            @DisplayName("factor TOP_MONTH_OF_PRE_DEPARTURE_DELAY and calculated value to report creator")
            void CollectFactors_WhenGetsTopMonthOfPreDepartureDelayAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                PreDepartureDelayFactor expectedFactor = PreDepartureDelayFactor.TOP_MONTH_OF_PRE_DEPARTURE_DELAY;
                ValueWithDateHolder expectedValue = mock(ValueWithDateHolder.class);
                given(preDepartureDelayFactorsCalculator.calculateTopMonthDelay(anyList())).willReturn(expectedValue);

                // When
                preDepartureFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(
                        preDepartureFactorCaptor.capture(),
                        valueWithDateHolderCaptor.capture());

                PreDepartureDelayFactor actualPreDepartureFactor = preDepartureFactorCaptor.getValue();
                ValueWithDateHolder actualValue = valueWithDateHolderCaptor.getValue();

                then(actualPreDepartureFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }

            @Test
            @DisplayName("factor TOP_DAY_OF_PRE_DEPARTURE_DELAY and calculated value to report creator")
            void CollectFactors_WhenGetsTopDayOfPreDepartureDelayAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                PreDepartureDelayFactor expectedFactor = PreDepartureDelayFactor.TOP_DAY_OF_PRE_DEPARTURE_DELAY;
                ValueWithDateHolder expectedValue = mock(ValueWithDateHolder.class);
                given(preDepartureDelayFactorsCalculator.calculateTopDayDelay(anyList())).willReturn(expectedValue);

                // When
                preDepartureFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(
                        preDepartureFactorCaptor.capture(),
                        valueWithDateHolderCaptor.capture());

                PreDepartureDelayFactor actualPreDepartureFactor = preDepartureFactorCaptor.getValue();
                ValueWithDateHolder actualValue = valueWithDateHolderCaptor.getValue();

                then(actualPreDepartureFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }

            @Test
            @DisplayName("factor AVERAGE_PRE_DEPARTURE_DELAY and calculated value to report creator")
            void CollectFactors_WhenGetsAveragePreDepartureDelayAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                PreDepartureDelayFactor expectedFactor = PreDepartureDelayFactor.AVERAGE_PRE_DEPARTURE_DELAY;
                double expectedValue = 10.0d;
                given(preDepartureDelayFactorsCalculator.calculateAverageDelayTime(anyList())).willReturn(expectedValue);

                // When
                preDepartureFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(preDepartureFactorCaptor.capture(), doubleCaptor.capture());

                PreDepartureDelayFactor actualPreDepartureFactor = preDepartureFactorCaptor.getValue();
                double actualValue = doubleCaptor.getValue();

                then(actualPreDepartureFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }
        }

        @ParameterizedTest(name = "{index} : {0} factor name")
        @EnumSource(PreDepartureDelayFactor.class)
        @DisplayName("when no data factor method pass to report creator")
        void CollectFactors_WhenNoDataFactorGetsDataFromSuperclass_ThenPassToPrecisionFactorCreator(
                PreDepartureDelayFactor preDepartureDelayFactor) {

            // When
            preDepartureFactorCollector.getNoDataFactor(preDepartureDelayFactor);

            // Then
            verify(statisticReportCreator).create(preDepartureFactorCaptor.capture());

            PreDepartureDelayFactor actualPreDepartureFactor = preDepartureFactorCaptor.getValue();

            then(actualPreDepartureFactor).isEqualTo(preDepartureDelayFactor);
        }
    }
}