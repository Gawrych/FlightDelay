package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.calculator.TrafficFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.TrafficFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import com.flightDelay.flightdelayapi.traffic.TrafficService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("The traffic factor collector")
class TrafficFactorCollectorImplTest {

    @Mock
    private TrafficService trafficService;

    @Mock
    private TrafficFactorsCalculator trafficFactorsCalculator;

    @Mock
    private StatisticReportCreator statisticReportCreator;

    @Captor
    private ArgumentCaptor<List<TrafficDto>> dtoCaptor;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor[]> entityArrayCaptor;

    @Captor
    private ArgumentCaptor<TrafficFactor> trafficFactorCaptor;

    @Captor
    private ArgumentCaptor<ValueWithDateHolder> valueWithDateHolderCaptor;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    private TrafficFactorCollectorImpl trafficFactorCollector;

    @BeforeEach
    void setUp() {
        this.trafficFactorCollector = spy(new TrafficFactorCollectorImpl(
                trafficService,
                trafficFactorsCalculator,
                statisticReportCreator));
    }

    @Nested
    @DisplayName("correctly collect factors")
    class ReturnFilledList {

        @Test
        @DisplayName("when pass dto list to superclass method")
        void CollectFactors_WhenPassDtoListFromDatabase_ThenReturnListOfFactors() {
            // Given
            List<TrafficDto> expectedList = List.of(new TrafficDto());
            given(trafficService.findAllLatestByAirport(anyString())).willReturn(expectedList);

            // When
            trafficFactorCollector.collect("AAAA");

            // Then
            verify(trafficFactorCollector).collectFactors(
                    anyString(),
                    dtoCaptor.capture(),
                    any(EntityStatisticFactor[].class));

            List<TrafficDto> actualList = dtoCaptor.getValue();

            then(actualList).isEqualTo(expectedList);
        }

        @Test
        @DisplayName("when pass traffic factors array to superclass method")
        void CollectFactors_WhenPassTrafficFactorArray_ThenReturnListOfFactors() {
            // Given
            List<TrafficDto> trafficDtos = List.of(new TrafficDto());
            given(trafficService.findAllLatestByAirport(anyString())).willReturn(trafficDtos);

            // When
            trafficFactorCollector.collect("AAAA");

            // Then
            EntityStatisticFactor[] expectedEntity = TrafficFactor.values();

            verify(trafficFactorCollector).collectFactors(anyString(), anyList(), entityArrayCaptor.capture());

            EntityStatisticFactor[] actualEntity = entityArrayCaptor.getValue();

            then(actualEntity).isEqualTo(expectedEntity);
        }

        @Test
        @DisplayName("when match factor TOP_MONTH_OF_TRAFFIC with external calculator")
        void CollectFactors_WhenPassTopMonthOfTraffic_ThenMatchWithExternalCalculator() {
            // Given
            TrafficFactor factorName = TrafficFactor.TOP_MONTH_OF_TRAFFIC;

            // When
            trafficFactorCollector.calculateFactor(factorName, List.of());

            // Then
            verify(trafficFactorsCalculator).calculateTopMonth(anyList());
            verifyNoMoreInteractions(trafficFactorsCalculator);
        }

        @Test
        @DisplayName("when match factor AVERAGE_MONTHLY_TRAFFIC with external calculator")
        void CollectFactors_WhenPassAverageMonthlyTraffic_ThenMatchWithExternalCalculator() {
            // Given
            TrafficFactor factorName = TrafficFactor.AVERAGE_MONTHLY_TRAFFIC;

            // When
            trafficFactorCollector.calculateFactor(factorName, List.of());

            // Then
            verify(trafficFactorsCalculator).calculateAverageMonthly(anyList());
            verifyNoMoreInteractions(trafficFactorsCalculator);
        }

        @Test
        @DisplayName("when pass factor TOP_MONTH_OF_TRAFFIC and calculated value to report creator")
        void CollectFactors_WhenGetsTopMonthOfTrafficAndValue_ThenPassToPrecisionFactorCreator() {
            // Given
            TrafficFactor expectedFactor = TrafficFactor.TOP_MONTH_OF_TRAFFIC;
            ValueWithDateHolder expectedValue = mock(ValueWithDateHolder.class);
            given(trafficFactorsCalculator.calculateTopMonth(anyList())).willReturn(expectedValue);

            // When
            trafficFactorCollector.calculateFactor(expectedFactor, List.of());

            // Then
            verify(statisticReportCreator).create(trafficFactorCaptor.capture(), valueWithDateHolderCaptor.capture());

            TrafficFactor actualTrafficFactor = trafficFactorCaptor.getValue();
            ValueWithDateHolder actualValue = valueWithDateHolderCaptor.getValue();

            then(actualTrafficFactor).isEqualTo(expectedFactor);
            then(actualValue).isEqualTo(expectedValue);
        }

        @Test
        @DisplayName("when pass factor AVERAGE_MONTHLY_TRAFFIC and calculated value to report creator")
        void CollectFactors_WhenGetsAverageMonthlyTrafficAndValue_ThenPassToPrecisionFactorCreator() {
            // Given
            TrafficFactor expectedFactor = TrafficFactor.AVERAGE_MONTHLY_TRAFFIC;
            double expectedValue = 10.0d;
            given(trafficFactorsCalculator.calculateAverageMonthly(anyList())).willReturn(expectedValue);

            // When
            trafficFactorCollector.calculateFactor(expectedFactor, List.of());

            // Then
            verify(statisticReportCreator).create(trafficFactorCaptor.capture(), doubleCaptor.capture());

            TrafficFactor actualTrafficFactor = trafficFactorCaptor.getValue();
            double actualValue = doubleCaptor.getValue();

            then(actualTrafficFactor).isEqualTo(expectedFactor);
            then(actualValue).isEqualTo(expectedValue);
        }

        @ParameterizedTest(name = "{index} : {0} input factor name")
        @EnumSource(TrafficFactor.class)
        @DisplayName("when no data factor method pass to report creator")
        void CollectFactors_WhenNoDataFactorGetsDataFromSuperclass_ThenPassToPrecisionFactorCreator(
                TrafficFactor trafficFactor) {

            // When
            trafficFactorCollector.getNoDataFactor(trafficFactor);

            // Then
            verify(statisticReportCreator).create(trafficFactorCaptor.capture());

            TrafficFactor actualTrafficFactor = trafficFactorCaptor.getValue();

            then(actualTrafficFactor).isEqualTo(trafficFactor);
        }
    }
}